package com.bestfeng.dydj.utils;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author zh
 * @description spel表达式解析工具类
 */
public class SpelUtil {
    private static final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    /**
     * 解析
     * @param spel
     * @param method
     * @param args
     * @return
     */
    public static String parse(String spel, Method method, Object[] args) {
        /**
         * 创建解析器
         */
        ExpressionParser expressionParser = new SpelExpressionParser();
        StandardEvaluationContext standardEvaluationContext = packageEvaluationContext(method,args);
        return expressionParser.parseExpression(spel).getValue(standardEvaluationContext, String.class);
    }

    /**
     *
     * @param spel
     * @param standardEvaluationContext
     * @return
     */
    public static String parse(String spel, StandardEvaluationContext standardEvaluationContext) {
        /**
         * 创建解析器
         */
        ExpressionParser expressionParser = new SpelExpressionParser();
        return expressionParser.parseExpression(spel).getValue(standardEvaluationContext, String.class);
    }


    /**
     *
     * @param method
     * @param args
     * @return
     */
    public static StandardEvaluationContext packageEvaluationContext(Method method, Object[] args) {

        /**
         * 获取被拦截方法的参数名列表
         */
       //String[] paramNames = parameterNameDiscoverer.getParameterNames(method);

        /**
         * spel的上下文
         */
        StandardEvaluationContext standardEvaluationContext = new MethodBasedEvaluationContext( TypedValue.NULL,method,args,parameterNameDiscoverer);
        /**
         * 放入参数
         */
       // if ((null != paramNames) && (null != args)) {
       //     for (int i = 0; i < paramNames.length; i++) {
       //         standardEvaluationContext.setVariable(paramNames[i], args[i]);
       //     }
       // }
//        //设置p0 p1 p2支持 类型spring cache
//        int paramCount = method.getParameterCount();
//        for (int i = 0; i < paramCount; i++) {
//            Object value =  args[i];
//            standardEvaluationContext.setVariable("p" + i, value);
//            if (paramNames != null && paramNames.length > 0) {
//                standardEvaluationContext.setVariable(paramNames[i], value);
//            }
//        }
        return standardEvaluationContext;
    }
    private void  lookMethodBasedEvaluationContext(Method method, Object[] args){

    }

}
