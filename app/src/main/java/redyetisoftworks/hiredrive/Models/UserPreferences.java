package redyetisoftworks.hiredrive.Models;

/**
 * Created by Collin Sims on 3/23/2015.
 */
public class UserPreferences {

    private int id;
    private String Username;
    private String Password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
