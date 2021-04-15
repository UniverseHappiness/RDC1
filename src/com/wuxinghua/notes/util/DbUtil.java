package com.wuxinghua.notes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DbUtil {
    //数据库地址
    private static String dbURL="jdbc:mysql://localhost:3306/db1?characterEncoding=UTF-8";
    //用户名
    private static String dbUser="root";
    //密码
    private static String dbPassword="#include235711";
    //驱动名称
    private static String jdbcName="com.mysql.jdbc.Driver";

    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getCon()throws SQLException,ClassNotFoundException{
        Class.forName(jdbcName);
        Connection con= DriverManager.getConnection(dbURL, dbUser, dbPassword);
        return con;
    }

    /**
     * 关闭连接
     * @param con
     * @throws Exception
     */
    public static void close(PreparedStatement st, Connection con)throws SQLException{
        if(st!=null){
            st.close();
            if(con!=null){
                con.close();
            }
        }
    }
}
