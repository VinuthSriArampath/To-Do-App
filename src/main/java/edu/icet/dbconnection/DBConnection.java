package edu.icet.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   private static DBConnection instance;
   private Connection connection;
   private DBConnection(){
       try {
           connection=DriverManager.getConnection("JDBC:mysql://localhost:3306/todoapp","root","123456");
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

    public static DBConnection getInstance() {
        if(instance==null) return instance=new DBConnection();
        return instance;
    }
    public Connection getConnection(){
       return connection;
    }

}
