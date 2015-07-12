package redyetisoftworks.hiredrive.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import redyetisoftworks.hiredrive.DBAccessors.EmployeeDao;
import redyetisoftworks.hiredrive.HireDrive;
import redyetisoftworks.hiredrive.Models.Employee;
import redyetisoftworks.hiredrive.Models.Task;
import redyetisoftworks.hiredrive.R;
import redyetisoftworks.hiredrive.UserPreferences;

/**
 * Created by Collin Sims on 7/4/2015.
 */
public class EmployeeTaskListFragment extends Fragment {

    private ArrayList<Task> tasks = new ArrayList<>(10);
    private EmployeeInfoFragment employeeInfoFragment;
    private int empID;
    private Employee employee = new Employee();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empID = getArguments().getInt("EmployeeID");
        if (empID != -1) employee = EmployeeDao.getEmployeeByID(HireDrive.hiredriveDB, empID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_main_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        if (tasks.isEmpty()) fillTaskList();
        listView.setAdapter(new EmployeeTaskListAdapter(getActivity(), R.layout.employee_main_list_item, tasks));
        getActivity().setTitle("Checklist for " + employee.firstName + ", " + employee.lastName + " " + employee.middleInitial);
        setHasOptionsMenu(true);
        return rootView;
    }

    public void fillTaskList() {
        tasks.add(new Task("Sign Employee Manual"));
        tasks.add(new Task("Complete IRS W-4"));
        tasks.add(new Task("Complete State of IN WH-4"));
        tasks.add(new Task("Complete I-9 U.S. Citizenship & Immigration Service Form (w/ copies of I.D.)"));
        tasks.add(new Task("Complete Tomato Bar Employee Information Form"));
        tasks.add(new Task("Alcohol Server License"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.checklist_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();
        if (i == R.id.action_edit_employee) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            getActivity().setTitle("Edit Employee");
            employeeInfoFragment = new EmployeeInfoFragment();
            Bundle args = new Bundle();
            args.putInt("EmployeeID", empID);
            employeeInfoFragment.setArguments(args);
            fragmentTransaction.replace(R.id.frame_container, employeeInfoFragment, "empInfo");
            fragmentTransaction.addToBackStack("empInfo");
            fragmentTransaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
