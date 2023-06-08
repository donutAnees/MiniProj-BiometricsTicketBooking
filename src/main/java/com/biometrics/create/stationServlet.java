package com.biometrics.create;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**
 * Servlet implementation class stationServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 1000, // 1 GB
maxRequestSize = 1024 * 1024 * 1000) 
@WebServlet("/station")
public class stationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int Station = Integer.parseInt(request.getParameter("station"));//
		PrintWriter out=response.getWriter();
		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		String path = "Resources" + File.separator + fileName;
		Connection con=null;
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Biometrics","root","10022004");
			PreparedStatement pst= con.prepareStatement("select username from credentials where path=?");
			pst.setString(1, path);
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				String username=rs.getString("username");
				pst=con.prepareStatement("select * from intransit where username=?");
				pst.setString(1, username);
				rs=pst.executeQuery();				
				if(rs.next()) {
					int cur_station=rs.getInt("cur_station");//
					int mul=(Station-cur_station)*10;//
					pst=con.prepareStatement("update account_balance set amount=amount-? where username=?");//
					pst.setInt(1, mul);//
					pst.setString(2, username);
					pst.executeUpdate();
					pst=con.prepareStatement("delete from intransit where username=?");
					pst.setString(1, username);
					pst.executeUpdate();
					pst=con.prepareStatement("select amount from account_balance where username=?");
					pst.setString(1, username);
					rs=pst.executeQuery();
					rs.next();
					int cur_amount=rs.getInt("amount");
					String camount=Integer.toString(cur_amount);
					session.setAttribute("amount",camount);
					dispatcher=request.getRequestDispatcher("Ended.jsp");
				}
				else {
					PreparedStatement pst_=con.prepareStatement("insert into intransit(username,cur_station) values(?,?)");//
					pst_.setString(1, username);
					pst_.setInt(2, Station);
					pst_.executeUpdate();
					dispatcher=request.getRequestDispatcher("Started.jsp");
				}
				dispatcher.forward(request,response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
