package timebudget.database.DAOs;

import timebudget.database.interfaces.IUserDAO;
import timebudget.model.User;

import java.util.List;

public class UserDAO implements IUserDAO {
	@Override
	public boolean create(User user) {
		return false;
	}
	
	@Override
	public boolean update(User user) {
		return false;
	}
	
	@Override
	public List<User> getAll() {
		return null;
	}
	
	@Override
	public boolean delete(User user) {
		return false;
	}
	
	@Override
	public boolean clear() {
		return false;
	}
}
