package cn.otote.shop.alipay_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author otote
 * Created on 2018/11/30 17:23.
 */
@Controller
@RequestMapping("/alipay")
public class AliPayController {


    @RequestMapping("/topay")
    public String toPay() {

        return null;
    }



    
}
