package timebudget.model;

public class TimePeriod {

    public static final int NO_START_AT = -1;
    public static final int NO_END_AT = -1;

    private int startAt = NO_START_AT; // Must be 4 characters long
    private int endAt = NO_END_AT;

    // useful for serialization
    public TimePeriod() {};

    public TimePeriod(int startAt, int endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }


    public TimePeriod(TimePeriod tp) {
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



    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        TimePeriod that = (TimePeriod) object;
        return getStartAt() == that.getStartAt() &&
                getEndAt() == that.getEndAt();
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getStartAt(), getEndAt());
    }

    @Override
    public java.lang.String toString() {
        return "TimePeriod{" +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                '}';
    }
}

