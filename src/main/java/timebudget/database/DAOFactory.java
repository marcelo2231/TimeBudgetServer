package timebudget.database;

import timebudget.ReportGen;
import timebudget.database.DAOs.CategoryDAO;
import timebudget.database.DAOs.EventDAO;
import timebudget.database.DAOs.UserDAO;
import timebudget.database.interfaces.ICategoryDAO;
import timebudget.database.interfaces.IDAOFactory;
import timebudget.database.interfaces.IEventDAO;
import timebudget.database.interfaces.IUserDAO;
import timebudget.model.Category;
import timebudget.model.DateTimeRange;
import timebudget.model.User;
import timebudget.model.Event;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DAOFactory implements IDAOFactory {


	/**************** Strings for creating and connecting to database **************************/
	private final String DATABASE_URL = "jdbc:sqlite:." +
			File.separator + "plugins" +
			File.separator + "db" +
			File.separator + "tb.sqlite";
	
//	private final String SQL_CREATE_USERS = "CREATE TABLE IF NOT EXISTS users (\n"
////			+ " ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
////			+ " Username text, \n"
////			+ " Password text, \n"
////			+ " Email text, \n"
////			+ " CreatedAt integer"
////			+ ");";
////
////
////	private final String SQL_CREATE_CATEGORIES = "CREATE TABLE IF NOT EXISTS categories (\n"
////			+ " ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
////			+ " user_id integer, \n"
////			+ " deletedAt integer, \n"
////			+ " description text, \n"
////			+ " FOREIGN KEY(user_id) REFERENCES users(ID));";
////
////	private final String SQL_CREATE_EVENTS = "CREATE TABLE IF NOT EXISTS events (\n"
////			+ " ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
////			+ " user_id integer, \n"
////			+ " category_id integer, \n"
////			+ " description text, \n"
////			+ " start_time integer, \n"
////			+ " end_time integer, \n"
////			+ " FOREIGN KEY(user_id) REFERENCES users(ID),\n"
////			+ " FOREIGN KEY(category_id) REFERENCES categories(ID));";

	private final String SQL_CREATE_USERS = "CREATE TABLE users\n" +
											"(id INTEGER NOT NULL,\n" +
											"username VARCHAR(50) NOT NULL,\n" +
											"email VARCHAR(100) NOT NULL,\n" +
											"password VARCHAR(100) NOT NULL,\n" +
											"created_at INTEGER,\n" +
											"PRIMARY KEY (id));";
//
//	// Unused for now, maybe we'll add it alter?
//	private final String SQL_CREATE_AUTH = "CREATE TABLE auth\n" +
//											"(id INTEGER NOT NULL,\n" +
//											"user_id INTEGER NOT NULL,\n" +
//											"expires_at INTEGER NOT NULL,\n" +
//											"PRIMARY KEY (id),\n" +
//											"FOREIGN KEY(user_id) REFERENCES users(id));";
//
//	private final String SQL_CREATE_TIME_PERIODS = "CREATE TABLE time_periods\n" +
//													"(id INTEGER NOT NULL,\n" +
//													"user_id INTEGER NOT NULL,\n" +
//													"start_at INTEGER NOT NULL,\n" +
//													"end_at INTEGER NOT NULL,\n" +
//													"deleted_at INTEGER,\n" +
//													"PRIMARY KEY (id),\n" +
//													"FOREIGN KEY(user_id) REFERENCES users(id));";


	private final String SQL_CREATE_CATEGORIES = "CREATE TABLE categories\n" +
												 "(id INTEGER NOT NULL,\n" +
												 "description VARCHAR(100),\n" +
												 "user_id INTEGER NOT NULL,\n" +
												 "deleted_at INTEGER,\n" +
												 "PRIMARY KEY (id),\n" +
												 "FOREIGN KEY(user_id) REFERENCES users(id));";

	private final String SQL_CREATE_EVENTS = "CREATE TABLE events\n" +
											 "(id INTEGER NOT NULL,\n" +
											 "category_id INTEGER NOT NULL,\n" +
											 "description VARCHAR(100),\n" +
											 "start_at INTEGER NOT NULL,\n" +
											 "end_at INTEGER NOT NULL,\n" +
											 "user_id INTEGER NOT NULL,\n" +
											 "PRIMARY KEY (id),\n" +
											 "FOREIGN KEY(user_id) REFERENCES users(id),\n" +
											 "FOREIGN KEY(category_id) REFERENCES categories(id));";

	public static Connection connection;

	/******************************************************************************************/

	public DAOFactory(){
		File f = new File("." + File.separator + "plugins" + File.separator + "db");
		if(!f.exists())
			f.mkdirs();

		// wipe db every time, avoid carryover state!
		File db = new File("." + File.separator + "plugins" + File.separator + "db" + File.separator + "tb.sqlite");
		if(db.exists())
		    db.delete();

		startTransaction();
		this.createDatabaseTables();
		endTransaction(true);

		startTransaction();
		test();
		endTransaction(false);
	}

	private void test() {
		ICategoryDAO cd = getCategoryDAOInstance();
		IUserDAO ud = getUserDAOInstance();
		IEventDAO ed = getEventDAOInstance();

		Boolean pass = true;

		User u = new User(User.NO_USER_ID, "billy", "billy@gmail.com","password", User.NO_CREATED_AT);
		ud.create(u);

		System.out.println("Testing: user_id = " + String.valueOf(u.getUserID()));

		pass = pass && u.getUserID() != User.NO_USER_ID;

		Category c1 = new Category(Category.NO_CATEGORY_ID, u.getUserID(), "Books yo");
		Category c2 = new Category(Category.NO_CATEGORY_ID, u.getUserID(), "Netflixing");

		/* TODO: Why doesn't create set c's categoryID? */
		cd.create(c1);
		cd.create(c2);

		pass = pass && c1.getCategoryID() != Category.NO_CATEGORY_ID;

		// books events
		Event ev1 = new Event(Event.NO_EVENT_ID, c1.getCategoryID(), "War and Peace", u.getUserID(), 3600, 3600 * 3);
		Event ev2 = new Event(Event.NO_EVENT_ID, c1.getCategoryID(), "Anna Karenina", u.getUserID(), 3600 * 3, 3600 * 9);

		// netflix events
		Event ev3 = new Event(Event.NO_EVENT_ID, c2.getCategoryID(), "Keeping up with the Kards", u.getUserID(), 3600 * 10, 3600 * 11);
		Event ev4 = new Event(Event.NO_EVENT_ID, c2.getCategoryID(), "Stranger Things", u.getUserID(), 3600 * 12, 3600 * 15);

		ed.create(u, ev1);
		ed.create(u, ev2);
		ed.create(u, ev3);
		ed.create(u, ev4);

		List<Event> eventsInRange = ed.getWithinRange(u, new DateTimeRange(0, 3600 * 24));
		try {
			Map<Integer, Float> res = ReportGen.getReport(u, eventsInRange);
			System.out.println(res);
			pass = pass && res.get(c1.getCategoryID()) == 8f;
			pass = pass && res.get(c2.getCategoryID()) == 4f;	
		} catch (Throwable e) { 
			// print stack trace 
			e.printStackTrace(); 
			pass = false;
		} 
	
		System.out.println("Tests passed: " + String.valueOf(pass));
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
			//statement.execute(SQL_CREATE_AUTH);
			//statement.execute(SQL_CREATE_TIME_PERIODS);
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
