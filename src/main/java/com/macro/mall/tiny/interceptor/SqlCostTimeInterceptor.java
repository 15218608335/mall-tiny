package com.macro.mall.tiny.interceptor;

/**
 * @Author Mi_Tao
 * @Date 2024/5/1
 * @Description
 * @Version 1.0
 **/

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

// 定义拦截器  注解表明起作用的范围
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {StatementHandler.class,MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {StatementHandler.class,MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {StatementHandler.class,MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
@Slf4j
public class SqlCostTimeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object target = invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) target;

        try {
           return invocation.proceed(); // 执行被拦截的方法
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            log.info("SQL: {}, 执行耗时: {} ms", sql, end - start);
        }

    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
