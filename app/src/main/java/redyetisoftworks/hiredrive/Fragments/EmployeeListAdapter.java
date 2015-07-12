package redyetisoftworks.hiredrive.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import redyetisoftworks.hiredrive.Models.Employee;
import redyetisoftworks.hiredrive.R;

/**
 * Created by Collin Sims on 4/5/2015.
 */
public class EmployeeListAdapter extends ArrayAdapter<Employee> {
    private Context context;
    private ArrayList<Employee> employees;

    public EmployeeListAdapter(Context context, int resource, ArrayList<Employee> employees) {
        super(context, resource, employees);

        this.context = context;
        this.employees = employees;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Employee getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewWrapper wrapper;
        View view = convertView;
        if (view == null) {
            final LayoutInflater mInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.employee_list_item, parent, false);
            wrapper = new ViewWrapper();
            wrapper.empName = (TextView) view.findViewById(R.id.employeeName);
            wrapper.empPos = (TextView) view.findViewById(R.id.employeePosition);
            wrapper.empEmp = (TextView) view.findViewById(R.id.employeeEmployer);
            view.setTag(wrapper);
        }
        wrapper = (ViewWrapper) view.getTag();
        bindView(wrapper, position);
        return view;
    }

    protected void bindView(final ViewWrapper wrapper, final int position) {
        final Employee employee = getItem(position);
        wrapper.empName.setText(employee.firstName + ", " + employee.lastName + " " + employee.middleInitial);
    }

    public static class ViewWrapper {

        private TextView empName;
        private TextView empPos;
        private TextView empEmp;

    }
}
