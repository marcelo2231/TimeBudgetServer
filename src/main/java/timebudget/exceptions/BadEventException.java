package timebudget.exceptions;

public class BadEventException extends Exception {
	/**
	 * required to inherit from Exception.
	 */
	private static final long serialVersionUID = 6808956433665864000L;

	public BadEventException(String message) {
		super(message);
	}
}
