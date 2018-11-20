package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.dao.IGoodsDao;
import cn.otote.shop.entity.Goods;
import cn.otote.shop.service.IGoodsService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/19 16:13.
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<Goods> queryAll() {
        List<Goods> goodsList = goodsDao.queryAll();
        return goodsList;
    }

    @Override
    public Goods addGoods(Goods goods) {
        goodsDao.addGoods(goods);
        return goods;
    }

    @Override
    public List<Goods> queryNewGoods() {
        return goodsDao.queryNewGoods();
    }
}
