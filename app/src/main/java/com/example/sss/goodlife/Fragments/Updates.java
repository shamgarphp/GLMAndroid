package com.example.sss.goodlife.Fragments;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.sss.goodlife.Adapters.MySplashScreenAdapter;
import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.R;
import com.example.sss.goodlife.Utils.Utilities;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Updates  extends Fragment {

        RelativeLayout prayer,notification,localevents;

    private ViewPager homePager;
    private CircleIndicator indicator;


    private static final Integer[] slideImages= {R.drawable.goodlife_logo,R.drawable.g,R.drawable.goodlife_logo,R.drawable.g};
    private ArrayList<Integer> slidImagesArray = new ArrayList<Integer>();
    private static int currentPage = 0;

    public Updates() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Updates");
        View view= inflater.inflate(R.layout.activity_updates, container, false);

        indicator = (CircleIndicator)view.findViewById(R.id.indicator);
        homePager=view.findViewById(R.id.pager);
        localevents = view.findViewById(R.id.localevents);
        notification = view.findViewById(R.id.notification);
        prayer = view.findViewById(R.id.prayer);

        localevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        prayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Utilities.getSharedInstance().setHoverForButtons(localevents);
        Utilities.getSharedInstance().setHoverForButtons(notification);
        Utilities.getSharedInstance().setHoverForButtons(prayer);
    }



}
