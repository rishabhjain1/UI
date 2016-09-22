package ConsoleInterface;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import ShoppingDatabase.DBOperations;
import UsersDatabase.UserDBOperations;


public class Welcome {
			
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		System.out.println("Welcome MSCI Mobile Shopping Portal");
		DBOperations DB_Ops = new DBOperations();
		
		// SOP the categories

		DB_Ops.getCategory();
		System.out.println("");
				
		String brand;
		String category;
		String sub_category;
		int pid;
		int quantity;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			// SOP THE SUBCATEGORIES
			System.out.println("Choose one of above categories: ");
			String str = reader.readLine();
			category = "\'"+ str +"\' ";
			DB_Ops.getSubCategory(category);
			System.out.println("");
			
			// SOP THE BRANDNAMES
	        System.out.println("Enter sub category:");
	        str = reader.readLine();
	        sub_category= str;
	        DB_Ops.getBrands(sub_category);
	        
	        
	        // SOP the models
			 System.out.println("Enter brand name:");
			 str = reader.readLine();
			 brand = "\'"+str+"\'";
			 DB_Ops.getBrandDetails(sub_category, brand);
			 
			 
			 // Purchasing
			 
			 System.out.println("Please enter PID of your choosen model");
			 pid= Integer.parseInt(reader.readLine());
			 System.out.println("Please enter Quantiy");
			 quantity = Integer.parseInt(reader.readLine());
			 DB_Ops.PurchaseItem(sub_category, pid, quantity);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }		
	
	}
}
