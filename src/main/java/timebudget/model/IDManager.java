package timebudget.model;

class IDManager {
	private static int nextUserID = 0;
	private static int nextGameID = 0;
	
	static int getNextUserID() {
		//Corn.log(Level.FINEST, "New User id: " + nextUserID + " has been used");
		return nextUserID++;
	}
	
}
