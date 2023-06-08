<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Account Page</title>
        <link rel="stylesheet" href="accountstyle.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,600,0,0" />
    </head>
    <body>
        <div class="acc-details-container">
            <div class="acc-details">
                <div class="acc-details-logo">
                    <img src="user.png" alt="userlogo">
                </div>
                <div class="acc-details-name">
                    <h1>HI!</h1>
                    <div><h1><%=session.getAttribute("username") %></h1></div>
                </div>
                <form action="balance" method="get"></form>
                <div class="acc-balance">
                    Your Current Balance INR 
                    <%
                    try{
                    	String username =(String)session.getAttribute("username");
                    	Class.forName("com.mysql.jdbc.Driver");
            			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Biometrics","root","10022004");
            			PreparedStatement pst = con.prepareStatement("select amount from account_balance where username = ?");
                    	pst.setString(1,username);
                    	ResultSet rs=pst.executeQuery();
                    	if(rs.next()){
                    		out.print(rs.getInt("amount"));
                    	}
                    }
                    
                    catch(Exception e){
                    	e.printStackTrace();
                    }
                    %>
                </div>
                <div class="acc-addmoney">
                    <a href="addmoney.jsp">Add Money</a>
                </div>
            </div>
        </div>

    </body>
</html>