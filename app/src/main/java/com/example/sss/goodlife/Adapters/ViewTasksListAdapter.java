package com.example.sss.goodlife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.Models.DayListModel;
import com.example.sss.goodlife.R;

import java.util.ArrayList;

public class ViewTasksListAdapter extends BaseAdapter {
   Context context;
    ArrayList<DayListModel> list;
    public ViewTasksListAdapter(Context context) {
        this.context = context;
    }

    public ViewTasksListAdapter(MainActivity applicationContext, ArrayList<DayListModel> listModel) {
        this.context = applicationContext;
        this.list = listModel;
    }


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
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

        if(i % 2 == 0){
            stampLyt.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            reject.setVisibility(View.GONE);
        }else {
            stampLyt.setVisibility(View.GONE);
//            approve.setVisibility(View.GONE);
//            reject.setVisibility(View.GONE);

        }


        // returns the view for the current row
        return view;
    }
}
