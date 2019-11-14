package timebudget.model;

import timebudget.ReportGen;
import timebudget.ServerFacade;
import timebudget.database.interfaces.ICategoryDAO;
import timebudget.database.interfaces.IDAOFactory;
import timebudget.database.interfaces.IEventDAO;
import timebudget.database.interfaces.IUserDAO;
import timebudget.exceptions.*;

//import java.security.SecureRandom;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class ServerModel {
	private Set<User> users; // Stores all users ever.
	private SecureRandom random;
	private IUserDAO userDAO;
	private IEventDAO eventDAO;
	private ICategoryDAO categoryDAO;

	public ServerModel(IDAOFactory factory) {
		users = new HashSet<User>();
		random = new SecureRandom();
		if(factory != null) {
			userDAO = factory.getUserDAOInstance();
			eventDAO = factory.getEventDAOInstance();
			categoryDAO = factory.getCategoryDAOInstance();
			loadFromDatabase();
		}
	}

	private void loadFromDatabase() {
		if(userDAO != null) {
			ServerFacade.daoFactory.startTransaction();
			List<User> userList = userDAO.getAll();
			if(userList != null) {
				for(User userObj : userList) {
					User user = new User(userObj);
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
	public User addUser(User user) throws UserCreationException, DatabaseNotInitializedException {
		if(user.getUsername().length() < 4) throw new UserCreationException("Username too short!");
		if(user.getPassword().length() < 8) throw new UserCreationException("Password too short!");

		for(User u : users) {
			if(u.getUsername().equals(user.getUsername()))
				throw new UserCreationException("User already exisits!");
		}

		User newUser = new User(user);

		if(userDAO != null) {
			ServerFacade.daoFactory.startTransaction();

			if(userDAO.create(user)) {
				ServerFacade.daoFactory.endTransaction(true);

				newUser.setUserID(user.getUserID());

				HashMap<String, Integer> initCatMap = new HashMap<>();
				initCatMap.put("Sleep", 0xFF4CAF50);
				initCatMap.put("Work", 0xFFF44336);
				initCatMap.put("School", 0xFF2196F3);
				initCatMap.put("Eat", 0xFFFF9800);
				initCatMap.put("Health/Wellness", 0xFF9C27B0);
				initCatMap.put("Amusement", 0xFF00BCD4);

				for (String s : initCatMap.keySet()) {
					Category defaultCategory = new Category(Category.NO_CATEGORY_ID, newUser.getUserID(), initCatMap.get(s), s);
					try {
						createCategory(newUser, defaultCategory);
					} catch (BadCategoryException e) {
						e.printStackTrace();
					}
				}
			} else {
				ServerFacade.daoFactory.endTransaction(false);
				throw new UserCreationException("Could not create User in Database.");
			}
		} else {
			throw new DatabaseNotInitializedException("The UserDAO was NULL!");
		}
		generateToken(newUser);
		users.add(newUser);
		return newUser;
	}

	public Category createCategory(User user, Category category) throws BadCategoryException {
		ServerFacade.daoFactory.startTransaction();

		if(categoryDAO.create(category)) {
			ServerFacade.daoFactory.endTransaction(true);
			if(category.getCategoryID() == Category.NO_CATEGORY_ID)
				System.err.println("FAILED TO UPDATE NEW CATEGORY ID");
			return category;
		} else {
			ServerFacade.daoFactory.endTransaction(false);
			throw new BadCategoryException("Could not create Category!");
		}
	}
	
	public List<Category> getCategoriesForUser(int userID) throws DatabaseError {
		ServerFacade.daoFactory.startTransaction();

		List<Category> categories = categoryDAO.getAllForUser(userID);

		if(categories != null) {
			ServerFacade.daoFactory.endTransaction(false);
			return categories;
		} else {
			ServerFacade.daoFactory.endTransaction(false);
			throw new DatabaseError("An error has occured in CategoryDAO");
		}
	}
	
	public Category getCategoryByID(User user, int categoryID){
		ServerFacade.daoFactory.startTransaction();
		Category category = categoryDAO.getByCategoryID(user, categoryID);
		ServerFacade.daoFactory.endTransaction(false);
		return category;
	}

	/**
	 * creates a new event
	 * @param event contains all parts of the event to be created
	 * @return newly created event
	 * @throws BadEventException
	 */
	public Event createEvent(User user, Event event) throws BadEventException {
		if(event == null) throw new BadEventException("Event is null.");

		ServerFacade.daoFactory.startTransaction();
		event.setUserID(user.getUserID());
		if (eventDAO.create(user, event)) {
			ServerFacade.daoFactory.endTransaction(true);
		} else {
			ServerFacade.daoFactory.endTransaction(false);
			throw new BadEventException("Failed to create Event!");
		}

		return event;
	}

	/**
	 * edits an event
	 * @param event contains all parts of the event to be edited
	 * @return was event edit successful
	 * @throws BadEventException
	 */
	public boolean editEvent(User user, Event event) throws BadEventException {
		if(event == null) throw new BadEventException("Event is null.");
		ServerFacade.daoFactory.startTransaction();
		boolean success = eventDAO.update(user, event);

		if (success)
			ServerFacade.daoFactory.endTransaction(true);
		else
			ServerFacade.daoFactory.endTransaction(false);
		return success;
	}

	/**
	 * deletes an event
	 * @param eventID the event id
	 * @return deletion successful
	 * @throws BadEventException
	 */
	public boolean deleteEvent(User user, int eventID) throws BadEventException {
		if(eventID == -1) throw new BadEventException("Event id is -1.");
		ServerFacade.daoFactory.startTransaction();
		boolean success = eventDAO.delete(user, eventID);
		if (success) 
			ServerFacade.daoFactory.endTransaction(true);
		else
			ServerFacade.daoFactory.endTransaction(false);

		return success;
	}

	/**
	 * gets an event by id
	 * @param eventID the event id
	 * @return the event
	 * @throws BadEventException
	 */
	public Event getEventByID(User user, int eventID) throws BadEventException {
		if(eventID == -1) throw new BadEventException("Event id is -1.");
		ServerFacade.daoFactory.startTransaction();
		Event e = eventDAO.getByID(user, eventID);
		ServerFacade.daoFactory.endTransaction(false);
		return e;
	}

	/**
	 * gets an event by id
	 * @param user the user who's events we want
	 * @param range specified range of time for grabbing the events
	 * @return a list of events
	 * @throws BadUserException
	 * @throws BadEventException
	 */
	public List<Event> getEventListOneCategory(User user, DateTimeRange range, int categoryID) throws BadUserException, BadEventException {
		if(user.getUserID() == -1) throw new BadUserException("User ID is -1.");
		if(range == null) throw new BadEventException("range is null.");
		ServerFacade.daoFactory.startTransaction();
		List<Event> returnList = eventDAO.getWithinRangeOneCategory(user, range, categoryID);
		ServerFacade.daoFactory.endTransaction(false);
		return returnList;
	}


	/**
	 * gets an event by id
	 * @param user the user who's events we want
	 * @param range specified range of time for grabbing the events
	 * @return a list of events
	 * @throws BadUserException
	 * @throws BadEventException
	 */
	public List<Event> getEventList(User user, DateTimeRange range) throws BadUserException, BadEventException {
		if(user.getUserID() == -1) throw new BadUserException("User ID is -1.");
		if(range == null) throw new BadEventException("range is null.");
		ServerFacade.daoFactory.startTransaction();
		List<Event> returnList = eventDAO.getWithinRange(user, range);
		ServerFacade.daoFactory.endTransaction(false);
		return returnList;
	}

	/**
	 * get a report for a specified datetime range
	 * @param user the user who's events we want
	 * @param range specified range of time for grabbing the events
	 * @return a list of events
	 * @throws BadUserException
	 * @throws BadEventException
	 */
	public Map<Integer, Float> getReport(User user, DateTimeRange range) throws BadUserException, BadEventException, DatabaseError {
		if(user.getUserID() == -1) throw new BadUserException("User ID is -1.");
		if(range == null) throw new BadEventException("range is null.");

		// These include events that belong to categories that could be deleted.
		List<Event> eventsInRange = getEventList(user, range);
	
		ServerFacade.daoFactory.startTransaction();
		Map<Integer, Float> report = ReportGen.getReport(user, range, eventsInRange);
		ServerFacade.daoFactory.endTransaction(false);

		List<Category> activeCategories = getCategoriesForUser(user.getUserID());
		for (Category c: activeCategories) {
			if (!report.containsKey(c.getCategoryID()))
				report.put(c.getCategoryID(), 0f);
		}

		return report;
	}
}