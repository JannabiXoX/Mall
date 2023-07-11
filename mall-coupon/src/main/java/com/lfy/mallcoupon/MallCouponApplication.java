package com.lfy.mallcoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *1）、引入依赖
 *           <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 *         </dependency>
 *2）、在application.yml中配置
 *  application:
 *     name: coupon
 *   config:
 *     import:
 *       - nacos:coupon.properties?refresh=true
 *3）、根据上一行命名的文件在nacos配置中心添加同名数据集
 *
 * @RefreshScope 动态获取并刷新配置
 * @Value("${}")
 * 优先配置中心的配置
 *
 * 2.细节
 *   1）、命名空间：配置隔离
 *     默认：pulic（保留空间)：默认新增的所有配置都在public空间
 *     1、利用namespace做隔离
 *     2、每一个微服务互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己命名空间下的配置文件
 *
 *   2）、配置集：所有配置的集合
 *   3）、配置集ID：类似配置文件名 dataID
 *
 *   4）、配置分组：
 *     默认所有的配置集都属于：DEFAULT_GROUP；
 *     1111，gggg
 *
 * 每个微服务创建自己的命名空间、使用配置分组区分环境，dev,test,
 *
 * 3、同时加载多个配置集
 *   1）、微服务任何配置信息，任何配置文件都可以放在配置中心
 *   2）、只需要在配置多个路径
 *     config:
 *     import:
 *       - nacos:datasource.yml?refresh=true
 *       - nacos:mall-coupon.properties?refresh=true
 *       - nacos:mybatis-puls?refresh=true
 *       - nacos:nacos.yml?refresh=true
 *   3）、@Value、@ConfigurationProperties
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCouponApplication.class, args);
    }

}
