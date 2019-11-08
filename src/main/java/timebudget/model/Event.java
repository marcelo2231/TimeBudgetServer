package timebudget.model;

import com.google.gson.annotations.Expose;

public class Event {
	/**
	 * Fields
	 */
	public static final int NO_EVENT_ID = -1;
	public static final int NO_CATEGORY_ID = -1;
	public static final int NO_USER_ID = -1;
	public static final int NO_START_AT = -1;
	public static final int NO_END_AT = -1;


	// never serialize
	private transient int userID = NO_USER_ID;

	// don't serialize in the event/get_list handler
	private int categoryID = NO_CATEGORY_ID;

	@Expose
	private int eventID = NO_EVENT_ID;

	@Expose
	private String description = null;

	@Expose
	private int startAt = NO_START_AT;

	@Expose
	private int endAt = NO_END_AT;

	public Event() {}

	public Event(int eventID, int categoryID, String description, int userID, int startAt, int endAt) {
		this.eventID = eventID;
		this.categoryID = categoryID;
		this.description = description;
		this.userID = userID;
		this.startAt = startAt;
		this.endAt = endAt;
	}

	public Event(Event event) {
		this.eventID = event.getEventID();
		this.categoryID = event.getCategoryID();
		this.description = event.getDescription();
		this.userID = event.getUserID();
		this.startAt = event.getStartAt();
		this.endAt = event.getEndAt();
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getStartAt() {
		return startAt;
	}

	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	public int getEndAt() {
		return endAt;
	}

	public void setEndAt(int endAt) {
		this.endAt = endAt;
	}

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Event event = (Event) object;
		return getEventID() == event.getEventID() &&
				getCategoryID() == event.getCategoryID() &&
				getUserID() == event.getUserID() &&
				getStartAt() == event.getStartAt() &&
				getEndAt() == event.getEndAt() &&
				java.util.Objects.equals(getDescription(), event.getDescription());
	}

	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), getEventID(), getCategoryID(), getDescription(), getUserID(), getStartAt(), getEndAt());
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "Event{" +
				"eventID=" + eventID +
				", categoryID=" + categoryID +
				", description='" + description + '\'' +
				", userID=" + userID +
				", startAt=" + startAt +
				", endAt=" + endAt +
				'}';
	}
}