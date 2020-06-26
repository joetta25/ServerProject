package com.cognixia.jump;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class cd {

	public static Connection getConnection () throws SQLException {
        System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
		   Connection conn = null;
        try
        {
        	 
            String userName = "root";
            String password = "root123#";
            String url = "jdbc:mysql://localhost:3306/sakila?serverTimezone=EST5EDT";        
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("\nDatabase Connection Established...");
        }
       catch (SQLException ex)
        {
    	   
		       System.err.println ("Cannot connect to database server");
			   ex.printStackTrace();
        }      
		   
        
		return conn;
    }
	
}
