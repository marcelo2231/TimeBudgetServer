package timebudget.database.interfaces;


import timebudget.model.DateTimeRange;
import timebudget.model.Event;
import timebudget.model.TimePeriod;
import timebudget.model.User;

import java.util.List;

public interface IEventDAO extends IDAO{
	boolean create(User user, Event event);
	boolean update(User user, Event event);
	List<Event> getAllForUser(User user);
	List<Event> getWithinRange(User user, DateTimeRange range);
	List<Event> getByTimePeriod(User user, TimePeriod timePeriod);
	Event getByID(User user, int id);
	boolean delete(User user, int eventID);
}
