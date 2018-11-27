package cn.otote.shop.aop;

import cn.otote.shop.entity.User;
import cn.otote.shop.util.Constact;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * @author otote
 * Created on 2018/11/27 20:04.
 * 配置切面
 */
@Aspect
public class LoginAop {

    private static final Logger LOGGER= LoggerFactory.getLogger(LoginAop.class);


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 环绕增强
     * @param proceedingJoinPoint
     * @return
     */
    @Around("execution(* *..*Controller.*(..)) && @annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        LOGGER.debug("******调用了登录验证前置增强******");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取request请求
        HttpServletRequest request = attributes.getRequest();


        //用户令牌
        String loginToken=null;


        Cookie[] cookies = request.getCookies();
        //从cookie中查找用户令牌信息
        if (cookies!=null){
            for (int i = 0; i < cookies.length; i++) {
                if (Constact.LOGIN_TOKEN.equals(cookies[i].getName())){
                    loginToken=cookies[i].getValue();
                    break;
                }
            }
        }



        User user=null;

        if (loginToken!=null){
            //从缓存中获取用户信息
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }


        //用户没登陆   判断是否需要强制跳转到登录界面
        if (user==null){
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            //获取需要增强的目标方法
            Method method = signature.getMethod();
            //获取注解
            IsLogin isLogin = method.getAnnotation(IsLogin.class);

            //获取是否需要强制跳转
            boolean toLogin = isLogin.value();

            if (toLogin){
                String returnUrl=request.getRequestURL()+"?"+request.getQueryString();
                //对url进行编码
                returnUrl = URLEncoder.encode(returnUrl,"utf-8");
                returnUrl = returnUrl.replace("&","%26");

                //强制跳转到登录页
                return "redirect:http://localhost:8084/sso/tologin?returnUrl="+ returnUrl;
            }
        }

        //用户已经登录  获取目标方法的所有参数
        Object[] args = proceedingJoinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            //将user的值赋给目标方法的参数
            if (args[i].getClass()==User.class){
                args[i]=user;
            }
        }

        Object proceed = proceedingJoinPoint.proceed(args);

        LOGGER.debug("******调用了登录验证后置增强******");

        return proceed;
    }

}
