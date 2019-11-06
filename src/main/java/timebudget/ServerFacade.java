package timebudget;

import timebudget.database.interfaces.IDAOFactory;
import timebudget.exceptions.BadEventException;
import timebudget.exceptions.BadUserException;
import timebudget.exceptions.NoCategoryException;
import timebudget.exceptions.UserCreationException;
import timebudget.model.*;
import timebudget.model.request.GetEventListRequest;
import timebudget.model.request.LoginRequest;

import java.util.List;

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
		if(user != null)
			return model.addUser(user);
		throw new UserCreationException("User was null!");
	}

	@Override
	public List<Category> getAllActiveCategories(User user) throws BadUserException, NoCategoryException {
		//This will get the full user and its userID based on the auth token provided by the client.

		user = model.authenticate(user.getToken());
		return model.getCategoriesForUser(user.getUserID());
	}

	@Override
	public Category getCategoryByID(User user, int categoryID) throws NoCategoryException {
		return model.getCategoryByID(categoryID);
	}

	@Override
	public Event createEvent(Event event) throws BadEventException {
		return model.createEvent(event);
	}

	@Override
	public boolean deleteEvent(Event event) throws BadEventException {
		return model.deleteEvent(event.getEventID());
	}

	@Override
	public Event editEvent(Event event) throws BadEventException {
		return model.editEvent(event);
	}

	@Override
	public Event getEventByID(int eventID) throws BadEventException {
		return model.getEventByID(eventID);
	}

	@Override
	public List<Event> getEventList(GetEventListRequest request) throws BadUserException, BadEventException {
		return null;
	}

}
