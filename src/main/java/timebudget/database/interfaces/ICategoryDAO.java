package timebudget.database.interfaces;

import timebudget.model.Category;
import timebudget.model.User;

import java.util.List;

public interface ICategoryDAO extends IDAO {
	boolean create(Category category);
	boolean update(User u, Category category);
	List<Category> getAllForUser(int userID);
	Category getByCategoryID(User user, int categoryID);
	boolean delete(User u, int categoryID);
	boolean reactivate(User u, int categoryID);
	boolean clear(User user);
}
