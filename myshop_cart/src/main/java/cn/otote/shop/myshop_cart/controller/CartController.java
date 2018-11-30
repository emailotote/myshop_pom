package cn.otote.shop.myshop_cart.controller;

import cn.otote.shop.aop.IsLogin;
import cn.otote.shop.entity.ShopCart;
import cn.otote.shop.entity.User;
import cn.otote.shop.service.IShopCartService;
import cn.otote.shop.util.Constact;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author otote
 * Created on 2018/11/27 19:42.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger LOGGER= LoggerFactory.getLogger(CartController.class);

    @Reference
    private IShopCartService shopCartService;

    @IsLogin
    @RequestMapping("/addcart")
    public String addCart(@CookieValue(value = Constact.SHOPCART_TOKEN ,required = false) String token,
                          ShopCart shopCart,
                          User user,
                          HttpServletResponse response){

        if (user!=null){
            LOGGER.debug("用户信息:"+user.toString());
        }
        LOGGER.debug("*********购物车:商品id：{},商品数量:{}",shopCart.getGid(),shopCart.getGnumber());

        token = shopCartService.addShopCart(shopCart, user, token);

        if (token != null){
            //添加成功 将购物车的token写道cookie中
            Cookie cookie=new Cookie(Constact.SHOPCART_TOKEN, token);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*365);

            response.addCookie(cookie);
        }

        return "addsuccess";
    }


    @IsLogin
    @RequestMapping("/list")
    @ResponseBody
    public String getShopCartList(@CookieValue(value = Constact.SHOPCART_TOKEN,required = false) String cartToken, User user){

        List<ShopCart> shopCartList = shopCartService.getShopCartList(cartToken, user);

        return "showShopCarts("+new Gson().toJson(shopCartList) +")";
    }


    @RequestMapping("/showlist")
    @IsLogin
    public String showList(@CookieValue(value = Constact.SHOPCART_TOKEN, required = false)String cartToken,
                           User user,
                           Model model){

        List<ShopCart> shopCartList = shopCartService.getShopCartList(cartToken, user);

        model.addAttribute("shopcarts", shopCartList);

        return "shopcartlist";
    }


}
