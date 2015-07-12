package redyetisoftworks.hiredrive;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import redyetisoftworks.hiredrive.DBAccessors.CopyDatabaseTask;
import redyetisoftworks.hiredrive.DBAccessors.DatabaseHelper;
import redyetisoftworks.hiredrive.Fragments.EmployeeInfoFragment;
import redyetisoftworks.hiredrive.Fragments.EmployeeListFragment;
import redyetisoftworks.hiredrive.Fragments.NavigationDrawerFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private NavigationDrawerFragment navigationDrawerFragment;

    // used to store app title
    private CharSequence mTitle;
    private int navPosition;
    private Button closeBtn;

    private EmployeeListFragment employeeListFragment = new EmployeeListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enabling action bar app icon and behaving it as toggle button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        employeeListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_container, employeeListFragment, "employeeList");
        fragmentTransaction.addToBackStack("employeeList");
        fragmentTransaction.commit();

        /*navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        navigationDrawerFragment.setUp(R.id.drawerSide, (DrawerLayout)findViewById(R.id.drawer_layout), toolbar);
        closeBtn = (Button) findViewById(R.id.closeEmployeeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });*/

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /*@Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == navPosition)
            return;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        navPosition = position;

        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }
    }*/

    @Override
    public void onResume(){
        super.onResume();
        shouldDisplayHomeUp();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp() {
        Log.d("back stack count ==> ", ""+getSupportFragmentManager().getBackStackEntryCount());
        boolean back = getSupportFragmentManager().getBackStackEntryCount() > 1;
        getSupportActionBar().setDisplayHomeAsUpEnabled(back);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
        shouldDisplayHomeUp();
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
