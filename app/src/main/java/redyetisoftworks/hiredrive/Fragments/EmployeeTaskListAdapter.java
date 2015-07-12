package redyetisoftworks.hiredrive.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import redyetisoftworks.hiredrive.Models.Task;
import redyetisoftworks.hiredrive.R;

/**
 * Created by Collin Sims on 7/4/2015.
 */
public class EmployeeTaskListAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;

    public EmployeeTaskListAdapter(Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
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
            view = mInflater.inflate(R.layout.employee_main_list_item, parent, false);
            wrapper = new ViewWrapper();
            wrapper.taskName = (TextView) view.findViewById(R.id.taskDescription);
            wrapper.taskDone = (ImageView) view.findViewById(R.id.taskCompleted);
            view.setTag(wrapper);
        }
        wrapper = (ViewWrapper) view.getTag();
        bindView(wrapper, position);
        return view;
    }

    protected void bindView(final ViewWrapper wrapper, final int position) {
        final Task task = tasks.get(position);
        wrapper.taskName.setText(task.description);
        if (!task.completed)
            wrapper.taskDone.setVisibility(View.INVISIBLE);
        else
            wrapper.taskDone.setVisibility(View.VISIBLE);
    }

    public static class ViewWrapper {

        private TextView taskName;
        private ImageView taskDone;

    }
}
