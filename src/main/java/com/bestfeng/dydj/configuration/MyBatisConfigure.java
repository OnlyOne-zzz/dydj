package com.bestfeng.dydj.configuration;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan({"com.bestfeng.dydj.mbg.mapper","com.bestfeng.dydj.dao"})
public class MyBatisConfigure {

}
