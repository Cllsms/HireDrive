package redyetisoftworks.hiredrive.DBAccessors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import redyetisoftworks.hiredrive.HireDrive;

/**
 * Created by Collin Sims on 3/23/2015.
 */
public class UserPreferencesDao {

  /*  public UserPreferencesDao(DAOProvider provider) throws IOException {
        super("UserPreferences", provider);
    }

    @Override
    public UserPreferences newObject() {
        return new UserPreferences();
    }

    @Override
    public void unmap(UserPreferences obj, Map values) {
        obj.setId(Integer.parseInt(values.get("id").toString()));
        obj.setUsername((String)(values.get("Username")));
        obj.setPassword((String)(values.get("Password")));
    }

    @Override
    public void map(UserPreferences obj, Map values) {
        values.put("ID", obj.getId());
        values.put("Username", obj.getUsername());
        values.put("Password", obj.getPassword());
    }

    @Override
    public long getId(UserPreferences object) {
        return object.getId();
    }

    public ArrayList<UserPreferences> getUserPreferences() {
        ArrayList<UserPreferences> retval = new ArrayList<UserPreferences>();

        try {
            if (!this.fetchAll().isEmpty())
                retval.addAll(this.fetchAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retval;
    }*/
}
