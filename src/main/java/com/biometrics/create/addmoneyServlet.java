package com.biometrics.create;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class addmoneyServlet
 */
@WebServlet("/addmoney")
public class addmoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int amount=Integer.parseInt(request.getParameter("amount"));
		String username = (String) session.getAttribute("username");
		RequestDispatcher dispatcher = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Biometrics","root","10022004");
			PreparedStatement pst= con.prepareStatement("update account_balance set amount = amount+? where username = ? ");
			pst.setInt(1,amount);
			pst.setString(2, username);
			pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("Account.jsp");
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
