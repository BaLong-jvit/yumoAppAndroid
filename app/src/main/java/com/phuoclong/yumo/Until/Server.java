package com.phuoclong.yumo.Until;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
//   private static String ipServer = "yomidata.mssql.somee.com";
//   private static String classs = "net.sourceforge.jtds.jdbc.Driver";
//   private static String database = "yomidata";
//   private static String userName = "adminbalong_SQLLogin_1";
//   private static String password = "an999izt1w";
    private static String ipServer = "192.168.1.37";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "YoMi";
    private static String userName = "sa";
    private static String password = "123";
   @SuppressLint("NewApi")
   public static Connection connection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
//            ConnURL = "workstation id="+ipServer+";packet size=4096;user id="
//                    +userName+";pwd="+password
//                    +";data source=QuanLyYumo.mssql.somee.com;persist security info=False;initial catalog="+database;
            ConnURL = "jdbc:jtds:sqlserver://" + ipServer + ";"
                    + "databaseName=" + database + ";user=" + userName + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

}