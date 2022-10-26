package com.bushro.common.idempotent.aspect;


import com.bushro.common.idempotent.annotation.Idempotent;
import com.bushro.common.idempotent.exception.IdempotentException;
import com.bushro.common.idempotent.expression.KeyResolver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The Idempotent Aspect
 *
 * @author bushro
 */
@Aspect
public class IdempotentAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdempotentAspect.class);

	private static final ThreadLocal<Map<String, Object>> THREAD_CACHE = ThreadLocal.withInitial(HashMap::new);

	private static final String RMAPCACHE_KEY = "idempotent";

	/**
	 * 业务key
	 */
	private static final String KEY = "key";

	/**
	 * 是否在业务完成后删除key true:删除 false:不删除
	 */
	private static final String DELKEY = "delKey";

	@Resource
	private Redisson redisson;

	@Resource
	private KeyResolver keyResolver;

	@Pointcut("@annotation(com.bushro.common.idempotent.annotation.Idempotent)")
	public void pointCut() {
	}

	@Before("pointCut()")
	public void beforePointCut(JoinPoint joinPoint) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (!method.isAnnotationPresent(Idempotent.class)) {
			return;
		}
		Idempotent idempotent = method.getAnnotation(Idempotent.class);

		String key;

		// 若没有配置 幂等 标识编号，则使用 url + 参数列表作为区分
		if (!StringUtils.hasLength(idempotent.key())) {
			String url = request.getRequestURL().toString();
			String argString = Arrays.asList(joinPoint.getArgs()).toString();
			key = url + argString;
		}
		else {
			// 使用jstl 规则区分
			key = keyResolver.resolver(idempotent, joinPoint);
		}

		long expireTime = idempotent.expireTime();
		String info = idempotent.info();
		TimeUnit timeUnit = idempotent.timeUnit();
		boolean delKey = idempotent.delKey();

		// do not need check null
		RMapCache<String, Object> rMapCache = redisson.getMapCache(RMAPCACHE_KEY);
		String value = LocalDateTime.now().toString().replace("T", " ");
		Object v1;
		if (null != rMapCache.get(key)) {
			// 已经存在了
			throw new IdempotentException("[idempotent]:" + info);
		}
		synchronized (this) {
			//不存在则放入
			v1 = rMapCache.putIfAbsent(key, value, expireTime, timeUnit);
			// 如果原本没有这个key的话就会返回null, 如果有的话就会返回key对应的value
			if (null != v1) {
				throw new IdempotentException("[idempotent]:" + info);
			}
			else {
				LOGGER.info("[idempotent]:has stored key={},value={},expireTime={}{},now={}", key, value, expireTime,
						timeUnit, LocalDateTime.now().toString());
			}
		}
		Map<String, Object> map = new HashMap<>(16);
		map.put(KEY, key);
		map.put(DELKEY, delKey);
		THREAD_CACHE.set(map);
	}

	@After("pointCut()")
	public void afterPointCut(JoinPoint joinPoint) {
		Map<String, Object> map = THREAD_CACHE.get();
		if (CollectionUtils.isEmpty(map)) {
			return;
		}

		RMapCache<Object, Object> mapCache = redisson.getMapCache(RMAPCACHE_KEY);
		if (mapCache.size() == 0) {
			return;
		}

		String key = map.get(KEY).toString();
		boolean delKey = (boolean) map.get(DELKEY);

		if (delKey) {
			mapCache.fastRemove(key);
			LOGGER.info("[idempotent]:has removed key={}", key);
		}
		THREAD_CACHE.remove();
	}

}
