package com.wipro.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {
    public static Connection getDBConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String Url = "jdbc:oracle:thin:@localhost:1521:XE";
            String User = "system";    
            String Pass = "roshni";     

            Connection connection = DriverManager.getConnection(Url, User, Pass);
            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}