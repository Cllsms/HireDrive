package redyetisoftworks.hiredrive;

import android.app.Application;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import redyetisoftworks.hiredrive.DBAccessors.DatabaseHelper;

/**
 * Created by Collin Sims on 4/5/2015.
 */
public class HireDrive extends Application {

    public static String HIREDRIVE_DB = "hiredrive.db";
    public static String dbPath;
    public static String sd_Card_Location;
    public static SQLiteDatabase hiredriveDB;

    @Override
    public void onCreate(){
        super.onCreate();
        final ComponentName comp = new ComponentName(this, getClass());
        PackageInfo pinfo;
        try {
            pinfo = getPackageManager().getPackageInfo(comp.getPackageName(), 0);
            HireDrive.dbPath = pinfo.applicationInfo.dataDir + "/databases/";
            HireDrive.sd_Card_Location = Environment.getExternalStorageDirectory() + "/Android/data/" + pinfo.packageName + "/files/";
        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (DatabaseHelper.databaseExists()){
            HireDrive.hiredriveDB = Utils.getHireDriveDB(this);
        }
    }

    @Override
    public void onTerminate() {
        if (HireDrive.hiredriveDB != null)
            HireDrive.hiredriveDB.close();
        super.onTerminate();
    }
}
