package timebudget;

import timebudget.database.interfaces.IDAOFactory;
import timebudget.exceptions.BadUserException;
import timebudget.exceptions.NoCategoryException;
import timebudget.exceptions.UserCreationException;
import timebudget.model.Category;
import timebudget.model.ServerModel;
import timebudget.model.User;
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
	public List<Category> getAllActiveCategories(int userID) throws BadUserException, NoCategoryException {
		
		
		
		return null;
	}
	
	@Override
	public Category getCategoryByID(int categoryID) throws NoCategoryException {
		
		return null;
	}
	
}
