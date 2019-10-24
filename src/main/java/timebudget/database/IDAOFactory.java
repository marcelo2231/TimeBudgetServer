package timebudget.database;

public interface IDAOFactory {
	
	IUserDAO getUserDAOInstance();
	
	boolean startTransaction();
	boolean endTransaction(boolean commit);
}
