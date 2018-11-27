package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.dao.IUserDao;
import cn.otote.shop.entity.ResultData;
import cn.otote.shop.entity.User;
import cn.otote.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author otote
 * Created on 2018/11/27 11:52.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResultData<User> login(String username, String password) {

        User user = userDao.login(username);

        ResultData<User> resultData = new ResultData<>();

        if (user == null) {
            //表示没找到该用户
            resultData.setCode(404);
            resultData.setMsg("用户名不存在！！！");
        }else {
            if (user.getPassword().equals(password)){
                resultData.setCode(200);
                resultData.setMsg("登录成功");
                resultData.setData(user);
            }else {
                resultData.setCode(405);
                resultData.setMsg("密码错误！！！");
            }
        }

        return resultData;
    }
}
