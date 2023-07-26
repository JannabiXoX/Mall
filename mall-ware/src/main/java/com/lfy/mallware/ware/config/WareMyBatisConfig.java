package com.lfy.mallware.ware.config;/*
 *@Author:user
 *@Date:7/18/2023 10:33 AM
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.lfy.mallware.ware.dao")
@EnableTransactionManagement
@Configuration
public class WareMyBatisConfig {
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
