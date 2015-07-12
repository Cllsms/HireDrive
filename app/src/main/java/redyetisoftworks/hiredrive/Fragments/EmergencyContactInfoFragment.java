package redyetisoftworks.hiredrive.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import redyetisoftworks.hiredrive.R;

/**
 * Created by Collin Sims on 7/12/2015.
 */
public class EmergencyContactInfoFragment extends Fragment {

    private int empID;
    private ImageButton prev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empID = getArguments().getInt("EmployeeID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_emergency_contact_fragment, container, false);
        prev = (ImageButton) rootView.findViewById(R.id.EmergencyContactInfoPrev);




        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }
}
