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
@WebServlet("/SpecificationServlet")
public class SpecificationServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public SpecificationServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("p"));
		HttpSession session=request.getSession(false);  
		String ID = (String)session.getAttribute("UserID");
		try {
			
			DBSession dbConnection = new DBSession();
			String query1 = "SELECT PRODUCT_NAME FROM PRODUCTS WHERE PID="+pid;
			ResultSet rs1 = dbConnection.runQuery(query1);
			rs1.next();
			String name = rs1.getString("PRODUCT_NAME");
			String query = "SELECT ATTRIBUTE,ATT_VALUE FROM ATTRIBUTE_VALUES WHERE PID="+pid;
			ResultSet rs = dbConnection.runQuery(query);
			request.getRequestDispatcher("/specification.html").include(request,response);
			//DBOps Db = new DBOps();
			//String html = Db.HTML(ID);
			//out.print(html);
			out.print("<p style= font-size:20px align=right>Welcome " + ID + "!</p>");
			out.print("<center><table class='upd-table'><tr><th colspan=2>"+name+"</th></tr><tr><th>Attribute</th><th>Attribute_Value</th></tr>");
			
			while(rs.next()) {
				out.print("<tr><td id ='price'>" + rs.getString("ATTRIBUTE")+ "</td><td id ='price'>" +rs.getString("ATT_VALUE")+"</td></tr>");
			}
			dbConnection.close();
			out.print("</table></center>");
			out.print("<form action= UpdateCart method = GET><p align=center><input type=number name=quantity placeholder=EnterQuantity >  <input type=submit value=Addtocart style=background: SteelBlue;border-radius:10px;color: white; ><input type=hidden name=pid value="+pid+" ></p> </form>");
		} catch (ClassNotFoundException e) {
			out.print("HI");
			e.printStackTrace();
		} catch (SQLException e) {
			out.print("I");
			e.printStackTrace();
		}
		out.print("</body></html>");
		
	}
}