package com.wuxinghua.notes.dao;

import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    private Connection con;

    private PreparedStatement stmt;

    // 管理员得到用户
    public User AdminGetUser(String userName, Note note) {

        User user = new User();
        try {
            con = DbUtil.getCon();
            String sql = "SELECT * FROM user WHERE user_name = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                user.setPassword(rs.getString("password"));
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setNoteNumber(rs.getInt("note_number"));
                return user;

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
        return null;
    }
}

