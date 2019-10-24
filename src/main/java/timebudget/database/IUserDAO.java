package timebudget.database;

import timebudget.model.User;

import java.util.List;

public interface IUserDAO extends IDAO {
	boolean create(User user);
	boolean update(User user);
	List<User> getAll();
	boolean delete(User user);
}
