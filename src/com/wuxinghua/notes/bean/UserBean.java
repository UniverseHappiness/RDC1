package com.wuxinghua.notes.bean;

import com.wuxinghua.notes.entity.User;


public class UserBean {
    private String result;
    private String password;
    private String newUserName;
    private String checkPassword;
    private String oldPassword;
    private String newPassword;

    private User user;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserBean(String result) {
        super();
        this.result = result;
    }

    public UserBean(String result, User user) {
        super();
        this.result = result;
        this.user = user;
    }

    public UserBean(String result, String newUserName, User user) {
        super();
        this.result = result;
        this.newUserName = newUserName;
    }
    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "result='" + result + '\'' +
                ", password='" + password + '\'' +
                ", newUserName='" + newUserName + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", user=" + user +
                '}';
    }
}

