package cn.otote.shop.myshop_service_provider;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.otote.shop.dao")
@DubboComponentScan("cn.otote.shop.myshop_service_provider.service.impl")
public class MyshopServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopServiceProviderApplication.class, args);
    }
}
