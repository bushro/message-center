package com.bushro.common.idempotent.expression;

/**
 * @author bushro
 * @date 2022/10/25
 */


import com.bushro.common.idempotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author bushro
 * 默认key 抽取， 优先根据 spel 处理
 * @date 2022/10/25
 */
public class ExpressionResolver implements KeyResolver {

	private static final SpelExpressionParser PARSER = new SpelExpressionParser();

	private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

	@Override
	public String resolver(Idempotent idempotent, JoinPoint point) {
		// 获取方法的参数
		Object[] arguments = point.getArgs();
		// 获取方法的参数名
		String[] params = DISCOVERER.getParameterNames(getMethod(point));
		StandardEvaluationContext context = new StandardEvaluationContext();

		if (params != null && params.length > 0) {
			for (int len = 0; len < params.length; len++) {
				//把参数名和参数值放进去
				context.setVariable(params[len], arguments[len]);
			}
		}

		Expression expression = PARSER.parseExpression(idempotent.key());
		//解析出表达式对应的值
		return expression.getValue(context, String.class);
	}

	/**
	 * 根据切点解析方法信息
	 * @param joinPoint 切点信息
	 * @return Method 原信息
	 */
	private Method getMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			try {
				method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
						method.getParameterTypes());
			}
			catch (SecurityException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
		return method;
	}

}
