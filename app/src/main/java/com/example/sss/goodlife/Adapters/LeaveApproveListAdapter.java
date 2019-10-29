package com.example.sss.goodlife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.Models.DayListModel;
import com.example.sss.goodlife.Models.LeaveIds;
import com.example.sss.goodlife.R;

import java.util.ArrayList;
import java.util.List;

public class LeaveApproveListAdapter extends BaseAdapter {
   Context context;
    List<LeaveIds> list;
    public LeaveApproveListAdapter(Context context) {
        this.context = context;
    }

    public LeaveApproveListAdapter(Context applicationContext, List<LeaveIds> listModel) {
        this.context = applicationContext;
        this.list = listModel;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // inflate the layout for each list row
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.leave_approval_item, viewGroup, false);
        }

        RelativeLayout stampLyt = view.findViewById(R.id.stampLyt);
        ImageView approve = view.findViewById(R.id.approvedImg);
        ImageView reject = view.findViewById(R.id.rejectImg);
        TextView startDate = view.findViewById(R.id.startDate);
        TextView endDate = view.findViewById(R.id.endDate);
        TextView leaveType = view.findViewById(R.id.leaveType);
        TextView desc = view.findViewById(R.id.desc);

        desc.setText("Description :"+list.get(i).getDescription());
        leaveType.setText("Leave Type :"+list.get(i).getLeave_type());
        startDate.setText(list.get(i).getStart_date());
        endDate.setText(list.get(i).getEnd_date());

/*
        if(i % 2 == 0){
            stampLyt.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            reject.setVisibility(View.GONE);
        }else {
            stampLyt.setVisibility(View.GONE);
//            approve.setVisibility(View.GONE);
//            reject.setVisibility(View.GONE);

        }
*/


        // returns the view for the current row
        return view;
    }
}
