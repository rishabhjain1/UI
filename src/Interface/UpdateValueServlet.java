package Interface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ShoppingDatabase.DBSession;
import UsersDatabase.UserDBOperations;

@WebServlet("/UpdateValueServlet")
public class UpdateValueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public UpdateValueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	
		 PrintWriter out =response.getWriter();
		 int pid = Integer.parseInt(request.getParameter("pid"));
		 int quantity = Integer.parseInt(request.getParameter("quantity"));
		 int price = Integer.parseInt(request.getParameter("price"));
		 request.getRequestDispatcher("/Update.html").include(request,response);
		 try {
			 DBSession dbConnection = new DBSession();
			 String query = "UPDATE PRODUCTS SET QUANTITY ="+quantity +", PRICE ="+price +" WHERE PID ="+pid;
			 ResultSet rs = 	dbConnection.runQuery(query);
			 out.print("<div id ='del-msg'><h1 > successfully Updated </h1>"
						+ "</div>");
			 dbConnection.close();
		 }catch (ClassNotFoundException e) {
				out.print("HI");
				e.printStackTrace();
			} catch (SQLException e) {
				out.print("I");
				e.printStackTrace();
			}
		 
		 
	}

}
