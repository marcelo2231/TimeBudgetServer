package timebudget.model;

public class User {
	
	public static final int NO_USER_ID = -1;
	public static final int NO_CREATED_AT = -1;

	private int userID = NO_USER_ID;
	private String username = null; // Must be 4 characters long
	private String email = null; // Must be 4 characters long
	private String password = null; // Must be 8 characters long
	private int createdAt = NO_CREATED_AT;


	public User(int userID, String username, String email, String password, int createdAt) {
		this.userID = userID;
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
	}

	public User(User user) {
		this.userID = user.getUserID();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.createdAt = user.getCreatedAt();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		User user = (User) object;
		return getUserID() == user.getUserID() &&
				getCreatedAt() == user.getCreatedAt() &&
				java.util.Objects.equals(getUsername(), user.getUsername()) &&
				java.util.Objects.equals(getEmail(), user.getEmail()) &&
				java.util.Objects.equals(getPassword(), user.getPassword());
	}

	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), getUserID(), getUsername(), getEmail(), getPassword(), getCreatedAt());
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "User{" +
				"userID=" + userID +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}

