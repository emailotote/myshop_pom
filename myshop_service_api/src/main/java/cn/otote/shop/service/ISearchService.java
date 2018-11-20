package cn.otote.shop.service;

import cn.otote.shop.entity.Goods;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/20 17:50.
 */
public interface ISearchService {

    /***
     * 添加索引到索引库
     * @param goods
     * @return
     */
    Integer addIndex(Goods goods);

    /**
     * 根据关键字查询数据
     * @param keyWord
     * @return
     */
    List<Goods> search(String keyWord);
}
