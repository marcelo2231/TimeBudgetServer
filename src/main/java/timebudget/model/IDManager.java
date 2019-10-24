package timebudget.model;

import timebudget.log.Corn;

import java.util.logging.Level;

class IDManager {
	private static int nextUserID = 0;
	
	static int getNextUserID() {
		Corn.log(Level.FINEST, "New User id: " + nextUserID + " has been used");
		return nextUserID++;
	}
	
}
