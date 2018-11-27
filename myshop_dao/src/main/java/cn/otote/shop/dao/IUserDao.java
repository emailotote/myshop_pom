package cn.otote.shop.dao;

import cn.otote.shop.entity.User;

/**
 * @author otote
 * Created on 2018/11/27 11:45.
 */
public interface IUserDao {

    User login(String username);
}
