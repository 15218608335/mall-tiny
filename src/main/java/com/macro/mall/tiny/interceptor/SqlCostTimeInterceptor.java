package com.macro.mall.tiny.interceptor;

/**
 * @Author Mi_Tao
 * @Date 2024/5/1
 * @Description
 * @Version 1.0
 **/

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Statement;
import java.util.Properties;

// 定义拦截器  注解表明起作用的范围
@Intercepts({
        @Signature( type = Executor.class, method = "update",args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})

})
@Slf4j
public class SqlCostTimeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement = (Statement) invocation.getArgs()[0];
        System.out.println("-------------------------------");
        System.out.println("sql:"+statement.toString().substring(statement.toString().indexOf(":")+1));
        Object result =invocation.proceed();
        System.out.println("-------------------------------");
//        System.out.println("result:"+ JSONObject.toJSONString(result));
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
