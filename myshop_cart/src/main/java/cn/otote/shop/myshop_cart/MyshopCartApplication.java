package cn.otote.shop.myshop_cart;

import cn.otote.shop.aop.LoginAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyshopCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopCartApplication.class, args);
    }


    @Bean
    public LoginAop getLoginAop(){
        return new LoginAop();
    }
}
