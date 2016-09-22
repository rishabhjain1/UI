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
import javax.servlet.http.HttpSession;

import ShoppingDatabase.*;
@WebServlet("/CartServlet")
public class CartServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public CartServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out =response.getWriter();
		 HttpSession session=request.getSession(false);  
		String ID = (String)session.getAttribute("UserID");
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT CART.PID, PRODUCT_NAME, CART.QUANTITY, PRICE FROM CART JOIN PRODUCTS WHERE CART.PID=PRODUCTS.PID AND USERID='"+ID+"'";
			ResultSet rs = dbConnection.runQuery(query);
			request.getRequestDispatcher("/cart.html").include(request,response);
			//DBOps Db = new DBOps();
			//String html = Db.HTML(ID);
			//out.print(html);
			out.print("<p style= font-size:20px align=right>Welcome " + ID + "!</p>");
			out.print("<center><table class='upd-table'><tr><th>Product Name</th><th>Price</th><th>Quantity</th><th>amount</th></tr>");
			int totalAmount=0;
			while(rs.next()) {
				String productName = rs.getString("PRODUCT_NAME");
				int price=rs.getInt("PRICE");
				int pid=rs.getInt("CART.PID");
				int quantity=rs.getInt("CART.QUANTITY");
				int amount= quantity*price;
				totalAmount = totalAmount+amount;
				out.print("<tr><td><a href = /Shopping/SpecificationServlet?p="+pid+">"+productName+"</a></td><td id ='price'>    "+price+"</td><td id ='price'><form action=UpdateCart method=GET><input name=quantity id=q3 type=search value="+quantity+"  />"+  "<input type=hidden name=pid value="+pid+" /></form></td> <td id ='price'> "+amount+"</td></tr>");
				}
				dbConnection.close();
				out.print("<tr><td></td><td></td><td id ='price'>Total Amount = </td><td id ='price'>"+totalAmount+"</td>");
				out.print("<tr><td id ='price' colspan=4><form action=PurchaseServlet method=GET ><p align=center><input type=submit name = Purchase value=Purchase style=background:SteelBlue;border-radius:10px;color:white;font-weight:700;></p></form></td>");
				out.print("</table><center>");
				
		} catch (ClassNotFoundException e) {
			out.print("HI");
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("I");
			e.printStackTrace();
		}
	}
}
	
		
		