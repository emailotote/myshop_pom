package cn.otote.shop.dao;

import cn.otote.shop.entity.Order;
import cn.otote.shop.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 18:21.
 */
public interface IOrderDao {

    Integer addOrder(Order order);

    Integer addOrderDetail(@Param("orderdetails")List<OrderDetail> orderDetails);

    Order queryOrderById(Integer id);

    List<Order> queryOrderByUid(Integer uid);

    Integer updateOrderStatus(@Param("orderId") Integer orderId,@Param("status") Integer status);
}
