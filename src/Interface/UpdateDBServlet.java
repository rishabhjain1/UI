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

@WebServlet("/UpdateDBServlet")
public class UpdateDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UpdateDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out =response.getWriter();
		String search = request.getParameter("search");
		request.getRequestDispatcher("/Update.html").include(request,response);
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT DISTINCT PRODUCTS.PID,QUANTITY, PRODUCT_NAME,PRICE FROM PRODUCTS JOIN PRODUCT_CATEGORY ON PRODUCTS.PID=PRODUCT_CATEGORY.PID WHERE PRODUCT_NAME LIKE '%"+search+"%'OR CATEGORY LIKE '%"+search+"%'";
			ResultSet rs = dbConnection.runQuery(query);
			//out.print("hi");
			//out.print("<html><head><meta charset=ISO-8859-1><title>Insert title here</title></head><body>");
			out.print("<table class='upd-table'><tr><th>Product Name</th><th>Price</th><th>Quantity</th><th colspan = '4'>Action</th></tr>");
			
			while(rs.next()) {
				String productName = rs.getString("PRODUCT_NAME");
				int price=rs.getInt("PRICE");
				int quantity=rs.getInt("QUANTITY");
				int pid=rs.getInt("PRODUCTS.PID");				
				out.print("<tr>"
							+ "<td><a href = /Shopping/SpecificationServlet?p="+pid+">"+productName+"</a></td>"
							+ "<td id ='price'>"+price+"</td>"
							+ "<td id = 'quant'>"+ quantity+"</td>"
							+ "<td id = 'btn'><a href = /Shopping/updatingDBServlet?pid="+pid+"><button class='w3-btn w3-blue'> UPDATE</button></a></td>"							
							+ "<td id = 'btn'><a href = /Shopping/deletingDBServlet?pid="+pid+"><button class='w3-btn w3-red'> DELETE</button></a></td>"
						+"</tr>");
				}
				dbConnection.close();
				out.print("</table>");
			} catch (ClassNotFoundException e) {
				out.print("HI");
				e.printStackTrace();
			} catch (SQLException e) {
				out.print("I");
				e.printStackTrace();
			}
	}

}
