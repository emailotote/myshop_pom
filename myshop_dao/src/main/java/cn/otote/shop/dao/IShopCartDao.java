package cn.otote.shop.dao;

import cn.otote.shop.entity.ShopCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/28 16:55.
 */
public interface IShopCartDao {

    Integer addShopCart(ShopCart shopCart);

    List<ShopCart> queryCartsByUid(Integer uid);

    List<ShopCart> quertCartsByIds(@Param("ids") Integer[] ids);

    Integer deleteCartsByIds(@Param("ids") Integer[] ids);

}
