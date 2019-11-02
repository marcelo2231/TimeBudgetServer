package timebudget.database.DAOs;

import timebudget.database.interfaces.IEventDAO;
import timebudget.model.Event;
import timebudget.model.TimePeriod;

import java.util.List;

public class EventDAO implements IEventDAO {
	@Override
	public boolean create(Event event) {
		return false;
	}
	
	@Override
	public boolean update(Event event) {
		return false;
	}
	
	@Override
	public List<Event> getAll() {
		return null;
	}
	
	@Override
	public List<Event> getByTimePeriod(TimePeriod timePeriod) {
		return null;
	}
	
	@Override
	public Event getByID(int id) {
		return null;
	}
	
	@Override
	public boolean delete(Event user) {
		return false;
	}
	
	@Override
	public boolean clear() {
		return false;
	}
}
