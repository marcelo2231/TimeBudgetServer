package timebudget.model;

import timebudget.ServerFacade;
import timebudget.database.interfaces.IDAOFactory;
import timebudget.database.interfaces.IUserDAO;
import timebudget.exceptions.BadUserException;
import timebudget.exceptions.UserCreationException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerModel {
	private Set<User> users; // Stores all users ever.
	private SecureRandom random;
	private IUserDAO userDAO;
	
	public ServerModel(IDAOFactory factory) {
		users = new HashSet<User>();
		random = new SecureRandom();
		if(factory != null) {
			userDAO = factory.getUserDAOInstance();
			loadFromDatabase();
		}
	}
	
	private void loadFromDatabase() {
		if(userDAO != null) {
			ServerFacade.daoFactory.startTransaction();
			List<User> userList = userDAO.getAll();
			if(userList != null) {
				for(User userObj : userList) {
					User user = new User(userObj.getUsername(), userObj.getPassword(), userObj.getFullName());
					user.setUserID(userObj.getUserID());
					users.add(user);
				}
			}
			
			
			ServerFacade.daoFactory.endTransaction(false);
		}
	}
	
	private void generateToken(User u) {
		u.setToken(new BigInteger(130, random).toString(32));
	}
	
	/**
	 * authenticate a user using username and password of a given user and generates authentication token
	 *
	 * @param username valid username of a user
	 * @param password valid password of a user
	 * @return user who was logged in, or null if bad credentials
	 */
	public User authenticate(String username, String password) throws BadUserException {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				if(u.getPassword().equals(password)) {
					generateToken(u);
					return u;
				}
				throw new BadUserException("Invalid username or password!");
			}
		}
		throw new BadUserException("Invalid username or password!");
	}
	
	/**
	 * authenticates a user using an authentication token.
	 *
	 * @param token token of specified user to log on
	 * @return user with corresponding token if login successful, null if not
	 */
	public User authenticate(String token) throws BadUserException {
		for(User u : users) {
			if(token.equals(u.getToken()))
				return u;
		}
		throw new BadUserException("Invalid user token!");
	}
	
	/**
	 * create a new user and generates an authentication token
	 * @param user contains all parts of the user to be created
	 * @return newly created user
	 * @throws UserCreationException
	 */
	public User addUser(User user) throws UserCreationException {
		if(user.getUsername().length() < 4) throw new UserCreationException("Username too short!");
		if(user.getPassword().length() < 8) throw new UserCreationException("Password too short!");
		if(user.getFullName() == null || user.getFullName().length() < 1) user.setFullName(user.getUsername());
		
		for(User u : users) {
			if(u.getUsername().equals(user.getUsername()))
				throw new UserCreationException("User already exisits!");
		}
		
		User newUser = new User(user.getUsername(), user.getPassword(), user.getFullName());
		
		if(userDAO != null) {
			ServerFacade.daoFactory.startTransaction();
			
			if(userDAO.create(user)) {
				ServerFacade.daoFactory.endTransaction(true);
				newUser.setUserID(user.getUserID());
			} else {
				ServerFacade.daoFactory.endTransaction(false);
				throw new UserCreationException("Could not create User in Database.");
			}
		} else {
			newUser.setUserID(IDManager.getNextUserID());
		}
		generateToken(newUser);
		users.add(newUser);
		return newUser;
	}
	
}