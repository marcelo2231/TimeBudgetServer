package timebudget.database.DAOs;

import timebudget.database.interfaces.IEventDAO;
import timebudget.model.Event;
import timebudget.model.TimePeriod;
import timebudget.model.User;

import java.util.List;

public class EventDAO implements IEventDAO {
	@Override
	public Event create(User user, Event event) {
		return null;
	}
	
	@Override
	public boolean update(User user, Event event) {
		return false;
	}
	
	@Override
	public List<Event> getAll() {
		return null;
	}
	
	@Override
	public List<Event> getByTimePeriod(User user, TimePeriod timePeriod) {
		return null;
	}
	
	@Override
	public Event getByID(User user, int id) {
		return null;
	}
	
	@Override
	public boolean delete(User user, int eventID) {
		return false;
	}
	
	@Override
	public boolean clear() {
		return false;
	}
}
