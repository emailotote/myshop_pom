package cn.otote.shop.myshop_service_provider;

import cn.otote.shop.dao.IGoodsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyshopServiceProviderApplicationTests {

    @Autowired
    private IGoodsDao goodsDao;

    @Test
    public void contextLoads() {
        System.out.println(goodsDao);
    }

}
