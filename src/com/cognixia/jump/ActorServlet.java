package com.cognixia.jump;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ActorServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 6649592449996130701L;
	private Connection conn;
	private PreparedStatement pstmt;
	
	
	List<String> titleList = new ArrayList<>();
	// set up my connection to the db and set up my prepared statement
	@Override
	public void init() {
		conn = ConnectionManager.getConnection();
		try {
			pstmt = conn.prepareStatement("select title from film where rating = ? && rental_rate =? order by title Limit ?");
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
		String rating = request.getParameter("rating");
		double rental = Double.parseDouble(request.getParameter("rental"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		boolean retrieved = false;
		try {
			pstmt.setString(1, rating);
			pstmt.setDouble(2, rental);
			pstmt.setInt(3, limit);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				titleList.add(rs.getString("title"));
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
			for (String tie : titleList) {
				if(!tie.equals(null)) {
					pw.println("<h1>" + tie + "</h1>");
				}
			}
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