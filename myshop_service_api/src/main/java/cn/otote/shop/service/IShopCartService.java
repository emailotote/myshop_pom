package cn.otote.shop.service;

import cn.otote.shop.entity.ShopCart;
import cn.otote.shop.entity.User;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/28 16:59.
 */
public interface IShopCartService {

    //添加商品到购物车
    String addShopCart(ShopCart shopCart, User user, String token);

    List<ShopCart> getShopCartList(String cartToken,User user);
}
