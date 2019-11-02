package timebudget.database.interfaces;


import timebudget.model.Event;
import timebudget.model.TimePeriod;

import java.util.List;

public interface IEventDAO extends IDAO{
	boolean create(Event event);
	boolean update(Event event);
	List<Event> getAll();
	List<Event> getByTimePeriod(TimePeriod timePeriod);
	Event getByID(int id);
	boolean delete(Event user);
}
