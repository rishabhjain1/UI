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

import UsersDatabase.UserDBOperations;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SignUpServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		 UserDBOperations usr_DBOps = new UserDBOperations();	
		 PrintWriter out =response.getWriter();
		 String usr_name=request.getParameter("name");
		 String usr_pass=request.getParameter("pass");
		 boolean check;
		 try {
			check = usr_DBOps.doValidate(usr_name);
			System.out.println(check);
			if(check==false)
		     {
				request.getRequestDispatcher("/home.html").include(request,response);
				out.print("<script type=\"text/javascript\">");
		        out.print("alert('Username exists, try another name');");
		        out.print("</script>");
			 }
		     else
		     {	 
		         usr_DBOps.addAccountDetails(usr_name,usr_pass);
		         request.getRequestDispatcher("/home.html").include(request,response);
		         out.print("<script type=\"text/javascript\">");
		         out.print("alert('Signup successful! please Login');");
		         out.print("</script>");
		     }			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}    
     
    }
}