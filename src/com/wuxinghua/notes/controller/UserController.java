package com.wuxinghua.notes.controller;


import com.wuxinghua.notes.bean.UserBean;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.service.UserService;

public class UserController {
    private UserService userService = new UserService();


    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public UserBean login(String userName, String password){

        return userService.login(userName, password);
    }

    /**
     * 用户注册
     * @param userName
     * @param password
     * @param checkPassword
     * @return
     */
    public UserBean register(String userName, String password, String checkPassword){

        return userService.register(userName,password,checkPassword);
    }

    /**
     * 修改用户名
     * @param newUserName
     * @param user
     * @return
     */
    public UserBean modifyUserName(String newUserName, User user){

        return userService.modifyUserName(newUserName,user);
    }

    /**
     * 修改密码
     * @param oldPassWord
     * @param newPassword
     * @param checkPassword
     * @param user
     * @return
     */
    public UserBean modifyPassword(String oldPassWord, String newPassword,String checkPassword ,User user){

        return userService.modifyPassword(oldPassWord,newPassword,checkPassword,user);
    }
}


