package cn.otote.shop.service;

import cn.otote.shop.entity.Goods;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/19 13:54.
 */
public interface IGoodsService {

    List<Goods> queryAll();

    Goods addGoods(Goods goods);

    List<Goods> queryNewGoods();
}
