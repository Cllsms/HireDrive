package redyetisoftworks.hiredrive.DBAccessors;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import redyetisoftworks.hiredrive.HireDrive;

/**
 * Created by Collin Sims on 4/5/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context ctx;

    public DatabaseHelper(final Context context){
        super(context, HireDrive.HIREDRIVE_DB, null, 1);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) return;
    }

    public static boolean databaseExists() {
        SQLiteDatabase unverifiedDB = null;

        try {
            final String dbPath = HireDrive.dbPath + HireDrive.HIREDRIVE_DB;
            Log.d("dbPath ==> ", dbPath);
            unverifiedDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            unverifiedDB.close();
        } catch (final SQLiteException e) {

        }
        return unverifiedDB != null;
    }

    public static boolean copyDatabase(final Context context) {
        InputStream in;
        OutputStream out;
        try{
            in = new BufferedInputStream(context.getAssets().open(HireDrive.HIREDRIVE_DB, AssetManager.ACCESS_STREAMING), 8192);
            out = new BufferedOutputStream(new FileOutputStream(HireDrive.dbPath + HireDrive.HIREDRIVE_DB), 8192);
            try{
                final byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) > 0) {
                    out.write(buffer, 0, read);
                }
                out.flush();
            } finally {
                in.close();
                out.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
