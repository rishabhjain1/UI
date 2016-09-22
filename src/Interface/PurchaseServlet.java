package Interface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ShoppingDatabase.*;
@WebServlet("/PurchaseServlet")
public class PurchaseServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public PurchaseServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 PrintWriter out =response.getWriter();
		 HttpSession session=request.getSession(false);  
		String ID = (String)session.getAttribute("UserID");
		try {
			
			DBOps db = new DBOps();
			int billNo = db.getBillNo()+1;
			Date d = new Date();
		    SimpleDateFormat ft = new SimpleDateFormat (" dd-MM-yyyy ");
		    SimpleDateFormat ft1 = new SimpleDateFormat ("hh:mm ");
		    String date = ft.format(d).toString();
		    String time = ft1.format(d).toString();
		    DBSession dbConnection = new DBSession();
			String query = "SELECT CART.PID, CART.QUANTITY, PRICE FROM CART JOIN PRODUCTS WHERE CART.PID=PRODUCTS.PID AND USERID='"+ID+"'";
			ResultSet rs = dbConnection.runQuery(query);
			while(rs.next()) {
				DBSession dbConnection1 = new DBSession();
				int price=rs.getInt("PRICE");
				int pid=rs.getInt("CART.PID");
				int quantity=rs.getInt("CART.QUANTITY");
				String query1 ="INSERT INTO PURCHASE_HISTORY (BILL_NO, userID, PID, quantity, price, DATE, TIME) VALUES ("+billNo+", '"+ID+"', "+pid+", "+quantity+", "+price+", '"+date+"', '"+time+"')";
				String query2 ="DELETE FROM CART WHERE pid = "+pid+" AND USERID='"+ID+"'";
				db.setNcheckQuantity(pid, quantity);
				dbConnection1.runQuery(query1);
				dbConnection1.runQuery(query2);
				dbConnection1.close();
				
			}
			dbConnection.close();
			DBSession dbConnection3 = new DBSession();
			//DBOps Db = new DBOps();
			//String html = Db.HTML(ID);
			//out.print(html);
			out.print("<p style= font-size:20px align=right>Welcome " + ID + "!</p>");
			request.getRequestDispatcher("/cart.html").include(request,response);
			String query3 = "SELECT DISTINCT BILL_NO, DATE, TIME FROM PURCHASE_HISTORY WHERE USERID='"+ID+"' ORDER BY BILL_NO ASC";
			ResultSet rs3 = dbConnection3.runQuery(query3);			
			out.print("<center><table class='upd-table'><tr><th>Bill No.</th><th>Amount</th><th>Date</th></tr>");
			while(rs3.next()) {
			int billNo1 = rs3.getInt("BILL_NO");
			String Date1= rs3.getString("DATE");
			DBSession dbConnection4 = new DBSession();
			String query4 = "SELECT SUM(QUANTITY*PRICE) AS Amount FROM PURCHASE_HISTORY WHERE BILL_NO="+billNo1+" AND USERID='"+ID+"'";
			ResultSet rs4 = dbConnection4.runQuery(query4);
			rs4.next();
			int amount = rs4.getInt(1);
			out.print("<tr><td id ='price'><a href = /Shopping/BillServlet?p="+billNo1+"&a="+amount+">"+billNo1+"</a></td><td id ='price'>    "+amount+"</td><td id ='price'>     "+Date1+"</td></tr>");
			dbConnection4.close();
			}
			out.print("</table></center></body></html>");
			dbConnection3.close();
			
		}catch (ClassNotFoundException e) {
			out.print("HI");
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("I");
			e.printStackTrace();
		}
	}
}
			
				
				