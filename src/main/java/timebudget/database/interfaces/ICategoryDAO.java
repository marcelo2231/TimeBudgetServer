package timebudget.database.interfaces;

import timebudget.model.Category;
import timebudget.model.User;

import java.util.List;

public interface ICategoryDAO extends IDAO {
	boolean create(Category category);
	boolean update(Category category);
	List<Category> getAllForUser(int userID);
	Category getByCategoryID(int categoryID);
	boolean delete(Category category);
	boolean clear(User user);
}
