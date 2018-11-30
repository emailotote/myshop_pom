package cn.otote.shop.myshop_order.controller;

import cn.otote.shop.aop.IsLogin;
import cn.otote.shop.entity.Address;
import cn.otote.shop.entity.Order;
import cn.otote.shop.entity.ShopCart;
import cn.otote.shop.entity.User;
import cn.otote.shop.service.IAddressService;
import cn.otote.shop.service.IOrderService;
import cn.otote.shop.service.IShopCartService;
import cn.otote.shop.util.Constact;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author otote
 * Created on 2018/11/28 20:51.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Reference
    private IAddressService addressService;

    @Reference
    private IOrderService orderService;

    @Reference
    private IShopCartService shopCartService;

    @RequestMapping("/edit")
    @IsLogin(true)
    public String orderEdit(@CookieValue(value = Constact.SHOPCART_TOKEN,required = false)String cartToken,
                            User user,
                            Model model,Integer[] goodsid){


        //查询用户的地址信息
        List<Address> addressList = addressService.queryAddressByUid(user.getId());

        LOGGER.debug("登录的用户："+user.toString()+";商品id："+ Arrays.toString(goodsid)+";token:"+cartToken);
        LOGGER.debug("登录的用户的地址信息："+addressList);

        //总价
        Double allPrice=0.0;

        //用来存放当前用户选中要付款的商品
        List<ShopCart> shopCarts = new ArrayList<>();

        //从数据库中获取用户当前的购物车的所有商品
        List<ShopCart> shopCartList = shopCartService.getShopCartList(cartToken, user);

        for (ShopCart shopCart : shopCartList) {
            for (int i = 0; i < goodsid.length; i++) {
                //当前的商品被选中
                if (shopCart.getGid().equals(goodsid[i])){
                    shopCarts.add(shopCart);
                    allPrice+=shopCart.getMoney();
                }
            }
        }


        model.addAttribute("addresslist",addressList);
        model.addAttribute("allprice",allPrice);
        model.addAttribute("shopcarts", shopCarts);

        return "orderedit";
    }


    /**
     * 新增收货地址
     * @param user
     * @param address
     * @return
     */
    @RequestMapping("/addaddress")
    @ResponseBody
    @IsLogin(true)
    public Integer addAddress(User user,Address address){

        address.setUid(user.getId());

        LOGGER.debug("用户信息："+user);
        LOGGER.debug("新增的地址信息："+address);

        Integer result = addressService.insertAddress(address);

        LOGGER.debug("添加地址的结果："+result);

        return result;
    }


    @RequestMapping("/addorder")
    @ResponseBody
    @IsLogin(true)
    public String addOrder(Integer aid,Integer[] cids,User user){

        LOGGER.debug("收货地址id："+aid);
        LOGGER.debug("购物车id："+Arrays.toString(cids));
        LOGGER.debug("用户："+user);

        //添加订单
        String orderId = orderService.addOrder(aid, cids, user);

        LOGGER.debug("订单号："+orderId);

        return orderId;
    }



    /**
     * 查询用户的订单列表
     * @return
     */
    @RequestMapping("/list")
    @IsLogin(true)
    public String orderList(User user, Model model){
        List<Order> orders = orderService.queryOrdersByUid(user.getId());
        model.addAttribute("orders", orders);

        LOGGER.debug("用户订单详情："+orders);
        return "orderlist";
    }
}
