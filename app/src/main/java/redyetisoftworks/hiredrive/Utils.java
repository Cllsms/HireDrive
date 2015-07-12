package redyetisoftworks.hiredrive;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import redyetisoftworks.hiredrive.DBAccessors.DatabaseHelper;

/**
 * Created by Collin Sims on 6/25/2015.
 */
public class Utils {

    public static boolean hireDriveDBExists(){ return DatabaseHelper.databaseExists(); }

    public static SQLiteDatabase getHireDriveDB(final Context context){
        final SQLiteDatabase retval = new DatabaseHelper(context).getWritableDatabase();
        retval.execSQL("PRAGMA foreign_keys=ON;");
        return retval;
    }
}
