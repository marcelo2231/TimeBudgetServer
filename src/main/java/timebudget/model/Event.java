package timebudget.model;


public class Event {
    /**
     * Fields
     */
    public static final int NO_EVENT_ID = -1;
	private String category = null;
    private String description = null;
    private int eventID = NO_EVENT_ID;
    
    /**
	 * Constructor that creates a User with a name and password.
	 * @param category category of the new event
	 * @param description the description of the new event
	 *
	 * @pre username and password parameters are not null;
	 * @post a new User is created with the given username and password
	 */
	public Event(String category, String description) {
		this.category = category;
		this.description = description;
	}
    
	/**
	 * A copy constructor for a new Event.
	 * @param event the Event object to be copied.
	 *
	 * @pre event param is not null
	 * @post A new Event is created that is a copy of the provided User.
	 * @Post the new Event has the same category and description as the provided event.
	 */
	public Event(Event event) {
        this.category = event.getCategory();
        this.description = event.getDescription();
        this.eventID = event.getEventID();
    }
    
    /**
	 * Returns this event's descripton
	 * @return the description currently stored for this Event
	 *
	 * @post retVal == this.description;
	 */
	public String getCategory() {
		return category;
    }

    /**
	 * Set's the event descripton
	 */
    public void setCategory(String category) {
		this.category = category;
	}

    /**
	 * Returns this event's descripton
	 * @return the description currently stored for this Event
	 *
	 * @post retVal == this.description;
	 */
	public String getDescription() {
		return description;
    }

    /**
	 * Set's the event descripton
	 */
    public void setDescription(String description) {
		this.description = description;
    }
    
        /**
	 * Returns this event's id
	 * @return the event id currently stored for this Event
	 *
	 * @post retVal == this.eventID;
	 */
	public int getEventID() {
		return eventID;
    }

    /**
	 * Set's the event id
	 */
    public void setEventID(int eventID) {
		this.eventID = eventID;
	}
    
    	/**
	 * Method to compare two objects to determine if the other is the same as this one.
	 * @param o the other object to be compared to this User
	 * @return true/false depending on whether both this User and o are the same object
	 *
	 * @post this == o || this != o
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Event event = (Event) o;
		
		if(category != event.category) return false;
		return description.equals(event.description);
	}
	
	/**
	 * This method creates a hashing code for this userObject based on this.username, this.password,
	 * and this.userID.
	 * @return the hash code for this User object
	 *
	 * @pre this != null
	 * @pre this.username != null
	 * @post a unique hash code for this user has been generated.
	 */
	@Override
	public int hashCode() {
		int result = category.hashCode();
		result = 31 * result + description.hashCode();
		return result;
	}
	
	/**
	 * A string representation of this user which just consists of the username.
	 * @return the category of this event
	 *
	 * @post retVal == this.category
	 */
	@Override
	public String toString() {
		return category;
	}
}