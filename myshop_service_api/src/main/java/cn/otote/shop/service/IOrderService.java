package cn.otote.shop.service;

import cn.otote.shop.entity.Order;
import cn.otote.shop.entity.User;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 17:58.
 */
public interface IOrderService {

    String addOrder(Integer aid, Integer[] cids, User user);

    List<Order> queryOrdersByUid(Integer uid);

}
