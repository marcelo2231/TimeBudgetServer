package timebudget;

import timebudget.database.interfaces.IDAOFactory;
import timebudget.exceptions.*;
import timebudget.model.*;
import timebudget.model.request.LoginRequest;

import java.util.List;
import java.util.Map;

public class ServerFacade implements IServer {

	private ServerModel model;

	public static IDAOFactory daoFactory = null;

	private static ServerFacade instance = null;
	public static ServerFacade getInstance() {
		if(instance == null)
			instance = new ServerFacade();
		return instance;
	}
	private ServerFacade() {
		model = new ServerModel(daoFactory);
	}

	@Override
	public User login(LoginRequest loginRequest) throws BadUserException {
		if(loginRequest != null)
			return model.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
		throw new BadUserException("User was null!");
	}

	@Override
	public User register(User user) throws UserCreationException {
		if(user != null) {
			try {
				return model.addUser(user);
			} catch (DatabaseNotInitializedException e) {
				e.printStackTrace();
			}
		}
		throw new UserCreationException("User was null!");
	}

	@Override
	public Category createCategory(User user, Category category) throws BadUserException, BadCategoryException {
		user = model.authenticate(user.getToken());
		return model.createCategory(user, category);
	}

	@Override
	public List<Category> getAllActiveCategories(User user) throws DatabaseError, BadUserException {
		//This will get the full user and its userID based on the auth token provided by the client.
		user = model.authenticate(user.getToken());
		return model.getCategoriesForUser(user.getUserID());
	}

	@Override
	public Category getCategoryByID(User user, int categoryID) throws NoCategoryException, BadUserException {
		user = model.authenticate(user.getToken());
		return model.getCategoryByID(user, categoryID);
	}

	@Override
	public Event createEvent(User user, Event event) throws BadEventException, BadUserException {
		user = model.authenticate(user.getToken());

		return model.createEvent(user, event);
	}

	@Override
	public boolean deleteEvent(User user, Event event) throws BadEventException, BadUserException {
		user = model.authenticate(user.getToken());
		return model.deleteEvent(user, event.getEventID());
	}

	@Override
	public boolean editEvent(User user, Event event) throws BadEventException, BadUserException {
		user = model.authenticate(user.getToken());
		event.setUserID(user.getUserID());
		return model.editEvent(user, event);
	}

	@Override
	public Event getEventByID(User user, int eventID) throws BadEventException, BadUserException {
		user = model.authenticate(user.getToken());

		return model.getEventByID(user, eventID);
	}

	@Override
	public EventList getEventListOneCategory(User user, DateTimeRange range, int categoryID) throws BadUserException, BadEventException {
		user = model.authenticate(user.getToken());
		return new EventList(model.getEventListOneCategory(user, range, categoryID));
	}

	@Override
	public Map<Integer, Float> getReport(User user, DateTimeRange range) throws BadUserException, BadEventException, DatabaseError {
		user = model.authenticate(user.getToken());
		return model.getReport(user, range);
	}
}
