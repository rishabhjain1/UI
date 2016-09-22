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

@WebServlet("/AttributeServlet")
public class AttributeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AttributeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out =response.getWriter();
		int pid = Integer.parseInt(request.getParameter("pid"));
		String att_name = request.getParameter("att_name");
		String att_value = request.getParameter("att_value");
		request.getRequestDispatcher("/specification.html").include(request,response);
		try {
			DBSession dbConnection = new DBSession();
			String query = "SELECT PRODUCT_NAME FROM PRODUCTS WHERE PID="+pid;
			ResultSet rs=dbConnection.runQuery(query);
			rs.next();
			String name = rs.getString("PRODUCT_NAME");
			String query1 = "INSERT INTO ATTRIBUTE_VALUES (PID,ATTRIBUTE,ATT_VALUE) VALUES ("+pid+", '"+att_name+"', '"+att_value+"')";
			dbConnection.runQuery(query1);
			out.println("<script type=\"text/javascript\">");
	        out.print("alert('Attribute added to "+name+"');");
	        out.println("</script>");
		}catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}