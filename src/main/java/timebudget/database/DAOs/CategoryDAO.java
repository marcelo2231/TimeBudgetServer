package timebudget.database.DAOs;

import timebudget.database.interfaces.ICategoryDAO;
import timebudget.model.Category;
import timebudget.model.User;

import java.util.List;

public class CategoryDAO implements ICategoryDAO {
	@Override
	public boolean create(Category category) {
		return false;
	}
	
	@Override
	public boolean update(Category category) {
		return false;
	}
	
	@Override
	public List<Category> getAllForUser(int userID) {
		return null;
	}
	
	@Override
	public Category getByCategoryID(User user, int categoryID) {
		return null;
	}
	
	@Override
	public boolean delete(Category user) {
		return false;
	}
	
	@Override
	public boolean clear() {
		return false;
	}
}
