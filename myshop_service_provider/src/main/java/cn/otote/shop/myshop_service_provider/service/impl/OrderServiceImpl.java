package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.dao.IAddressDao;
import cn.otote.shop.dao.IOrderDao;
import cn.otote.shop.dao.IShopCartDao;
import cn.otote.shop.entity.*;
import cn.otote.shop.service.IOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 18:20.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private IShopCartDao shopCartDao;

    @Override
    @Transactional
    public String addOrder(Integer aid, Integer[] cids, User user) {
        //根据地址id获取地址详情
        Address address = addressDao.queryAddressById(aid);

        //查询购物车信息
        List<ShopCart> shopCartList = shopCartDao.quertCartsByIds(cids);

        //总价
        Double allPrice = 0.0;
        for (ShopCart shopCart : shopCartList) {
            allPrice += shopCart.getMoney();
        }


        //创建订单
        Order order=new Order();
        //订单号
        Date date = new Date();
        order.setOrderid((int)date.getTime());
        order.setAddress(address.getAddress());
        order.setCode(address.getCode());
        order.setOprice(allPrice);
        order.setPerson(address.getPerson());
        order.setStatus(0);
        order.setPhone(address.getPhone());
        order.setUid(user.getId());

        //添加订单
        orderDao.addOrder(order);


        //创建订单详情
        List<OrderDetail> orderDetails=new ArrayList<>();
        for (ShopCart shopCart : shopCartList) {
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOid(order.getId());
            orderDetail.setGcount(shopCart.getGnumber());
            orderDetail.setGid(shopCart.getGid());
            orderDetail.setGimage(shopCart.getGoods().getGimage());
            orderDetail.setPrice(shopCart.getGoods().getPrice());
            orderDetail.setGinfo(shopCart.getGoods().getGinfo());
            orderDetail.setGname(shopCart.getGoods().getTitle());
            orderDetails.add(orderDetail);
        }

        //添加订单详情
        orderDao.addOrderDetail(orderDetails);

        //删除购物车
        shopCartDao.deleteCartsByIds(cids);

        return order.getOrderid().toString();
    }

    @Override
    public List<Order> queryOrdersByUid(Integer uid) {
        return orderDao.queryOrderByUid(uid);
    }
}
