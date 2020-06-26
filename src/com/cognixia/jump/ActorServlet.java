package com.cognixia.jump;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActorServlet extends HttpServlet {

	private static final long serialVersionUID = 6649592449996130701L;
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	// set up my connection to the db and set up my prepared statements
	@Override
	public void init() {
		
		conn = ConnectionManager.getConnection();
		
		try {
			pstmt = conn.prepareStatement("select * from actor where actor_id = ?");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		doGet(request, response);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("actor-id"));
		
		String firstName = null, lastName = null;
		
		boolean retrieved = false;
		
		try {
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");
				
				retrieved = true;
			}
			
			rs.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		response.setContentType("text/html");
		
		PrintWriter pw = response.getWriter();
		
		
		pw.println("<html>");
		
		pw.println("<header><title>Actor</title></header>");
		
		pw.println("<body>");
		
		if(retrieved) {
			pw.println("<h1>" + firstName + " " + lastName + "</h1>");
		}
		else {
			pw.println("<h1>Actor Not Found</h1>");
		}
		
		pw.println("</body>");
		
		pw.println("</html>");
		
		
	}
	
	
	
	
	
	
	// close connection and prepared statement once finished
	@Override
	public void destroy() {
		
		try {
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
}
