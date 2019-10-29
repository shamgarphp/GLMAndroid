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
import com.example.sss.goodlife.Models.VendorIds;
import com.example.sss.goodlife.R;

import java.util.ArrayList;
import java.util.List;

public class VendorApproveListAdapter extends BaseAdapter {
   Context context;
    List<VendorIds> list;
    public VendorApproveListAdapter(Context context) {
        this.context = context;
    }

    public VendorApproveListAdapter(Context applicationContext, List<VendorIds> listModel) {
        this.context = applicationContext;
        this.list = listModel;
    }


    @Override
    public int getCount() {
        return list.size();
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
                    inflate(R.layout.vendor_approval_item, viewGroup, false);
        }

        RelativeLayout stampLyt = view.findViewById(R.id.stampLyt);
        ImageView approve = view.findViewById(R.id.approvedImg);
        ImageView reject = view.findViewById(R.id.rejectImg);
        TextView financeCat = view.findViewById(R.id.financeCat);
        TextView loc = view.findViewById(R.id.loc);
        TextView vendorName = view.findViewById(R.id.vendorName);
        TextView totAmt = view.findViewById(R.id.totAmt);

        vendorName.setText(list.get(i).getVendor_name());
        loc.setText(list.get(i).getLocation());
        financeCat.setText(list.get(i).getFinance_category());
        totAmt.setText(list.get(i).getTotal_amount());

      /*  if(i % 2 == 0){
            stampLyt.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
            reject.setVisibility(View.GONE);
        }else {
            stampLyt.setVisibility(View.GONE);
//            approve.setVisibility(View.GONE);
//            reject.setVisibility(View.GONE);

        }*/


        // returns the view for the current row
        return view;
    }
}
