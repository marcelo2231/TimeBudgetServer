package timebudget.exceptions;

public class BadUserException extends Exception {
	private static final long serialVersionUID = -8595072168402686249L;

	public BadUserException(String message) {
		super(message);
	}
}
