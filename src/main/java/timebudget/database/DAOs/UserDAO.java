package timebudget.database.DAOs;

import timebudget.database.DAOFactory;
import timebudget.database.interfaces.IUserDAO;
import timebudget.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDAO implements IUserDAO {
	
	@Override
	public boolean create(User user) {
		String sql = "INSERT INTO Users(Username, Password, Email, CreatedAt) VALUES(?,?,?,?)";
		
		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setInt(4, user.getCreatedAt());
			
			preparedStatement.executeUpdate();
			String idSql = "SELECT last_insert_rowid()";
			Statement statement = DAOFactory.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(idSql);
			int id = resultSet.getInt("last_insert_rowid()");
			user.setUserID(id);
			
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Updates a user in the users table.
	 * @param user a user with the new data and the same auto generated id for the
	 *            user to be updated.
	 * @return boolean indicating whether or not update occured.
	 */
	@Override
	public boolean update(User user) {
		String sql = "UPDATE users SET Username = ? ,"
				+ " Password = ?,"
				+ " Email = ?,"
				+ " CreatedAt = ? "
				+ " WHERE ID = ?";
		
		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setInt(4, user.getCreatedAt());
			preparedStatement.setInt(5, user.getUserID());
			preparedStatement.executeUpdate();
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Method to return all Users in the users table
	 * @return a list of UserDTO objects representing all users currently stored in table.
	 */
	@Override
	public List<User> getAll() {
		String sql = "SELECT ID, Username, Password, Email, CreatedAt FROM users ORDER BY ID";
		try {
			Statement statement = DAOFactory.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return parseResultsSet(resultSet);
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}
	
	private List<User> parseResultsSet(ResultSet resultSet) throws SQLException {
		ArrayList<User> userList = new ArrayList<>();
		while(resultSet.next()){
			User user = new User(resultSet.getInt("ID"),
					resultSet.getString("Username"),
					resultSet.getString("Password"),
					resultSet.getString("Email"),
					resultSet.getInt("CreatedAt"));
			userList.add(user);
		}
		return userList;
	}
	
	@Override
	public boolean delete(User user) {
		String sql = "DELETE FROM users WHERE ID = ?";
		
		try(PreparedStatement statement = DAOFactory.connection.prepareStatement(sql)){
			statement.setInt(1, user.getUserID());
			
			statement.executeUpdate();
			return true;
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean clear() {
		String sql_drop = "DROP TABLE IF EXISTS users";
		String sql_create = "CREATE TABLE users (\n"
				+ " ID integer PRIMARY KEY AUTOINCREMENT,\n"
				+ " Username text, \n"
				+ " Password text, \n"
				+ " Email text, \n"
				+ " CreatedAt integer \n"
				+ ");";
		
		try(Statement stmt_drop = DAOFactory.connection.createStatement();
		    Statement stmt_create = DAOFactory.connection.createStatement()){
			stmt_drop.execute(sql_drop);
			stmt_create.execute(sql_create);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
}

