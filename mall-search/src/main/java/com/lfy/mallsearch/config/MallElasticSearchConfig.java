package com.lfy.mallsearch.config;/*
 *@Author:user
 *@Date:7/22/2023 3:12 PM
 */

import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MallElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;


    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("121.5.68.6", 9200, "http"));
        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
        return client;
    }


}
