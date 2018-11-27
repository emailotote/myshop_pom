package cn.otote.shop.service;

import cn.otote.shop.entity.ResultData;
import cn.otote.shop.entity.User;

/**
 * @author otote
 * Created on 2018/11/27 11:50.
 */
public interface IUserService {
    ResultData<User> login(String username,String password);
}
