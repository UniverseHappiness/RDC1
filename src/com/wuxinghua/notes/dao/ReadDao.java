package com.wuxinghua.notes.dao;

import com.wuxinghua.notes.bean.NoteBean;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.DbUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadDao {


    private Connection con;

    private PreparedStatement stmt;

    // 标题查询
    public ObservableList<Note> queryTitle(String searchTitle) {

        ObservableList<Note> list = FXCollections.observableArrayList();
            boolean flag = false;

        try {
            con = DbUtil.getCon();
            String sql = "select * from note where title like ? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+searchTitle+"%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Note note = new Note();

                note.setTitle(resultSet.getString("title"));
                note.setNoteId(resultSet.getInt("note_id"));
                note.setUserName(resultSet.getString("user_name"));
                note.setLike(resultSet.getInt("like"));
                note.setCreateTime(resultSet.getString("create_time"));
                note.setUpdateTime(resultSet.getString("update_time"));
                note.setVisibility(resultSet.getBoolean("visibility"));

                if (note.isVisibility()) {
                    list.add(note);
                }
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
        return list;

    }

    // 用户查询
    public ObservableList<Note> userQuery(String userName) {
        ObservableList<Note> list = FXCollections.observableArrayList();

        try {
            con = DbUtil.getCon();
            String sql = "select * from `note` where `user_name` like ? ";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+userName+"%");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Note note = new Note();
                note.setTitle(resultSet.getString("title"));
                note.setNoteId(resultSet.getInt("note_id"));
                note.setUserName(resultSet.getString("user_name"));
                note.setLike(resultSet.getInt("like"));
                note.setCreateTime(resultSet.getString("create_time"));
                note.setUpdateTime(resultSet.getString("update_time"));
                note.setVisibility(resultSet.getBoolean("visibility"));

                if (note.isVisibility()) {
                    list.add(note);
                }
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
        return list;
    }

    // 用户信息
    public ObservableList<User> userInfomation() {
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            con = DbUtil.getCon();
            String sql = "select * from user";
            stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
               User user = new User();

               user.setUserId(resultSet.getInt("user_id"));
               user.setUserName(resultSet.getString("user_name"));
               user.setPassword(resultSet.getString("password"));
               user.setNoteNumber(resultSet.getInt("note_number"));

               list.add(user);
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
            System.out.println(list);
        }
        return list;
    }

    // 笔记信息
    public ObservableList<Note> noteInformation(User user){

        ObservableList<Note> list = FXCollections.observableArrayList();
        try {
            con = DbUtil.getCon();
            String sql = "select * from note where user_name = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Note note = new Note();

                note.setTitle(resultSet.getString("title"));
                note.setNoteId(resultSet.getInt("note_id"));
                note.setUserName(resultSet.getString("user_name"));
                note.setLike(resultSet.getInt("like"));
                note.setCreateTime(resultSet.getString("create_time"));
                note.setUpdateTime(resultSet.getString("update_time"));
                note.setVisibility(resultSet.getBoolean("visibility"));

                list.add(note);
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
            System.out.println(list);
        }
        return list;

    }

    // 所有笔记信息
    public NoteBean allNoteInformation(User user) {

        ObservableList<Note> list = FXCollections.observableArrayList();
        try {
            con = DbUtil.getCon();
            String sql = "select * from note";
            stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Note note = new Note();
                note.setTitle(resultSet.getString("title"));
                note.setNoteId(resultSet.getInt("note_id"));
                note.setUserName(resultSet.getString("user_name"));
                note.setLike(resultSet.getInt("like"));
                note.setCreateTime(resultSet.getString("create_time"));
                note.setUpdateTime(resultSet.getString("update_time"));
                note.setVisibility(resultSet.getBoolean("visibility"));
                list.add(note);
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
            System.out.println(list);
            return new NoteBean("查看笔记成功",user,list);
        }
    }

    // 插入笔记
    public NoteBean noteInsert(String title, User user) throws IOException {
             FileReader fileReader = null ;
        try {
            fileReader = new FileReader(title+".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line).append('\n');
            }
            return new NoteBean("笔记文本查找成功",stringBuffer.toString(),user);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileReader.close();
        }

        return new NoteBean("笔记文本查找失败",null,user);
    }
}



