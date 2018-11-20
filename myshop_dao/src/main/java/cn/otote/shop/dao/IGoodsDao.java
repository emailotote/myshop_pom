package cn.otote.shop.dao;

import cn.otote.shop.entity.Goods;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/19 13:51.
 */
public interface IGoodsDao {

    List<Goods> queryAll();

    Integer addGoods(Goods goods);

    List<Goods> queryNewGoods();

}
