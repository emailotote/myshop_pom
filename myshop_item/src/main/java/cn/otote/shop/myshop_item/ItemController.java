package cn.otote.shop.myshop_item;

import cn.otote.shop.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author otote
 * Created on 2018/11/21 20:06.
 */
@Controller
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private Configuration configuration;

    /**
     * 生成静态页面
     * @param goods
     * @param request
     * @return
     */
    @RequestMapping("/createhtml")
    public void createHtml(@RequestBody Goods goods, HttpServletRequest request){

        Writer out=null;
        //获取页面模板
        try {
            Template template = configuration.getTemplate("goods.ftl");

            //通过map存放数据  生成到页面
            Map<String,Object> map = new HashMap<>();
            map.put("context", request.getContextPath());
            map.put("goods",goods);

            //获取存放静态页面的路径
            String path = this.getClass().getResource("/static/html/").getPath();
            //将静态页面输出
            out = new FileWriter(path+goods.getId()+".html");

            //生成静态页面
            template.process(map,out );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
