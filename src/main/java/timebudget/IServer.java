package timebudget;

import timebudget.exceptions.BadEventException;
import timebudget.exceptions.BadUserException;
import timebudget.exceptions.NoCategoryException;
import timebudget.exceptions.UserCreationException;
import timebudget.model.Category;
import timebudget.model.DateTimeRange;
import timebudget.model.Event;
import timebudget.model.User;
import timebudget.model.request.LoginRequest;

import java.util.List;
import java.util.Map;

public interface IServer {

	// CONTEXTS
	// Default
	String DEFAULT = "/";

	// User
	String USER_LOGIN = "/user/login";
	String USER_REGISTER = "/user/register";

	//Events
	String EVENT_CREATE = "/event/create";
	String EVENT_EDIT = "/event/edit";
	String EVENT_DELETE = "/event/delete";
	String EVENT_GET_LIST = "/event/get_list";
	String EVENT_GET_BY_ID = "/event/get_by_id";

	//Reporting
	String REPORT_GET_TIME_METRICS = "/report/get_time_metrics_all"; //Gets metrics for


	//Mock calls
	String FAKE_IT = "/fake_it";

	//Categories
	String CATEGORIES_GET_ACTIVE = "/categories/get_all_active";
	String CATEGORIES_GET_BY_ID = "/categories/get_by_id";



	//CONTEXTS DEMO 2
	//Categories

	//Tags

	//Time Periods

	//Goals

	//Reporting pt. 2




	/**
	 * method to log a user in.
	 * @pre user.username exists
	 * @pre user.password corresponds to user.username
	 *
	 * @param loginRequest only should consist of a username and password
	 * @return the user who was logged in if the username and password were correct, null otherwise
	 * @throws BadUserException contains a message about the failure (only if user is not formatted correctly)
	 */
	User login(LoginRequest loginRequest) throws BadUserException;

	/**
	 * method to register a new user
	 * @pre user.username is unique
	 * @pre user.username.length > 4
	 * @pre user.password.length > 8
	 * @pre user.fullname set to user.username if user.fullname is null
	 *
	 * @param user contains username, password, and (optionally) fullname
	 *
	 * @return completed user that was registered
	 * @throws UserCreationException
	 */
	User register(User user) throws UserCreationException;

	List<Category> getAllActiveCategories(User user) throws BadUserException, NoCategoryException;
	
	Category getCategoryByID(User user, int categoryID) throws NoCategoryException, BadUserException;
	
	Event createEvent(User user, Event event) throws BadEventException, BadUserException;
	
	boolean deleteEvent(User user, Event event) throws BadEventException, BadUserException;
	
	boolean editEvent(User user, Event event) throws BadEventException, BadUserException;
	
	Event getEventByID(User user, int eventID) throws BadEventException, BadUserException;
	
	List<Event> getEventList(User user, DateTimeRange range) throws BadUserException, BadEventException;
	
	Map<Integer, Float> getReport(User user, DateTimeRange range) throws BadUserException, BadEventException;
}

