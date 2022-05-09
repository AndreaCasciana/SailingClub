package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * class to connect to database
 *
 */
public class ConnectDB {
	
	private static Connection connection;
	
	/**
	 * Initialise and creates connection with the database
	 */
	public ConnectDB() {
		connection = null;
		createConnection();
	}
	
	/**
	 * @return database connection
	 */
	public Connection getConnection( ) {
		return connection;
	}
	
	/**
	 * creates connection to the database provided in the file "DBConnectionDetails.txt"
	 * @return true if the connection is successful, otherwise it returns false
	 */
	public boolean createConnection() {
		try {
			
			File dbCon = new File("./src/utility/DBConnectionDetails.txt");
			Scanner readFile = new Scanner(dbCon);
			
			String DBURL = readFile.nextLine();
			String ARGS = readFile.nextLine();
			String LOGIN = readFile.nextLine();
			String PWD = readFile.nextLine();
			
			readFile.close();
			connection = DriverManager.getConnection(DBURL + ARGS, LOGIN, PWD);
			
		}catch(FileNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(connection != null &&  !createTable())
			return false;
		
		return true;
	}

	/**
	 * creates the tables in DBTables.txt to database if not created
	 * @return true if the creation is successful, otherwise it returns false
	 */
	private boolean createTable() {

		try {
			
			Statement statement = connection.createStatement();
			
			File tableQuery = new File("./src/utility/DBTables.txt");
			Scanner readFile = new Scanner(tableQuery);
			readFile.useDelimiter("\\Z");  
			statement.executeUpdate(readFile.next());
			readFile.close();
			SQLWarning warning = statement.getWarnings();
			if(warning == null) {
				tableQuery = new File("./src/utility/DBInsert.txt");
				readFile = new Scanner(tableQuery);
				readFile.useDelimiter("\\Z");  
				statement.executeUpdate(readFile.next());
				
				readFile.close();
			}
			
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * executes an sql query given
	 * @param query the string containing the sql query
	 * @return the database resultSet
	 */
	public ResultSet executeQuery(String query) {
		if(connection == null)
			return null;

		ResultSet result = null;
		try {
			
			Statement statement = connection.createStatement();  
			result = statement.executeQuery(query);
			if(!result.next())
				result = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * executes an sql query for updating datas in the database
	 * @param query the string containing the sql query
	 * @return true if successful
	 */
	public Boolean executeUpdate(String query) {
		if(connection == null)
			return false;
		try {
			Statement statement = connection.createStatement();
			if(statement.executeUpdate(query) > 0);
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return false;
	}

}
