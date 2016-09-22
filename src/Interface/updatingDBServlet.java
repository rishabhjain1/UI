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


@WebServlet("/updatingDBServlet")
public class updatingDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updatingDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("pid"));
		request.getRequestDispatcher("/Update.html").include(request,response);
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT QUANTITY, PRICE FROM PRODUCTS WHERE PID ="+pid;
			ResultSet rs = dbConnection.runQuery(query);
			rs.next();
			int quantity = rs.getInt("QUANTITY");
			int price = rs.getInt("PRICE");
			out.print("<div id ='upd-form'>");
			out.print("<form action ='UpdateValueServlet' method = 'POST'>");
			out.print("<label for= 'pidIn'>PID</label>"
					+"<br>"+"<br>"
					+ "<input type='text' id='pidIn' name='pid' value = "+ pid+"> "
					+ "<label for= 'qntIn'>QUANTITY</label>" +"<br>"+"<br>"
					+ "<input type='text' id='qntIn' name='quantity' value = "+ quantity+"> "
					+ "<label for= 'qntIn'>PRICE</label>" +"<br>"+"<br>"
					+ "<input type='text' id='qntIn' name='price' value = "+ price+"> "
					+ "<input type='submit' value='Submit' id ='up-form-submit'>"
					);		
			out.print("</form>");
			out.print("</div>");
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
