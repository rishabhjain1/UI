package Interface;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ShoppingDatabase.DBOps;
@WebServlet("/SearchServlet")
public class SearchServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public SearchServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		HttpSession session=request.getSession(false);
		String ID;
		if(session != null){
		ID = (String)session.getAttribute("UserID");
		} 
		else{
		ID = "Guest";
		}
		DBOps Db = new DBOps();
		try {
			Db.search(request, response, search,ID);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			/*try {
				DBSession dbConnection = new DBSession();
				String query = "SELECT DISTINCT PRODUCTS.PID, PRODUCT_NAME,PRICE FROM PRODUCTS JOIN PRODUCT_CATEGORY ON PRODUCTS.PID=PRODUCT_CATEGORY.PID WHERE PRODUCT_NAME LIKE '%"+search+"%'OR CATEGORY LIKE '%"+search+"%'";
				ResultSet rs = dbConnection.runQuery(query);
				request.getRequestDispatcher("/search.html").include(request,response);
				DBOps Db = new DBOps();
				//String html = Db.HTML(ID);
				//out.print(html);
				//out.print("<center>");			
				out.print("<table class='upd-table'><tr><th>Product Name</th><th>Price</th><th>Quantity</th></tr>");
				
				while(rs.next()) {
					String productName = rs.getString("PRODUCT_NAME");
					int price=rs.getInt("PRICE");
					int pid=rs.getInt("PRODUCTS.PID");
					out.print("<tr>"
							+ "<td><a href = /Shopping/SpecificationServlet?p="+pid+">"+productName+"</a></td>"
							+ "<td id ='price'>"+price+"</td>"
							+"<td id = 'btn'><form action=UpdateCart method=GET><input type=number name=quantity > <input type=hidden name=pid value="+pid+"> <input type=hidden name=search value="+search+">  <input type=submit value=AddToCart ></form></td></tr>");
					}
					dbConnection.close();
					out.print("</table>");
					out.print("</center>");
				} catch (ClassNotFoundException e) {
					out.print("HI");
					e.printStackTrace();
				} catch (SQLException e) {
					out.print("I");
					e.printStackTrace();
				}*/
		}
}
