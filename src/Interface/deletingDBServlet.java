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

@WebServlet("/deletingDBServlet")
public class deletingDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public deletingDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("pid"));
		request.getRequestDispatcher("/Update.html").include(request,response);
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT PRODUCT_NAME FROM PRODUCTS WHERE PID ="+pid;
			ResultSet rs = dbConnection.runQuery(query);
			rs.next();
			String product_name = rs.getString("PRODUCT_NAME");
			String query1 = "DELETE FROM PRODUCTS WHERE PID="+pid;
			ResultSet rs1 = dbConnection.runQuery(query1);
			out.print("<div id ='del-msg'><h1 > successfully removed the product: </h1>"
					+ "<h2>"+ product_name +"</h2>"
					+ "</div>");
			dbConnection.close();
			
		} catch (ClassNotFoundException e) {
			out.print("HI");
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("I");
			e.printStackTrace();
		}	
	}

}
