package timebudget.database.DAOs;

import timebudget.database.DAOFactory;
import timebudget.database.interfaces.ICategoryDAO;
import timebudget.model.Category;
import timebudget.model.User;

import java.util.List;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


//import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/*
	Error Reporting: return null or throw exceptions?

*/

public class CategoryDAO implements ICategoryDAO {
	@Override
	public boolean create(Category category) {
		String sql = "INSERT INTO categories (description, user_id) VALUES(?,?)";

		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setString(1, category.getDescription());
			preparedStatement.setInt(2, category.getUserID());

			preparedStatement.executeUpdate();
			String idSql = "SELECT last_insert_rowid()";
			Statement statement = DAOFactory.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(idSql);
			int id = resultSet.getInt("last_insert_rowid()");
			category.setCategoryID(id);

		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Category category) {
		String sql = "UPDATE categories SET description = ? WHERE id = ?";

		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setString(1, category.getDescription());
			preparedStatement.setInt(2, category.getCategoryID());
			preparedStatement.executeUpdate();
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<Category> getAllForUser(int userID) {
		String sql = "SELECT id, user_id, description, deleted_at FROM categories" +
					   "WHERE user_id = ? ORDER BY description";

		try {
			PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql);
			preparedStatement.setInt(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery(sql);
			return parseResultsSet(resultSet);
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public Category getByCategoryID(User user, int categoryID) {
		// public List<Category> getAllForUser(int userID) {
		String sql = "SELECT id, user_id, description, deleted_at FROM categories" +
					   "WHERE user_id = ? and id = ? ORDER BY description";

		try {
			PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql);
			preparedStatement.setInt(1, user.getUserID());
			preparedStatement.setInt(2, categoryID);

			List<Category> ar = parseResultsSet(preparedStatement.executeQuery(sql));
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
	public boolean delete(Category category) {
		String sql = "UPDATE categories SET deleted_at = ? WHERE id = ?";

		Date now = new Date();
		long ut = now.getTime() / 1000L;

		try(PreparedStatement preparedStatement = DAOFactory.connection.prepareStatement(sql)){
			preparedStatement.setLong(1, ut);
			preparedStatement.setInt(2, category.getCategoryID());
			preparedStatement.executeUpdate();
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	private List<Category> parseResultsSet(ResultSet resultSet) throws SQLException {
		ArrayList<Category> categoryList = new ArrayList<>();
		while(resultSet.next()){
			Category category = new Category(
					resultSet.getInt("id"),
					resultSet.getInt("user_id"),
					resultSet.getString("description"),
					resultSet.getInt("deleted_at")
			);
			categoryList.add(category);
		}
		return categoryList;
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

	public boolean clear(User user) {
		String sql = "DELETE FROM categories WHERE user_id = ?";

		try(PreparedStatement statement = DAOFactory.connection.prepareStatement(sql)){
			statement.setInt(1, user.getUserID());
			statement.executeUpdate();
			return true;
		} catch (SQLException e){
			System.err.println(e.getMessage());
			return false;
		}
	}
}
