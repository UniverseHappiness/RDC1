package com.wuxinghua.notes.service;

import com.wuxinghua.notes.bean.UserBean;
import com.wuxinghua.notes.dao.UserDao;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.ValidateUtil;

public class UserService {

    private UserDao userDao = new UserDao();

    // 用户登录
    public UserBean login(String userName, String password){

        User user = new User();

        if(ValidateUtil.isInvalidUserName(userName)){
            return new UserBean("用户名为空",null);
        }
        user.setUserName(userName);
        user.setPassword(password);
        UserBean userBean = userDao.login(user);
        return userBean;
    }

    // 用户修改密码
    public UserBean modifyPassword(String oldPassword, String newPassword, String checkPassword, User user) {

        UserBean userBean = userDao.modifyPassword(oldPassword, newPassword, checkPassword, user);

        return userBean;
    }

    // 用户修改用户名信息
    public UserBean modifyUserName(String newUserName, User user) {
        if (ValidateUtil.isInvalidUserName(newUserName)){
            return new UserBean("用户名不能为空",null,null);
        }

        UserBean userBean = userDao.modifyUserName(user,newUserName);
        return userBean;
    }

    // 用户注册
    public UserBean register(String userName, String password, String checkPassword) {

        User user = new User();
        if(ValidateUtil.isInvalidUserName(userName)){
            return new UserBean("用户名为空,注册失败！",null);
        }
        user.setUserName(userName);
        user.setPassword(password);

        UserBean userBean = userDao.register(user,checkPassword);
        return userBean;
    }
}
