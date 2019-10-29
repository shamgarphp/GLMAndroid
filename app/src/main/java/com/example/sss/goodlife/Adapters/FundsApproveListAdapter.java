package com.example.sss.goodlife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.Models.DayListModel;
import com.example.sss.goodlife.Models.FundIds;
import com.example.sss.goodlife.R;
import java.util.List;

import java.util.ArrayList;

public class FundsApproveListAdapter extends BaseAdapter {
   Context context;
    List<FundIds> list;

    public FundsApproveListAdapter(Context applicationContext, List<FundIds> listModel) {
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
                    inflate(R.layout.fund_approval_item, viewGroup, false);
        }

        RelativeLayout stampLyt = view.findViewById(R.id.stampLyt);
        ImageView approve = view.findViewById(R.id.approvedImg);
        ImageView reject = view.findViewById(R.id.rejectImg);
        TextView prgname    = view.findViewById(R.id.prgname);
        TextView apply      = view.findViewById(R.id.apply);
        TextView applyadv   = view.findViewById(R.id.applyadv);
        TextView allocate   = view.findViewById(R.id.allocate);
        TextView createOn   = view.findViewById(R.id.createOn);

            createOn.setText(list.get(i).getCreated_on());
            prgname.setText("Program:"+list.get(i).getProgram_name());
            apply.setText("Advance: "+list.get(i).getAdvance_issued());
            allocate.setText("Allocated: "+list.get(i).getAllocate_fund());
            applyadv.setText("Applied: "+list.get(i).getApply_for_advance());
        /*if(i % 2 == 0){
            stampLyt.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            reject.setVisibility(View.GONE);
        }else {
            stampLyt.setVisibility(View.GONE);
        }
*/
        // returns the view for the current row
        return view;
    }
}
