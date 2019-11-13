package timebudget.exceptions;

public class DatabaseNotInitializedException extends Exception {
    private static final long serialVersionUID = 7453995851453887173L;

    public DatabaseNotInitializedException(String message) {
        super(message);
    }
}
