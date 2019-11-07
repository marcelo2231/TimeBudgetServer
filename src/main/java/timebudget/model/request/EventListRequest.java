package timebudget.model.request;


public class EventListRequest {
    private int categoryID;
    private int startAt;
    private int endAt;

    public EventListRequest(int categoryID, int startAt, int endAt){
        this.categoryID = categoryID;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public int getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getStartAt() {
        return this.startAt;        
    }

    public void setStart(int startAt) {
        this.startAt = startAt;
    }

    public int getEndAt() {
        return this.endAt;        
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }
}