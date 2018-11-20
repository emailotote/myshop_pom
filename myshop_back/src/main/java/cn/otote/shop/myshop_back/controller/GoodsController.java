package cn.otote.shop.myshop_back.controller;

import cn.otote.shop.entity.Goods;
import cn.otote.shop.service.IGoodsService;
import cn.otote.shop.util.HttpClientUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author otote
 * Created on 2018/11/19 16:50.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${image.path}")
    private String imgPath;//图片路径前缀

    @RequestMapping("/list")
    public String getGoodsList(Model model){

        List<Goods> goodsList = goodsService.queryAll();
        model.addAttribute("goodslist",goodsList);
        model.addAttribute("imgpath",imgPath);
        return "goodslist";
    }


    /**
     * 添加商品详情
     * @param goods
     * @param gfile 图片文件
     * @return
     * @throws IOException
     */
    @RequestMapping("/addgoods")
    public String addGoods(Goods goods, MultipartFile gfile) throws IOException {
        //通过fastdfs客户端上传图片
        StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(gfile.getInputStream(), gfile.getSize(), "png", null);
        //获取图片上传后的路径
        String path = result.getFullPath();
        goods.setGimage(path);

        //获取主键回填后的goods对象
        goods = goodsService.addGoods(goods);

        //模拟http请求发送数据  将记录同步到索引库
        HttpClientUtils.sendJson("http://localhost:8082/search/addIndex",new Gson().toJson(goods));

        return "redirect:/goods/list";
    }


    /**
     * 获取最新上架的商品
     * @return
     */
    @RequestMapping("/querynew")
    @ResponseBody
    @CrossOrigin //给响应头设置允许跨域
    public List<Goods> queryNewGoods(){
        List<Goods> goodsList = goodsService.queryNewGoods();
        System.out.println(goodsList);
        return goodsList;
    }


}
