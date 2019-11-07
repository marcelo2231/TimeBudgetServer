package timebudget.model.response;

import timebudget.model.User;

public class UserDetails {
    private String username;
    private String email;
    private String token;

    public UserDetails(String username, String email, String token){
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public UserDetails(User u) {
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.token = u.getToken();
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}