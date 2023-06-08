package com.biometrics.create;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class createServlet
 */
@WebServlet("/register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 1000, // 1 GB
maxRequestSize = 1024 * 1024 * 1000)   	// 1 GB
public class createServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String folderName="Resources";
		String uploadPath = request.getServletContext().getRealPath("") + folderName;
		File dir = new File(uploadPath);
		if (!dir.exists()) {
            dir.mkdirs();
        }
		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		String path = folderName + File.separator + fileName;
		InputStream is = filePart.getInputStream();
		String FilePath = uploadPath + File.separator + fileName;
        Files.copy(is, Paths.get(FilePath), StandardCopyOption.REPLACE_EXISTING);
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Biometrics","root","10022004");
			PreparedStatement pst = con.prepareStatement("insert into credentials(username,password,firstname,lastname,path) values(?,?,?,?,?)");
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, firstname);
			pst.setString(4, lastname);
			pst.setString(5, path);
			
			int rowCount = pst.executeUpdate();	
			dispatcher=request.getRequestDispatcher("login.jsp");
			
			if (rowCount > 0) {
				request.setAttribute("status", "success");
				pst=con.prepareStatement("insert into account_balance(username,amount) values(?,?)");
				pst.setString(1, username);
				pst.setInt(2, 0);
				pst.executeUpdate();
			}
			else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request,response);
		}
		catch(Exception e) {
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
