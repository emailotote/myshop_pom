package cn.otote.shop.myshop_search.controller;

import cn.otote.shop.entity.Goods;
import cn.otote.shop.entity.SolrPage;
import cn.otote.shop.service.ISearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Value("${image.path}")
    private String path;

    @RequestMapping("/list")
    public String searchByKeyWord(String keyWord, Model model,SolrPage<Goods> page){
        SolrPage<Goods> goodsPage = searchService.search(keyWord, page);

        model.addAttribute("goodsPage",goodsPage);
        model.addAttribute("path",path);//图片访问路径
        model.addAttribute("keyWord",keyWord);


        return "searchlist";
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
