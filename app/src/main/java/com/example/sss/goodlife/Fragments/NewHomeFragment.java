package com.example.sss.goodlife.Fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sss.goodlife.Adapters.MySplashScreenAdapter;
import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.R;
import com.example.sss.goodlife.Utils.Utilities;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class NewHomeFragment extends  Fragment implements View.OnClickListener{

    private TextView btn_home_vbs_back;
    RelativeLayout taskBtn,updatesBtn,approvals,apply;
    private ViewPager homePager;
    private CircleIndicator indicator;


    private static final Integer[] slideImages= {R.drawable.goodlife_logo,R.drawable.g,R.drawable.goodlife_logo,R.drawable.g};
    private ArrayList<Integer> slidImagesArray = new ArrayList<Integer>();
    private static int currentPage = 0;

    public NewHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Home");
        View view= inflater.inflate(R.layout.fragment_new_home, container, false);
        ImageView imageView = view.findViewById(R.id.updateIcon);

        imageView.setImageResource(R.drawable.update);

        imageView.setColorFilter(imageView.getContext().getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);

        indicator = (CircleIndicator)view.findViewById(R.id.indicator);
        homePager=view.findViewById(R.id.pager);
        updatesBtn = view.findViewById(R.id.updatesBtn);
        approvals = view.findViewById(R.id.approvals);
        apply = view.findViewById(R.id.apply);
        taskBtn = view.findViewById(R.id.taskBtn);

        btn_home_vbs_back=view.findViewById(R.id.btn_home_vbs_back);


        init();
        buttonHover();
        addClickListeners();
        btn_home_vbs_back.setOnClickListener(this);




        return view;
    }

    private void addClickListeners(){
        updatesBtn.setOnClickListener(this);
        apply.setOnClickListener(this);
        approvals.setOnClickListener(this);
        taskBtn.setOnClickListener(this);
    }
    private void buttonHover(){
        Utilities.getSharedInstance().setHoverForButtons(updatesBtn);
        Utilities.getSharedInstance().setHoverForButtons(approvals);
        Utilities.getSharedInstance().setHoverForButtons(apply);
        Utilities.getSharedInstance().setHoverForButtons(taskBtn);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.updatesBtn:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Updates fragment=new Updates();
                transaction.replace(R.id.frameContainer, fragment);
                transaction.commit();
                break;
            case R.id.apply:
                FragmentManager manager1 = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction1 = manager1.beginTransaction();
                Apply fragment1=new Apply();
                transaction1.replace(R.id.frameContainer, fragment1);
                transaction1.commit();
                break;
            case R.id.approvals:
                FragmentManager manager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction2 = manager2.beginTransaction();
                Approvals fragment2=new Approvals();
                transaction2.replace(R.id.frameContainer, fragment2);
                transaction2.commit();
                break;

            case R.id.taskBtn:
                FragmentManager manager3 = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction3 = manager3.beginTransaction();
                ViewTasks fragment3=new ViewTasks();
                transaction3.replace(R.id.frameContainer, fragment3);
                transaction3.commit();
                break;
        }

    }
}