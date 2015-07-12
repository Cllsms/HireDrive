package redyetisoftworks.hiredrive.DBAccessors;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Collin Sims on 7/12/2015.
 */
public class BaseDao {

    public static int getIntValueFromQuery(final SQLiteDatabase db, final String query) {
        final Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } finally {
            cursor.close();
        }
        return 0;
    }
}
