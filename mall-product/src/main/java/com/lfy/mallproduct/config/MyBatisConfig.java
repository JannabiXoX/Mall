package com.lfy.mallproduct.config;/*
 *@Author:user
 *@Date:7/12/2023 7:27 PM
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.lfy.mallproduct.product.dao")
public class MyBatisConfig {

    //引入分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置请求的页面大于最后页后操作，true调回到首页，false继续请求 默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大但也限制i数量
        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }
}
