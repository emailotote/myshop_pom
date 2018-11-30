package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.dao.IGoodsDao;
import cn.otote.shop.dao.IShopCartDao;
import cn.otote.shop.entity.Goods;
import cn.otote.shop.entity.ShopCart;
import cn.otote.shop.entity.User;
import cn.otote.shop.service.IShopCartService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author otote
 * Created on 2018/11/28 17:00.
 */
@Service
public class ShopCartServiceImpl implements IShopCartService {

    @Autowired
    private IShopCartDao shopCartDao;

    @Autowired
    private IGoodsDao goodsDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String addShopCart(ShopCart shopCart, User user, String token) {

        if (user != null) {
            //用户已经登录  将购物车的数据添加到数据库中
            shopCart.setUid(user.getId());
            shopCartDao.addShopCart(shopCart);
        } else {
            //用户没登录 将购物车的信息放入到redis缓存中
            if (token == null) {
                token = UUID.randomUUID().toString();
            }

            //将购物车的信息放入缓存
            redisTemplate.opsForList().leftPush(token, shopCart);
            redisTemplate.expire(token, 365, TimeUnit.DAYS);

            return token;
        }

        return null;
    }


    @Override
    @Transactional
    public List<ShopCart> getShopCartList(String cartToken, User user) {
        //用来合并缓存中和数据库中购物车中商品的集合
        List<ShopCart> shopCartList = null;


        //cartToken不为空  说明缓存中有存放商品信息。  先获取缓存中的商品信息
        if (cartToken != null){
            //获取缓存中商品的数量
            Long size = redisTemplate.opsForList().size(cartToken);
            shopCartList = redisTemplate.opsForList().range(cartToken, 0, size);

            //根据缓存中商品的id到数据库中查询商品的详情
            for (int i = 0; i < shopCartList.size(); i++) {
                Goods goods = goodsDao.queryGoodsById(shopCartList.get(i).getGid());
                //将商品详情放入集合中
                shopCartList.get(i).setGoods(goods);
            }
        }

        if (user != null){
            //用户已经登录  将缓存中的购物车信息添加到数据库中
            for (int i = 0; i < shopCartList.size(); i++) {
                shopCartList.get(i).setUid(user.getId());
                shopCartDao.addShopCart(shopCartList.get(i));
            }

            //清空缓存中的购物车信息
            redisTemplate.delete(cartToken);

            //从数据库中查询购物车的数据
            shopCartList = shopCartDao.queryCartsByUid(user.getId());
        }

        return shopCartList;
    }
}
