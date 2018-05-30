package dataBase;

import java.sql.*;
import java.util.Scanner;

public class DataBase implements Command {
	
	static Scanner reader= new Scanner(System.in);
	
	private static volatile DataBase db = null;
	
	private DataBase(){
		
	}
	
	public static DataBase getInstance() {
		if(db == null){
			synchronized(DataBase.class){
				if(db == null)
					db = new DataBase();
			}
		}
		return db;
	}
	
	public void makeConnection(String query) {
		try(	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?useSSL=false", "root", "ahmedmirza");
				PreparedStatement pstmt = conn.prepareStatement(query) ;
				) 
		{
			System.out.println("The SQL query is: " + query); 
	        System.out.println();
			executeQuery(pstmt);
	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void executeQuery(PreparedStatement pstmt){
        
        try {
        	
        	ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            
            int numColumns = meta.getColumnCount();
            
            for(int i=1; i<= numColumns; i++) {
            	System.out.print(meta.getColumnName(i) + "\t");
            }
            
            System.out.println("\n");
            
            while(resultSet.next()){
            	for(int i=1; i<= numColumns; i++){
            		System.out.print(resultSet.getString(i) + "\t");
            	}
            	System.out.println();
            }
            
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getQuery() {
		boolean running = true;
		while(running){
			System.out.print("Enter Query: ");
			String query = reader.nextLine();
			makeConnection(query);
		}
		
	}
	
	@Override
	public void executeQuery() {
		getQuery();
	}
	
	

	
	
}
