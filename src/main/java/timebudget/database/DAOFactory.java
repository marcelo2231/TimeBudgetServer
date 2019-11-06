package timebudget.database;

import timebudget.database.DAOs.CategoryDAO;
import timebudget.database.DAOs.EventDAO;
import timebudget.database.DAOs.UserDAO;
import timebudget.database.interfaces.ICategoryDAO;
import timebudget.database.interfaces.IDAOFactory;
import timebudget.database.interfaces.IEventDAO;
import timebudget.database.interfaces.IUserDAO;
import timebudget.model.Category;

import java.io.File;
import java.sql.*;

public class DAOFactory implements IDAOFactory {

	
	/**************** Strings for creating and connecting to database **************************/
	private final String DATABASE_URL = "jdbc:sqlite:." +
			File.separator + "plugins" +
			File.separator + "db" +
			File.separator + "tb.sqlite";
	
	private final String SQL_CREATE_USERS = "CREATE TABLE IF NOT EXISTS users (\n"
			+ " ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
			+ " Username text, \n"
			+ " Password text, \n"
			+ " Email text, \n"
			+ " CreatedAt integer"
			+ ");";


	private final String SQL_CREATE_CATEGORIES = "CREATE TABLE IF NOT EXISTS categories (\n"
			+ " ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
			+ " user_id integer, \n"
			+ " deletedAt integer, \n"
			+ " description text, \n"
			+ " FOREIGN KEY(user_id) REFERENCES users(ID));";

	private final String SQL_CREATE_EVENTS = "CREATE TABLE IF NOT EXISTS events (\n"
			+ " ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
			+ " user_id integer, \n"
			+ " category_id integer, \n"
			+ " description text, \n"
			+ " start_time integer, \n"
			+ " end_time integer, \n"
			+ " FOREIGN KEY(user_id) REFERENCES users(ID),\n"
			+ " FOREIGN KEY(category_id) REFERENCES categories(ID));";


	public static Connection connection;
	
	/******************************************************************************************/
	
	public DAOFactory(){
		File f = new File("." + File.separator + "plugins" + File.separator + "db");
		if(!f.exists())
			f.mkdirs();
		
		startTransaction();
		this.createDatabaseTables();
		endTransaction(true);
	}
	@Override
	public IUserDAO getUserDAOInstance() {
		return new UserDAO();
	}
	
	@Override
	public IEventDAO getEventDAOInstance() {
		return new EventDAO();
	}
	
	@Override
	public ICategoryDAO getCategoryDAOInstance() {
		return new CategoryDAO();
	}
	
	
	@Override
	public boolean startTransaction() {
		try {
			assert(connection == null);
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean endTransaction(boolean commit) {
		if(connection != null){
			try{
				if(commit){
					connection.commit();
				}else{
					connection.rollback();
				}
			}catch(SQLException e){
				System.err.println("Could not end transaction");
				e.printStackTrace();
				return false;
			}finally{
				safeClose(connection);
				connection = null;
			}
			return true;
		}
		return false;
	}
	
	private void createDatabaseTables(){
		try(Connection connection = DriverManager.getConnection(DATABASE_URL);
		    Statement statement = connection.createStatement()) {
			statement.execute(SQL_CREATE_USERS);
			statement.execute(SQL_CREATE_CATEGORIES);
			statement.execute(SQL_CREATE_EVENTS);
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	public static void safeClose(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException e){
			
			}
		}
	}
	
	public static void safeClose(Statement stmt){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException e){
			
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
}
