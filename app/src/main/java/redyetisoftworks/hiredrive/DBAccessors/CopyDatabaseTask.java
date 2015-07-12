package redyetisoftworks.hiredrive.DBAccessors;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import redyetisoftworks.hiredrive.HireDrive;

/**
 * Created by dyost on 8/21/13.
 */
public class CopyDatabaseTask extends AsyncTask<Void, Integer, Void> {

    private ProgressDialog progDialog;
    private final Context context;

    private final OnFinishedListener onFinishedListener;

    public CopyDatabaseTask(final Context context, final ProgressDialog progDialog, final OnFinishedListener onFinishedListener) {
        this.progDialog = progDialog;
        this.onFinishedListener = onFinishedListener;
        this.context = context;

    }

    @Override
    protected void onPreExecute(){
        progDialog = ProgressDialog.show(context, "Please Wait...", "Initializing Database...");
    }

    @Override
    protected Void doInBackground(final Void... values) {

        InputStream in;
        OutputStream out;
        try {
            String dbPath = context.getDatabasePath(HireDrive.dbPath).getPath();
            File dbFilePath = new File(dbPath);
            if (!dbFilePath.exists()) {
                dbFilePath.mkdir();
            }
            in = new BufferedInputStream(context.getAssets().open(HireDrive.HIREDRIVE_DB, AssetManager.ACCESS_STREAMING), 8192);
            out = new BufferedOutputStream(new FileOutputStream(HireDrive.dbPath + HireDrive.HIREDRIVE_DB), 8192);
            try {
                final byte[] buffer = new byte[1024];
                int read;
                int progress = 0;
                while ((read = in.read(buffer)) > 0) {
                    publishProgress(progress, in.available());
                    out.write(buffer, 0, read);
                    progress += read;
                }
                out.flush();
            } finally {
                in.close();
                out.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Void result) {
        if (this.onFinishedListener != null) {
            this.onFinishedListener.onFinished();
        }
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }

    public interface OnFinishedListener {

        void onFinished();
    }

}
