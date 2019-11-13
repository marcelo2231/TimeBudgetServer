package timebudget.exceptions;

public class DatabaseError extends Exception {
	private static final long serialVersionUID = -76372380159801288L;

	public DatabaseError(String message) {
		super(message);
	}
}
