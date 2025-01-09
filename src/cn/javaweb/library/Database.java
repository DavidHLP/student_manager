package cn.javaweb.library;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection connection=null;

    public static Connection getDatabaseConnection(Config config) {

        try{

            if(connection!=null){

                if(!connection.isClosed()){
                    return connection;
                }else{
                    System.out.println("数据库连接已断开");
                }
            }

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(config.getDbUrl(),config.getDbUser(),config.getDbPassword());
            if(connection==null){
                System.out.println("数据库连接失败！！！！！！！！！！！！！");
            }
            System.out.println("数据库连接成功！！！！！！！");
            return connection;
        }catch(Exception e){
            System.out.println("ERROR"+e.getMessage());
            return null;
        }
    }

    public static boolean releaseConnection() {
        if(connection!=null){
            try{
                connection.close();
                connection = null;
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

}
