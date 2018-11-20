package cn.otote.shop.myshop_search.controller;

import cn.otote.shop.entity.Goods;
import cn.otote.shop.service.ISearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author otote
 * Created on 2018/11/20 17:44.
 */
@Controller
@RequestMapping("/search")
public class SearchController {


    @Reference
    private ISearchService searchService;


    @RequestMapping("/list/{keyWord}")
    public String searchByKeyWord(@PathVariable String keyWord){

        return "";
    }


    /***
     * 添加记录到索引库中
     * //@RequestBody 表示接收的是json将json转为实体对象
     * @param goods
     * @return
     */
    @RequestMapping("/addIndex")
    public String addIndex(@RequestBody Goods goods){
        Integer result = searchService.addIndex(goods);
        if (result != 1){
            return "error";
        }
        return "" ;
    }
}
