package redyetisoftworks.hiredrive.Fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import redyetisoftworks.hiredrive.R;

/**
 * Created by dyost on 1/16/14.
 */
public class NavigationAdapter extends ArrayAdapter<String> {

    private List<String> menuList;
    private Context context;

    public NavigationAdapter(Context context, int resource, List<String> menuList) {
        super(context, resource, menuList);

        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String menuItem = menuList.get(position);

        View v = convertView;
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(context);
            v = li.inflate(R.layout.item_navigation, null);
        }

        TextView tvText = (TextView) v.findViewById(R.id.nav_item_text);
        ImageView imgIcon = (ImageView) v.findViewById(R.id.nav_item_img);

        tvText.setText(menuItem);
        switch (position) {
            case 0: //Summary
                imgIcon.setImageResource(R.drawable.ic_home);
                break;
            case 1: //Basic Info
                imgIcon.setImageResource(R.drawable.ic_whats_hot);
                break;
            case 2: //Dates
                imgIcon.setImageResource(R.drawable.ic_communities);
                break;
           /* case 3: //Agents
                imgIcon.setImageResource(R.drawable.dr_agents);
                break;
            case 4: //Origin
                imgIcon.setImageResource(R.drawable.dr_origin);
                break;
            case 5: //Destination
                imgIcon.setImageResource(R.drawable.dr_destination);
                break;
            case 6: //Survey
                imgIcon.setImageResource(R.drawable.dr_survey);
                break;
            case 7: //Notes
                imgIcon.setImageResource(R.drawable.dr_notes);
                break;
            case 8: //Move Info
                imgIcon.setImageResource(R.drawable.dr_move_information);
                break;
            case 9: //Doc Library
                imgIcon.setImageResource(R.drawable.dr_doc_library);
                break;
            case 10: //Pricing
                imgIcon.setImageResource(R.drawable.dr_pricing);
                break;
            case 11: //Pricing Summary
                imgIcon.setImageResource(R.drawable.dr_price_summary);
                break;
            case 12: //Pack Crate Summary
                imgIcon.setImageResource(R.drawable.dr_pack_crate_summary);
                break;*/
        }

        return v;
    }
}