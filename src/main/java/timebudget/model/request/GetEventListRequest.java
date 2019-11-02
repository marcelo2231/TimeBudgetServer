package timebudget.model.request;

import timebudget.model.TimePeriod;

public class GetEventListRequest {
    private int userID;
    private TimePeriod timePeriod;

    public GetEventListRequest(int userID, TimePeriod timePeriod) {
        this.setUserID(userID);
        this.setTimePeriod(timePeriod);
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}