package timebudget;

import timebudget.database.interfaces.IDAOFactory;
import timebudget.exceptions.BadUserException;
import timebudget.exceptions.UserCreationException;
import timebudget.model.ServerModel;
import timebudget.model.User;
import timebudget.model.request.LoginRequest;

public class ServerFacade implements IServer {
	
	private ServerModel model;
	
	public static IDAOFactory daoFactory = null;
	
	//If max_commands == -1 then store unlimited commands
	public static int max_commands = -1;
	
	private static ServerFacade instance = null;
	public static ServerFacade getInstance() {
		if(instance == null)
			instance = new ServerFacade();
		return instance;
	}
	private ServerFacade() {
		model = new ServerModel(daoFactory);
	}
	
	
	public User login(LoginRequest loginRequest) throws BadUserException {
		if(loginRequest != null)
			return model.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
		throw new BadUserException("User was null!");
	}

	
	public User register(User user) throws UserCreationException {
		if(user != null)
			return model.addUser(user);
		throw new UserCreationException("User was null!");
	}
	
	

}
