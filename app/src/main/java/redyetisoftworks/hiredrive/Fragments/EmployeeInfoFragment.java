package redyetisoftworks.hiredrive.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.joda.time.DateTime;

import redyetisoftworks.hiredrive.DBAccessors.EmployeeDao;
import redyetisoftworks.hiredrive.HireDrive;
import redyetisoftworks.hiredrive.Models.Employee;
import redyetisoftworks.hiredrive.R;
import redyetisoftworks.hiredrive.Utils;

/**
 * Created by Collin Sims on 4/4/2015.
 */
public class EmployeeInfoFragment extends Fragment {

    private EditText firstName;
    private EditText lastName;
    private EditText middleInitial;
    private EditText address1;
    private EditText address2;
    private EditText city;
    private EditText state;
    private EditText zip;
    private EditText phoneNumber;
    private EditText altPhone;
    private EditText email;
    private EditText ssn;
    private Spinner maritalStatus;
    private EditText spouseName;
    private CompoundButton veteran;
    private CompoundButton citizen;
    private Button birthday;
    private Employee employee = new Employee();
    private ImageButton next;
    private ImageButton prev;

    private int empID;

    public EmployeeInfoFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empID = getArguments().getInt("EmployeeID");
        if (empID != -1) getData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.employee_personal_info_fragment, container, false);

        firstName = (EditText) rootView.findViewById(R.id.EmployeeInfoFirstNameET);
        lastName = (EditText) rootView.findViewById(R.id.EmployeeInfoLastNameET);
        middleInitial = (EditText) rootView.findViewById(R.id.EmployeeInfoMIET);
        address1 = (EditText) rootView.findViewById(R.id.EmployeeInfoAddress1ET);
        address2 = (EditText) rootView.findViewById(R.id.EmployeeInfoAddress2ET);
        city = (EditText) rootView.findViewById(R.id.EmployeeInfoCityET);
        state = (EditText) rootView.findViewById(R.id.EmployeeInfoStateET);
        zip = (EditText) rootView.findViewById(R.id.EmployeeInfoZipET);
        phoneNumber = (EditText) rootView.findViewById(R.id.EmployeeInfoPhoneET);
        altPhone = (EditText) rootView.findViewById(R.id.EmployeeInfoPhoneAltET);
        email = (EditText) rootView.findViewById(R.id.EmployeeInfoEmailET);
        ssn = (EditText) rootView.findViewById(R.id.EmployeeInfoSSET);
        maritalStatus = (Spinner) rootView.findViewById(R.id.EmployeeInfoMaritalStatus);
        spouseName = (EditText) rootView.findViewById(R.id.EmployeeInfoSpouseName);
        veteran = (CompoundButton) rootView.findViewById(R.id.EmployeeInfoUSVeteran);
        citizen = (CompoundButton) rootView.findViewById(R.id.EmployeeInfoUSCitizen);
        birthday = (Button) rootView.findViewById(R.id.EmployeeInfoBirthday);
        next = (ImageButton) rootView.findViewById(R.id.EmployeeInfoNext);
        prev = (ImageButton) rootView.findViewById(R.id.EmployeeInfoPrev);
        if (employee != null) {
            firstName.setText(employee.firstName);
            lastName.setText(employee.lastName);
            middleInitial.setText(employee.middleInitial);
            address1.setText(employee.address1);
            address2.setText(employee.address2);
            city.setText(employee.city);
            state.setText(employee.state);
            zip.setText(employee.zip);
            phoneNumber.setText(employee.phone);
            altPhone.setText(employee.altPhone);
            email.setText(employee.email);
            ssn.setText(employee.ssn);
            maritalStatus.setSelection(employee.maritalStatus);
            spouseName.setText(employee.spouseName);
            veteran.setChecked(employee.isVeteran);
            citizen.setChecked(employee.isCitizen);
            birthday.setText(employee.birthdate);
        }
        spouseName.setVisibility(View.GONE);

        final DateTime currentDate = DateTime.now();
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        birthday.setText("" + month + " / " + day + " / " + year);
                        birthday.setGravity(Gravity.CENTER);
                    }
                }, currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth());
                dpd.show();
            }
        });

        maritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2) spouseName.setVisibility(View.VISIBLE);
                else spouseName.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.new_employee_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();
        if (i == R.id.saveNewEmployee) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            saveData();
            getActivity().onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    public void getData() {
        employee = EmployeeDao.getEmployeeByID(HireDrive.hiredriveDB, empID);
    }

    protected boolean saveData(){
        employee.firstName = firstName.getText().toString();
        employee.lastName = lastName.getText().toString();
        employee.middleInitial = middleInitial.getText().toString();
        employee.address1 = address1.getText().toString();
        employee.address2 = address2.getText().toString();
        employee.city = city.getText().toString();
        employee.state = state.getText().toString();
        employee.zip = zip.getText().toString();
        employee.phone = phoneNumber.getText().toString();
        employee.altPhone = altPhone.getText().toString();
        employee.email = email.getText().toString();
        employee.ssn = ssn.getText().toString();
        employee.birthdate = birthday.getText().toString();
        employee.maritalStatus = maritalStatus.getSelectedItemPosition();
        employee.spouseName = spouseName.getText().toString();
        employee.isVeteran = veteran.isChecked();
        employee.isCitizen = citizen.isChecked();

        EmployeeDao.saveEmployee(HireDrive.hiredriveDB, employee);
        return true;
    }
}
