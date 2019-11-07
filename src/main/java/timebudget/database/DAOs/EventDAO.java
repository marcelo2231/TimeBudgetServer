package timebudget.database.DAOs;

import timebudget.database.interfaces.IEventDAO;
import timebudget.model.Event;
import timebudget.model.TimePeriod;
import timebudget.model.User;

import java.util.List;

import timebudget.database.DAOFactory;
import timebudget.model.DateTimeRange;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


//import java.time.Instant;
import java.util.ArrayList;

/*
	Error Reporting: return null or throw exceptions?

CREATE TABLE events
(id INTEGER NOT NULL,
category_id INTEGER NOT NULL,
description VARCHAR(100),
start_at INTEGER NOT NULL,
end_at INTEGER NOT NULL,
user_id INTEGER NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(category_id) REFERENCES categories(id));

*/

public class EventDAO implements IEventDAO {
	@Override
	public boolean create(User user, Event event) {
		String sql = "INSERT INTO events (category_id, description, start_at, end_at, user_id) VALUES(?,?,?,?,?)";

		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setInt(1, event.getCategoryID());
			preparedStatement.setString(2, event.getDescription());
			preparedStatement.setInt(3, event.getStartAt());
			preparedStatement.setInt(4, event.getEndAt());
			preparedStatement.setInt(5, event.getUserID());

			preparedStatement.executeUpdate();
			String idSql = "SELECT last_insert_rowid()";
			Statement statement = DAOFactory.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(idSql);
			int id = resultSet.getInt("last_insert_rowid()");
			event.setEventID(id);
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean update(User user, Event event) {
		String sql = "UPDATE events SET category_id = " + String.valueOf(event.getCategoryID()) +
									  ", description = \"" + event.getDescription() + "\", " + 
									  " start_at = " + String.valueOf(event.getStartAt()) +
									  ", end_at = " + String.valueOf(event.getEndAt()) + 
									  " WHERE id = " + String.valueOf(event.getEventID()) +
									  " and user_id = " + String.valueOf(user.getUserID());

		try(Statement statement = DAOFactory.connection.createStatement()){
			statement.executeUpdate(sql);
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<Event> getAllForUser(User user) {
		// Painful workaround for 'not implemented by SQLite JDBC driver'
		String sql = "SELECT id, category_id, description, start_at, end_at, user_id FROM events " +
						" WHERE user_id = " + String.valueOf(user.getUserID()) + 
					    " ORDER BY start_at";

		try {
			Statement statement = DAOFactory.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return parseResultsSet(resultSet);

			// PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql);
			// preparedStatement.setInt(1, user.getUserID());
			// List<Event> ar = parseResultsSet(preparedStatement.executeQuery(sql));
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return null;
		}			
	}

	@Override
	public List<Event> getByTimePeriod(User user, TimePeriod timePeriod) {
		/* DEMO 2
		   check to see if timePeriod has start_at and end_at 
			 - if not, fetch it from the database
			
		   return the result of getWithinRange
		*/
		return null;
	}

	@Override
	public List<Event> getWithinRange(User user, DateTimeRange range) {
		// Painful workaround for 'not implemented by SQLite JDBC driver'
		String sql = "SELECT id, category_id, description, start_at, end_at, user_id FROM events " +
						" WHERE " + 
							   " user_id = " + String.valueOf(user.getUserID()) + 
							" and end_at > " + String.valueOf(range.getStartAt()) + 
						  " and start_at < " + String.valueOf(range.getEndAt()) + 
						" ORDER BY start_at";

		try {
			Statement statement = DAOFactory.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return parseResultsSet(resultSet);

			// I'm getting a `not implemented by SQLite JDBC driver` error when using PreparedStatement for select queries
			// PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql);
			// preparedStatement.setInt(1, user.getUserID());
			// preparedStatement.setInt(1, range.getStartAt());
			// preparedStatement.setInt(2, range.getEndAt());
			// List<Event> ar = parseResultsSet(preparedStatement.executeQuery(sql));
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return null;
		}			
	}

	@Override
	public Event getByID(User user, int id) {
		// public List<Category> getAllForUser(int userID) {
		String sql = "SELECT id, category_id, description, start_at, end_at, user_id FROM events" +
						" WHERE user_id = " + String.valueOf(user.getUserID()) + 
						  " and id = " + String.valueOf(id);

		try {
			Statement statement = DAOFactory.connection.createStatement();
		
			ResultSet results = statement.executeQuery(sql);
			List<Event> ar = parseResultsSet(results);
			if (ar.size() > 0)
				return ar.get(0);
			else 
				return null;

		} catch (SQLException e){
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean delete(User user, int eventID) {
		String sql = "DELETE from events WHERE id = ? and user_id = ?";

		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setInt(1, eventID);
			preparedStatement.setInt(2, user.getUserID());
			preparedStatement.executeUpdate();
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}


	public boolean clear() {
		String sql = "DELETE FROM categories;";

		try(Statement statement = DAOFactory.connection.createStatement()){
			statement.execute(sql);
			return true;
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
	}


	private List<Event> parseResultsSet(ResultSet resultSet) throws SQLException {
		ArrayList<Event> categoryList = new ArrayList<>();
		while(resultSet.next()){
			Event Event = new Event(
				resultSet.getInt("id"), 
				resultSet.getInt("category_id"), 
				resultSet.getString("description"),
				resultSet.getInt("user_id"),
				resultSet.getInt("start_at"),
				resultSet.getInt("end_at")
			);
			categoryList.add(Event);
		}
		return categoryList;
	}
}