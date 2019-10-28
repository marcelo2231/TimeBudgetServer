package timebudget.model;

public class TimePeriod {

    public static final int NO_TIME_PERIOD_ID = -1;
    public static final int NO_USER_ID = -1;
    public static final int NO_DELETED_AT = -1;
    public static final int NO_START_AT = -1;
    public static final int NO_END_AT = -1;

    private int timePeriodID = NO_TIME_PERIOD_ID;
    private int userID = NO_USER_ID;
    private int startAt = NO_START_AT; // Must be 4 characters long
    private int endAt = NO_END_AT;
    private int deletedAt = NO_DELETED_AT; // Must be 8 characters long

    // useful for serialization
    public TimePeriod() {};

    public TimePeriod(int timePeriodID, int userID, int startAt, int endAt, int deletedAt) {
        this.timePeriodID = timePeriodID;
        this.userID = userID;
        this.startAt = startAt;
        this.endAt = endAt;
        this.deletedAt = deletedAt;
    }

    public TimePeriod(int timePeriodID, int userID, int startAt, int endAt) {
        this.timePeriodID = timePeriodID;
        this.userID = userID;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public TimePeriod(TimePeriod tp) {
        this.timePeriodID = tp.getTimePeriodID();
        this.userID = tp.getUserID();
        this.startAt = tp.getStartAt();
        this.endAt = tp.getEndAt();
        this.deletedAt = tp.getDeletedAt();
    }

    public boolean isDeleted() {
        return deletedAt == NO_DELETED_AT;
    }

    public int getTimePeriodID() {
        return timePeriodID;
    }

    public void setTimePeriodID(int timePeriodID) {
        this.timePeriodID = timePeriodID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(int deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        TimePeriod that = (TimePeriod) object;
        return getTimePeriodID() == that.getTimePeriodID() &&
                getUserID() == that.getUserID() &&
                getStartAt() == that.getStartAt() &&
                getEndAt() == that.getEndAt() &&
                getDeletedAt() == that.getDeletedAt();
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getTimePeriodID(), getUserID(), getStartAt(), getEndAt(), getDeletedAt());
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "TimePeriod{" +
                "timePeriodID=" + timePeriodID +
                ", userID=" + userID +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

