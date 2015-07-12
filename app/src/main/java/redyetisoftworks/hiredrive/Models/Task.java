package redyetisoftworks.hiredrive.Models;

/**
 * Created by Collin Sims on 7/4/2015.
 */
public class Task {

    public String description;
    public boolean completed;

    public Task (String Description) {
        description = Description;
        completed = false;
    }

}
