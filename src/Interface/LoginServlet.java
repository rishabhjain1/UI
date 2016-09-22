package Interface;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import UsersDatabase.UserDBOperations;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out =response.getWriter();
		 String usr_name = request.getParameter("UserID");
		 String usr_pass = request.getParameter("Password");
		 HttpSession session=request.getSession();
		 session.setAttribute("UserID", usr_name);
		 UserDBOperations usr_DBOps = new UserDBOperations();
		 boolean check;
		 try {
			check = usr_DBOps.doAuthenticate(usr_name, usr_pass);
			if(check == true)
			 {
				 int accessLevel = usr_DBOps.getAccessLevel(usr_name);
				 if (accessLevel == 2 ) {
					 RequestDispatcher rd=request.getRequestDispatcher("/Admin.html");
			         rd.forward(request,response);
				 }
				 else if (accessLevel == 1) {
					 RequestDispatcher rd=request.getRequestDispatcher("/User.html");
			         rd.forward(request,response);
				 }
		    	 
			 }
			 else
			 {
		         request.getRequestDispatcher("/home.html").include(request,response);
		         out.println("<script type=\"text/javascript\">");
		         out.print("alert('Invalid Login please try again');");
		         out.println("</script>");
			 }		
		 } catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		  
	}
}