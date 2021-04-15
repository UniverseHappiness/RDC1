package com.wuxinghua.notes.dao;

import com.wuxinghua.notes.bean.Msg;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.util.DbUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NoteDao {

    private Connection con;

    private PreparedStatement stmt;


    // 更新点赞数
    public void like(int like, Note note) {

        try {
            con = DbUtil.getCon();
            String title = note.getTitle();
            System.out.println(like);
            String sql = "update note set `like` = ? where title = ?"; //update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, like);
            stmt.setString(2, title);
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

    // 删除笔记
    public Msg fileNoteDelete(String title) {

        File file = new File(title+".txt");

        boolean isDelete = file.delete();

        if (isDelete) {
            return new Msg("删除笔记成功", isDelete);
        }
        return new Msg("删除笔记失败,请先点击保存标题按钮",isDelete);
    }

    // 移除标题
    public void titleRemove(User user, Note note){
        try {
            con = DbUtil.getCon();
            int noteNumber = user.getNoteNumber() - 1;
            String title = note.getTitle();
            String sql = "delete from `note` where `title` = ? "; //update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];
            stmt = con.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.executeUpdate();
            String sql2 = "update `user` set `note_number` = ? ";
            stmt = con.prepareStatement(sql2);
            stmt.setInt(1, noteNumber);
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
