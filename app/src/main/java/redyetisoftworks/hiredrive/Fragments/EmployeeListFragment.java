package redyetisoftworks.hiredrive.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import redyetisoftworks.hiredrive.DBAccessors.CopyDatabaseTask;
import redyetisoftworks.hiredrive.DBAccessors.DatabaseHelper;
import redyetisoftworks.hiredrive.DBAccessors.EmployeeDao;
import redyetisoftworks.hiredrive.HireDrive;
import redyetisoftworks.hiredrive.Models.Employee;
import redyetisoftworks.hiredrive.R;
import redyetisoftworks.hiredrive.UserPreferences;
import redyetisoftworks.hiredrive.Utils;

/**
 * Created by Collin Sims on 6/25/2015.
 */
public class EmployeeListFragment extends Fragment {

    private ListView listView;
    private ArrayList<Employee> employeeList;
    private EmployeeInfoFragment employeeInfoFragment;
    private EmployeeTaskListFragment employeeTaskListFragment;
    private int selectedPosition;
    private ProgressDialog progressDialog;

    private static final int CMD_EDIT = 0;
    private static final int CMD_DELETE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.employee_list_layout, container, false);
        if (!DatabaseHelper.databaseExists()){
            createHireDriveDB();
        } else {
            HireDrive.hiredriveDB = Utils.getHireDriveDB(getActivity());
        }

        getActivity().setTitle("Employee List");
        listView = (ListView) rootView.findViewById(R.id.employeeList);
        try {
            employeeList = EmployeeDao.getAllEmployees(HireDrive.hiredriveDB);
            EmployeeListAdapter adapter = new EmployeeListAdapter(getActivity(), R.layout.employee_list_item, employeeList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                employeeTaskListFragment = new EmployeeTaskListFragment();
                Bundle args = new Bundle();
                args.putInt("EmployeeID", employeeList.get(position).id);
                employeeTaskListFragment.setArguments(args);
                fragmentTransaction.replace(R.id.frame_container, employeeTaskListFragment, "empMain");
                fragmentTransaction.addToBackStack("empMain");
                fragmentTransaction.commit();
            }
        });
        registerForContextMenu(listView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();
        if (i == R.id.action_new_employee) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            getActivity().setTitle("New Employee");
            employeeInfoFragment = new EmployeeInfoFragment();
            Bundle args = new Bundle();
            args.putInt("EmployeeID", -1);
            employeeInfoFragment.setArguments(args);
            fragmentTransaction.replace(R.id.frame_container, employeeInfoFragment, "empInfo");
            fragmentTransaction.addToBackStack("empInfo");
            fragmentTransaction.commit();
        }
        if (i == R.id.action_settings) {
            startActivity(new Intent(getActivity(), UserPreferences.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,0,0,"Edit Employee Info");
        menu.add(0,1,0, "Delete Employee");
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedPosition = info.position;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case CMD_EDIT:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().setTitle("Edit Employee");
                employeeInfoFragment = new EmployeeInfoFragment();
                Bundle args = new Bundle();
                args.putInt("EmployeeID", employeeList.get(selectedPosition).id);
                employeeInfoFragment.setArguments(args);
                fragmentTransaction.replace(R.id.frame_container, employeeInfoFragment, "empInfo");
                fragmentTransaction.addToBackStack("empInfo");
                fragmentTransaction.commit();
                return true;
            case CMD_DELETE:
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setMessage("Are You Sure You Want to Delete This Employee?");
                dlg.setTitle("Delete Employee...");
                dlg.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EmployeeDao.deleteEmployee(HireDrive.hiredriveDB, employeeList.get(selectedPosition).id);
                        employeeList.remove(selectedPosition);
                        EmployeeListAdapter adapter = new EmployeeListAdapter(getActivity(), R.layout.employee_list_item, employeeList);
                        listView.setAdapter(adapter);
                    }
                });
                dlg.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dlg.create().show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void createHireDriveDB() {
        progressDialog = new ProgressDialog(getActivity());
        new CopyDatabaseTask(getActivity(), progressDialog, new CopyDatabaseTask.OnFinishedListener() {
            @Override
            public void onFinished() {
                HireDrive.hiredriveDB = Utils.getHireDriveDB(getActivity());
            }
        }).execute();
    }
}
