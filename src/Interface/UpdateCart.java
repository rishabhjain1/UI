package Interface;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ShoppingDatabase.*;
@WebServlet("/UpdateCart")
public class UpdateCart  extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public UpdateCart() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		HttpSession session=request.getSession(false);
		String ID = (String)session.getAttribute("UserID");
		String search = request.getParameter("search");
		DBOps Db = new DBOps();
		if(search != null ){
			
			try {
				Db.search(request, response, search,ID);
			    Db.update(response, ID, quantity, pid);
			
		        }catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		if(search == null){
			 	try {
					Db.update(response, ID, quantity, pid);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RequestDispatcher rd=request.getRequestDispatcher("/CartServlet");
		        rd.include(request,response);
			}
		}
	}
		