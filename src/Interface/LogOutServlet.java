package Interface;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LogOutServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
       try{ 
    	   PrintWriter out =response.getWriter();
    	   String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
    	   out.print(docType + "<HTML><body align=center><h3><font color= white> Logged Out Successfully.</font></h3></body></HTML>"); 
   		   RequestDispatcher rd=request.getRequestDispatcher("/home.html");
   	       rd.include(request,response);
   		   HttpSession session=request.getSession(false);  
           session.invalidate();
    	  
       }
       catch(Exception e)
       {e.printStackTrace();}

       
	}

}