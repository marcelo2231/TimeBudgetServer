package timebudget.model;

public class DateTimeRange {

    public static final int NO_START_AT = -1;
    public static final int NO_END_AT = -1;
    public static final int NO_USER_ID = -1;

    private int startAt = NO_START_AT; // Must be 4 characters long
    private int endAt = NO_END_AT;
    private int userID = NO_USER_ID;

    // needed for serialization
    public DateTimeRange() {};

    public DateTimeRange(int startAt, int endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public DateTimeRange(int startAt, int endAt, int userID) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.userID = userID;
    }
    
    public DateTimeRange(DateTimeRange tp) {
        this.startAt = tp.getStartAt();
        this.endAt = tp.getEndAt();
    }



    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return this.userID;
    }



    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        DateTimeRange that = (DateTimeRange) object;
        return getStartAt() == that.getStartAt() &&
                getEndAt() == that.getEndAt();
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getStartAt(), getEndAt());
    }

    @Override
    public java.lang.String toString() {
        return "DateTimeRange{" +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                '}';
    }
}

