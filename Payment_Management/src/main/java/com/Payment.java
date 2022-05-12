package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class Payment 
{ //A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", ""); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		
		public String insertPayment(String code, String cus_id, String month, String bill, String date){ 
			
					String output = ""; 
					
					try
					{ 
						Connection con = connect(); 
						
						if (con == null) 
						{
							return "Error while connecting to the database for inserting."; 
							
						} 
						// create a prepared statement
						
						String query = "insert into payment_form (`payment_id`,`payment_code`,`customer_id`,`month_of_bill`,`bill_amount`,`date`)"+" values (?, ?, ?, ?, ?, ?)"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						
						// binding values
						preparedStmt.setInt(1, 0); 
						preparedStmt.setString(2, code); 
						preparedStmt.setString(3, cus_id); 
						preparedStmt.setString(4, month); 
						preparedStmt.setString(5, bill); 
						preparedStmt.setString(6, date); 
						
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						
						String newPayments = readPayments(); 
						output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 
					} 
					
					catch (Exception e) 
					{ 
						output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}"; 
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
		
		
		
		public String readPayments() 
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
		 if (con == null) 
		 { 
			 return "Error while connecting to the database for reading."; 
		 } 
		 // Prepare the html table to be displayed
		 output = "<table border=\"1\" class=\"table\"><tr><th>Payment Code</th>"
		 		+ "<th>Customer ID</th><th>Month of Bill</th>"
		 		+ "<th>Amount</th>"
		 		+ "<th>Date</th>"
		 		+ "<th>Update</th>"
		 		+ "<th>Remove</th></tr>"; 
		
		 String query = "select * from payment_form"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
			 String payment_id = Integer.toString(rs.getInt("payment_id")); 
				String payment_code = rs.getString("payment_code"); 
				String customer_id = rs.getString("customer_id"); 
				String month_of_bill = rs.getString("month_of_bill"); 
				String bill_amount = rs.getString("bill_amount"); 
				String date = rs.getString("date"); 
		 
				// Add into the html table
		 output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='"+payment_id+"'>"+payment_code+"</td>"; 
		 output += "<td>" + customer_id + "</td>"; 
		 output += "<td>" + month_of_bill + "</td>"; 
		 output += "<td>" + bill_amount + "</td>"; 
		 output += "<td>" + date + "</td>"; 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 + "class='btnUpdate btn btn-secondary' data-paymentid='" + payment_id + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' "
				 + "class='btnRemove btn btn-danger' data-paymentid='" + payment_id + "'></td></tr>"; 
		 
		 } 
		 con.close(); 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 
		catch (Exception e) 
		 { 
		 output = "Error while reading the payments."; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}

			
			
			public String updatePayment(String ID, String code, String cus_id, String month, String bill, String date){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								return "Error while connecting to the database for updating.";
								} 
							// create a prepared statement
							String query = "UPDATE payment_form SET payment_code=?,customer_id=?,month_of_bill=?,bill_amount=?,date=? WHERE payment_id=?"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							
							// binding values
							preparedStmt.setString(1, code); 
							preparedStmt.setString(2, cus_id); 
							preparedStmt.setString(3, month); 
							preparedStmt.setString(4, bill); 
							preparedStmt.setString(5, date); 
							preparedStmt.setInt(6, Integer.parseInt(ID)); 
							
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							String newPayments = readPayments();
							output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 

					} 
					
					catch (Exception e){ 
						
						output = "{\"status\":\"error\",\"data\":\"Error while updating the payment.\"}"; 

						System.err.println(e.getMessage()); 
						
					} 
					
					return output; 
			} 
			
			
			public String deletePayment(String payment_id){ 
				
					String output = ""; 
					
					try{ 
						Connection con = connect(); 
						
						if (con == null){
							return "Error while connecting to the database for deleting."; 
							} 
						
						// create a prepared statement
						String query = "delete from payment_form where payment_id=?"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(payment_id)); 
						
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						String newPayments = readPayments(); 
						 output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 

					} 
					
					catch (Exception e){ 
						output = "{\"status\":\"error\",\"data\":\"Error while deleting the payment.\"}";
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
			
			
			
			
} 
