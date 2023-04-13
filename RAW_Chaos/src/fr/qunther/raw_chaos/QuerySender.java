package fr.qunther.raw_chaos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class QuerySender {

	public static void sendInsertQuery(String query, ArrayList<Object> arguments) {		
	      Connection c = null;
	      
	      try {
	    	 Class.forName("org.postgresql.Driver"); 
	         c = DriverManager.getConnection("jdbc:postgresql://ip:port/db_name", "postgres", "PASSWORD");
	         c.setAutoCommit(false);
	         
	         PreparedStatement prepStmt = c.prepareStatement(query);
	         
	         for(int i=0; i<arguments.size(); i++){
		         prepStmt.setObject(i+1, arguments.get(i));
	         }
   	         prepStmt.executeUpdate();

	         prepStmt.close();
	         c.commit();
	         c.close();
	      } catch (Exception e) {
	         System.out.println(e.getClass().getName()+": "+ e.getMessage());
	      }
	}
	
	public static ArrayList<Object> sendGetterQuery(String query, ArrayList<Object> arguments) {
	      Connection c = null;
	      try {
	    	  
	    	 Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://ip:port/db_name", "postgres", "PASSWORD");
	         c.setAutoCommit(false);

	         PreparedStatement prepStmt = c.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	         
	         for(int i=0; i<arguments.size(); i++){
		         prepStmt.setObject(i+1, arguments.get(i));
	         }

	         ResultSet rs = prepStmt.executeQuery();
	         
	         int i=1;
	         ArrayList<Object> arr = new ArrayList<Object>();
	         
	         while(rs.next()) {
	        	 boolean stop=false;
	        	 while(!stop) {
	        		 try{
			        	 arr.add(rs.getObject(i));
			        	 i++;
			         }catch(Exception e){
			        	 stop=true;
			         }
	        	 }
	         }
	 			    
	         rs.close();
	         prepStmt.close();
	         c.close();

	         return arr;
	         
	      } catch (Exception e) {
	         System.out.println(e.getClass().getName()+": "+ e.getMessage());
	         return null;
	      }
	}
	
	
	
	public static Object[][] sendGetterALLrowsQuery(String query, ArrayList<Object> arguments) {
	      Connection c = null;
	      try {
	    	  
	    	 Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://ip:port/db_name", "postgres", "PASSWORD");
	         c.setAutoCommit(false);

	         PreparedStatement prepStmt = c.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	         
	         for(int i=0; i<arguments.size(); i++){
		         prepStmt.setObject(i+1, arguments.get(i));
	         }

	         ResultSet rs = prepStmt.executeQuery();
	         rs.last();
	         int rows_nb = rs.getRow();
	         rs.beforeFirst();
	         
	         ResultSetMetaData md = rs.getMetaData();
	         int columns_nb = md.getColumnCount();
	         
	         Object[][] arr = new Object[rows_nb][columns_nb];
	         
	         int row=0;
	         int column=1;
	         while(rs.next()) {
	        	 while(column != columns_nb+1) {
		        	 arr[row][column-1] = rs.getObject(column);	     
		        	 column++;
	        	 }
	        	 row++;
	        	 column=1;
	         }
	         
	         
	         rs.close();
	         prepStmt.close();
	         c.close();

	         return arr;
	         
	      } catch (Exception e) {
	         System.out.println(e.getClass().getName()+": "+ e.getMessage());
	         return null;
	      }
	}
	
}
