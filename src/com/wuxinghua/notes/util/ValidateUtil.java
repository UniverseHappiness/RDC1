package com.wuxinghua.notes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ValidateUtil {
    // 验证用户名
    public static boolean isInvalidUserName(String userName){
        if(userName == null || userName.equals("")){
            return true;
        }
        return false;
    }

    // 验证笔记标题
    public static boolean isValidNoteTitle(String title) {
        if (title == null ||  title.equals("")){
            return false;
        }
        return true;
    }

    // 验证保存信息
    public static boolean isSaveInformation(String noteText) {
        if (noteText == null || noteText.equals("")) {
            return false;
        }
        return true;
    }
}
