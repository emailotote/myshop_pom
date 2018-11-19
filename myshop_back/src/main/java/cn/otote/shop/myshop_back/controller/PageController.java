package cn.otote.shop.myshop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author otote
 * Created on 2018/11/19 12:36.
 */
@Controller
public class PageController {

    @RequestMapping("/topage/{page}")
    public String toPage(@PathVariable String page){
        return page;
    }

}
