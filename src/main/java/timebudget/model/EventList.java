package timebudget.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class EventList {

	@Expose
	private List<Event> events = null;

	public EventList() {}

	public EventList(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}