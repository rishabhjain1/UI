package ShoppingDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBOps {

	private DBSession dbConnection ;
		
	// method to purchase item
		public int PurchaseItem(int pid, int quantity) throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
			dbConnection = new DBSession();
			int tmpQuantity;
			String query = "SELECT QUANTITY FROM PRODUCTS WHERE PID =" + pid;
			ResultSet rs = dbConnection.runQuery(query);
			rs.next();
			tmpQuantity = rs.getInt(1);
			dbConnection.close();
			if (tmpQuantity >= quantity){
			//	setNcheckQuantity(pid, quantity);
				return -1;
			}
			else {
				return tmpQuantity;
			}
			
		}
		
	// to update the quantity after purchase
		public void setNcheckQuantity(int pid, int quantity) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();
			int tmpQuantity;
			String query = "SELECT QUANTITY FROM PRODUCTS WHERE PID =" + pid;
			ResultSet rs = dbConnection.runQuery(query);
			rs.next();
			tmpQuantity = rs.getInt(1);
			tmpQuantity = tmpQuantity - quantity;
			String query1 = "UPDATE PRODUCTS SET QUANTITY ="+tmpQuantity +" WHERE PID="+ pid;
			dbConnection.runQuery(query1);
			dbConnection.close();
		}
		public void insertCart(int pid, String user, int quantity) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			String query = "INSERT INTO CART (PID, USERID, QUANTITY) VALUES ("+pid+",'"+user+"',"+quantity+")";
			dbConnection.runQuery(query);
			dbConnection.close();
		}
		public int getQuantity(int pid, String user) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			String query = "SELECT COUNT(*) FROM CART WHERE PID="+pid+" AND USERID='"+user+"'";
			ResultSet rs=dbConnection.runQuery(query);
			rs.next();
			int tmpQuantity=rs.getInt(1);
			return tmpQuantity;
					
			
		}

		public void updateCart(int pid, String user, int quantity) throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			if (quantity==0){
				String query = "DELETE FROM CART WHERE pid = "+pid+"";
				dbConnection.runQuery(query);
			}
			else{
			String query = "UPDATE CART SET QUANTITY= "+quantity+" where pid = "+pid+" and userid= '"+user+"'";
			dbConnection.runQuery(query);
			}
			dbConnection.close();
		}
		
		public int getBillNo() throws ClassNotFoundException, SQLException {
			dbConnection = new DBSession();	
			String query = "SELECT COUNT(distinct BILL_NO) FROM PURCHASE_HISTORY;";
			ResultSet rs=dbConnection.runQuery(query);
			rs.next();
			int count=rs.getInt(1);
			return count;
					
			
		}

		public void search(HttpServletRequest request, HttpServletResponse response, String search, String ID) throws ClassNotFoundException, SQLException, IOException, ServletException{
				PrintWriter out =response.getWriter();
				DBSession dbConnection = new DBSession();
				String query = "SELECT DISTINCT PRODUCTS.PID, PRODUCT_NAME,PRICE FROM PRODUCTS JOIN PRODUCT_CATEGORY ON PRODUCTS.PID=PRODUCT_CATEGORY.PID WHERE PRODUCT_NAME LIKE '%"+search+"%'OR CATEGORY LIKE '%"+search+"%'";
				ResultSet rs = dbConnection.runQuery(query);
				request.getRequestDispatcher("/search.html").include(request,response);	
				out.print("<p style= font-size:20px align=right>Welcome " + ID + "!</p>");
				out.print("<table class='upd-table'><tr><th>Product Name</th><th>Price</th><th>Quantity</th></tr>");
				
				while(rs.next()) {
					String productName = rs.getString("PRODUCT_NAME");
					int price=rs.getInt("PRICE");
					int pid=rs.getInt("PRODUCTS.PID");
					out.print("<tr>"
							+ "<td><a href = /Shopping/SpecificationServlet?p="+pid+">"+productName+"</a></td>"
							+ "<td id ='price'>"+price+"</td>"
							+"<td id = 'btn'><form action=UpdateCart method=GET><input type=number name=quantity > <input type=hidden name=pid value="+pid+"> <input type=hidden name=search value="+search+">  <input type=submit id=s1 value=AddToCart ></form></td></tr>");
					}
					dbConnection.close();
					out.print("</table>");
					out.print("</center>");
		}
		public void update(HttpServletResponse response, String ID, int quantity, int pid) throws IOException, ClassNotFoundException, SQLException{
			PrintWriter out =response.getWriter();
			DBOps db = new DBOps();
			int count =db.getQuantity(pid, ID);
			if(count>0){
				try {
				int result;
				result = db.PurchaseItem(pid,quantity);
				if (result==-1){
					db.updateCart(pid,ID,quantity);
					out.println("<script type=\"text/javascript\">");
			        out.print("alert('Product already in cart, Quantity updated');");
			        out.println("</script>");
				}
				else{
					out.println("<script type=\"text/javascript\">");
			        out.print("alert('enter quantity less than "+ result+"')");
			        out.println("</script>");
				}
				}
				catch (NumberFormatException | ClassNotFoundException | SQLException e) {
						
						e.printStackTrace();
					}
			}
			
			else{
				try {
					int result;
					result = db.PurchaseItem(pid,quantity);
					if (result==-1){
						db.insertCart(pid,ID,quantity);
						out.println("<script type=\"text/javascript\">");
				        out.print("alert('Product added to cart')");
				        out.println("</script>");
						}
					else{
						out.println("<script type=\"text/javascript\">");
				        out.print("alert('enter quantity less than "+ result+"')");
				        out.println("</script>");
					}
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
}
