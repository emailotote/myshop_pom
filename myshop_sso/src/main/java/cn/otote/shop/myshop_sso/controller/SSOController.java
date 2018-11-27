package cn.otote.shop.myshop_sso.controller;

import cn.otote.shop.entity.ResultData;
import cn.otote.shop.entity.User;
import cn.otote.shop.service.IUserService;
import cn.otote.shop.util.Constact;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author otote
 * Created on 2018/11/27 11:37.
 */
@Controller
@RequestMapping("/sso")
public class SSOController {

    private static final Logger LOGGER= LoggerFactory.getLogger(SSOController.class);


    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/tologin")
    public String toLogin(Model model,String returnUrl) {
        model.addAttribute("returnUrl",returnUrl);
        return "login";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletResponse response, Model model,String returnUrl) {
        LOGGER.debug("用户名：{},密码：{}",username,password);

        //获取登录结果
        ResultData<User> resultData = userService.login(username, password);

        switch (resultData.getCode()){
            //登录成功
            case 200:

                if (returnUrl == null){
                    returnUrl="http://localhost:8081";
                }

                LOGGER.debug("登录成功后要去的页面："+returnUrl);

                //生成一个uuid 当作当前用户在redis缓存中的key
                String token = UUID.randomUUID().toString();

                //往缓存中添加当前登陆的用户信息
                redisTemplate.opsForValue().set(token,resultData.getData());
                //设置超时时间  一天
                redisTemplate.expire(token, 1, TimeUnit.DAYS);


                //设置一个cookie 将当前redis中的key写到用户浏览器的cookie中
                Cookie cookie=new Cookie(Constact.LOGIN_TOKEN,token);
                cookie.setMaxAge(60*60*24);
                //cookie的路径范围
                cookie.setPath("/");

                //响应中添加cookie信息
                response.addCookie(cookie);
                break;
            default:
                model.addAttribute("msg", resultData.getMsg());
                return "login";
        }


        return "redirect:"+returnUrl;
    }


    /**
     * 验证用户是否登录
     * @param token
     * @return
     */
    @RequestMapping("/checklogin")
    @ResponseBody
    public String checkLogin(@CookieValue(value = Constact.LOGIN_TOKEN,required = false) String token){
        User user = null;

        if (token != null){
            user = (User) redisTemplate.opsForValue().get(token);
        }


        if (user != null){
            return "loginInfo('"+new Gson().toJson(user) +"')";
        } else {
            return "loginInfo(null)";
        }
    }


    /**
     * 注销
     * @param token
     * @param response
     * @return
     */
    @RequestMapping("/signout")
    public String signOut(@CookieValue(value = Constact.LOGIN_TOKEN,required = false) String token, HttpServletResponse response){

        //在缓存中将当前用户信息删除
        redisTemplate.delete(token);

        //设置cookie过期
        Cookie cookie=new Cookie(Constact.LOGIN_TOKEN, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "login";
    }
}
