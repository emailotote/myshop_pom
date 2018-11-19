package cn.otote.shop.myshop_back;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FdfsClientConfig.class)//引入fastdfs配置
public class MyshopBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopBackApplication.class, args);
    }
}
