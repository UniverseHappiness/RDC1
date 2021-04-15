package com.wuxinghua.notes.dao;

import com.wuxinghua.notes.bean.NoteBean;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.DbUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteDao {

    private Connection con;

    private PreparedStatement stmt;

    // 提交笔记
    public boolean noteWriteCommit(Note note) {

        try {
            FileWriter fileWriter = new FileWriter(note.getTitle()+".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(note.getNoteText());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用文件名称创建流对象

        return true;
    }

    // 新建笔记
    public NoteBean DBCreateWrite(Note note, User user) {

        try {
            con = DbUtil.getCon();
            String userName = user.getUserName();
            String title = note.getTitle();
            String sql = "insert into note (`like`,`user_name`,`title`) values(0,?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, title);
            stmt.executeUpdate();
            int noteNumber = user.getNoteNumber()+1;

            String sql2 = "update user set note_number = ? where user_name = ?";
            stmt = con.prepareStatement(sql2);
            stmt.setInt(1, noteNumber);
            stmt.setString(2, userName);
            stmt.executeUpdate();

            return new NoteBean("添加标题成功", note);
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
        return new NoteBean("添加标题成功", note);
    }

    // 修改笔记标题
    public void DBModifyTitle(Note note, String oldTiTle) {

        try {
            con = DbUtil.getCon();
            String title = note.getTitle();
            String sql = "update note set title = ? where title = ?"; //update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];
            stmt = con.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, oldTiTle);
            stmt.executeUpdate();

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

    }

    // 修改可见性
    public void DBModifyVisibility(Note note){

        try {
            con = DbUtil.getCon();
            String title = note.getTitle();
            String sql = "update note set `visibility` = ? where `title` = ? "; //update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];
            stmt = con.prepareStatement(sql);
            stmt.setBoolean(1, note.isVisibility());
            stmt.setString(2, note.getTitle());
            stmt.executeUpdate();

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
    }
}


