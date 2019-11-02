package timebudget.database.interfaces;

public interface IDAOFactory {
	
	IUserDAO getUserDAOInstance();
	IEventDAO getEventDAOInstance();
	ICategoryDAO getCategoryDAOInstance();
	
	boolean startTransaction();
	boolean endTransaction(boolean commit);
}
