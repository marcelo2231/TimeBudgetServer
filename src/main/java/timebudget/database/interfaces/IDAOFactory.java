package timebudget.database.interfaces;

public interface IDAOFactory {
	
	IUserDAO getUserDAOInstance();
	
	boolean startTransaction();
	boolean endTransaction(boolean commit);
}
