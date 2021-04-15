package com.wuxinghua.notes.dao;

import com.wuxinghua.notes.bean.UserBean;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private Connection con;

    private PreparedStatement stmt;

    // 用户登录
    public UserBean login(User user) {

        try {
            con = DbUtil.getCon();
            String sql = "SELECT * FROM user WHERE user_name = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (user.getPassword().equals(rs.getString("password"))) {
                    user.setUserId(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setNoteNumber(rs.getInt("note_number"));
                    return new UserBean("登录成功", user);
                }
                return new UserBean("密码错误", null);
            }
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                DbUtil.close(stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new UserBean("该用户不存在", null);
    }

    // 用户修改密码
    public UserBean modifyPassword(String oldPassword, String newPassword, String checkPassword, User user) {

        try {
            if (user.getPassword().equals(oldPassword)&&newPassword.equals(checkPassword)){
            con = DbUtil.getCon();
            String sql = "update user set password = ? WHERE user_name = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, user.getUserName());
            stmt.executeUpdate();
                return new UserBean("密码修改成功");
            }else {
                return new UserBean("密码修改失败");
            }

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                DbUtil.close(stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new UserBean("密码修改失败");

    }

    // 用户修改用户名
    public UserBean modifyUserName(User user, String newUserName) {

        try {
            con = DbUtil.getCon();

            String sql = "select * from user where user_name = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, newUserName);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new UserBean("用户名已存在", null,null);
            } else {
                String sql2 = "update user set user_name = ? where user_name = ?";
                stmt = con.prepareStatement(sql2);
                stmt.setString(1, newUserName);
                stmt.setString(2, user.getUserName());
                stmt.executeUpdate();
                user.setUserName(newUserName);

                return new UserBean("用户名修改成功,请重新登录",newUserName,user );
            }


        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                DbUtil.close(stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new UserBean("改名失败", null,null);
    }

    // 用户注册
    public UserBean register(User user, String checkPassword) {
        try {
            con = DbUtil.getCon(); // 获得链接
            String sql = "SELECT * FROM user WHERE user_name = ?"; // ?表示占位符
            stmt = con.prepareStatement(sql); // 获得PrepareStatement对象
            stmt.setString(1, user.getUserName()); // 替换占位符
            ResultSet rs = stmt.executeQuery(); // 获取结果集
            if (rs.next()) {
                return new UserBean("用户名已存在,注册失败", null);
            } else if (user.getPassword().equals(checkPassword)) {
                String userName = user.getUserName();
                String password = user.getPassword();
                String sql_create = "insert into user(user_name,password) values(?,?)";
                stmt = con.prepareStatement(sql_create);
                stmt.setString(1, userName);
                stmt.setString(2, password);
                stmt.executeUpdate();
                return new UserBean("注册成功", user);
            } else {
                return new UserBean("两次输入密码不一致,注册失败", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtil.close(stmt, con);
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return new UserBean("系统错误", null);
    }
}

