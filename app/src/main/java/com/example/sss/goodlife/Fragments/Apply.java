package com.example.sss.goodlife.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.sss.goodlife.Adapters.MySplashScreenAdapter;
import com.example.sss.goodlife.Apply.ApplyFunds;
import com.example.sss.goodlife.Apply.ApplyLeave;
import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.R;
import com.example.sss.goodlife.Utils.Utilities;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Apply extends Fragment {

    RelativeLayout transport,vendorenroll,leave,funds,programs;

    private ViewPager homePager;
    private CircleIndicator indicator;


    private static final Integer[] slideImages= {R.drawable.goodlife_logo,R.drawable.g,R.drawable.goodlife_logo,R.drawable.g};
    private ArrayList<Integer> slidImagesArray = new ArrayList<Integer>();
    private static int currentPage = 0;
    public Apply() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Apply");
        View view= inflater.inflate(R.layout.fragment_apply, container, false);

        indicator = (CircleIndicator)view.findViewById(R.id.indicator);
        homePager=view.findViewById(R.id.pager);
        programs = view.findViewById(R.id.programs);
        vendorenroll = view.findViewById(R.id.vendorenroll);
        transport = view.findViewById(R.id.transport);
        funds = view.findViewById(R.id.funds);
        leave = view.findViewById(R.id.leave);

        programs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                VBSProgramApplication fragment=new VBSProgramApplication();
                transaction.replace(R.id.frameContainer, fragment);
                transaction.commit();
            }
        });
        funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(getActivity(), ApplyFunds.class);
               startActivity(i);
            }
        });
        vendorenroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                VbsFinanceApplication fragment=new VbsFinanceApplication();
                transaction.replace(R.id.frameContainer, fragment);
                transaction.commit();
            }
        });
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                VbsTransportApplication fragment=new VbsTransportApplication();
                transaction.replace(R.id.frameContainer, fragment);
                transaction.commit();
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ApplyLeave.class);
                startActivity(i);
            }
        });

        setHover();
        init();




        return view;
    }


    private void init() {
        for(int i=0;i<slideImages.length;i++)
            slidImagesArray.add(slideImages[i]);

        homePager.setAdapter(new MySplashScreenAdapter(getActivity(),slidImagesArray));
        indicator.setViewPager(homePager);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == slideImages.length) {
                    currentPage = 0;
                }
                homePager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 2500);

    }

    private void setHover(){
        Utilities.getSharedInstance().setHoverForButtons(programs);
        Utilities.getSharedInstance().setHoverForButtons(funds);
        Utilities.getSharedInstance().setHoverForButtons(leave);
        Utilities.getSharedInstance().setHoverForButtons(vendorenroll);
        Utilities.getSharedInstance().setHoverForButtons(transport);
    }
}
