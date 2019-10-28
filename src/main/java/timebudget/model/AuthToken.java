package timebudget.model;

public class AuthToken {

    public static final int NO_AUTH_ID = -1;
    public static final int NO_USER_ID = -1;
    public static final int NO_EXPIRES_AT = -1;

    private int authTokenID = NO_AUTH_ID;
    private int userID = NO_USER_ID;
    private int expiresAt = NO_EXPIRES_AT; // Must be 4 characters long

    public AuthToken() {}

    public AuthToken(int authTokenID, int userID) {
        this.authTokenID = authTokenID;
        this.userID = userID;
    }

    public AuthToken(int authTokenID, int userID, int expiresAt) {
        this.authTokenID = authTokenID;
        this.userID = userID;
        this.expiresAt = expiresAt;
    }

    public AuthToken(AuthToken at) {
        this.authTokenID = at.getAuthTokenID();
        this.userID = at.getUserID();
        this.expiresAt = at.getExpiresAt();
    }

    public int getAuthTokenID() {
        return authTokenID;
    }

    public void setAuthTokenID(int authTokenID) {
        this.authTokenID = authTokenID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(int expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        AuthToken authToken = (AuthToken) object;
        return getAuthTokenID() == authToken.getAuthTokenID() &&
                getUserID() == authToken.getUserID() &&
                getExpiresAt() == authToken.getExpiresAt();
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getAuthTokenID(), getUserID(), getExpiresAt());
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "AuthToken{" +
                "authTokenID=" + authTokenID +
                ", userID=" + userID +
                ", expiresAt=" + expiresAt +
                '}';
    }
}

