package com.bushro.common.idempotent;


import com.bushro.common.idempotent.aspect.IdempotentAspect;
import com.bushro.common.idempotent.expression.ExpressionResolver;
import com.bushro.common.idempotent.expression.KeyResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bushro
 * @date 2022/10/25
 * <p>
 * 幂等插件初始化
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class IdempotentAutoConfiguration {

	/**
	 * 切面 拦截处理所有 @Idempotent
	 * @return Aspect
	 */
	@Bean
	public IdempotentAspect idempotentAspect() {
		return new IdempotentAspect();
	}

	/**
	 * key 解析器
	 * @return KeyResolver
	 */
	@Bean
	@ConditionalOnMissingBean(KeyResolver.class)
	public KeyResolver keyResolver() {
		return new ExpressionResolver();
	}

}
