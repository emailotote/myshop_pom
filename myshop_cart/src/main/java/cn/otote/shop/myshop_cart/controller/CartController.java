package cn.otote.shop.myshop_cart.controller;

import cn.otote.shop.aop.IsLogin;
import cn.otote.shop.entity.ShopCart;
import cn.otote.shop.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author otote
 * Created on 2018/11/27 19:42.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger LOGGER= LoggerFactory.getLogger(CartController.class);

    @IsLogin
    @RequestMapping("/addcart")
    public String addCart(ShopCart shopCart, User user){

        if (user!=null){
            LOGGER.debug("用户信息:"+user.toString());
        }
        LOGGER.debug("*********购物车:商品id：{},商品数量:{}",shopCart.getGid(),shopCart.getGnumber());

        return "addsuccess";
    }

}
