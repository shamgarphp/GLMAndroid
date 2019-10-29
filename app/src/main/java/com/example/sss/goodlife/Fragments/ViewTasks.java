package com.example.sss.goodlife.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.sss.goodlife.Adapters.FundsApproveListAdapter;
import com.example.sss.goodlife.Adapters.ViewTasksListAdapter;
import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.R;
import com.example.sss.goodlife.Utils.Utilities;

public class ViewTasks extends Fragment {

    RelativeLayout createTaskBtn;
    ListView taskListView;
    private ViewTasksListAdapter vapld;
    public ViewTasks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tasks Application");
        final View view = inflater.inflate(R.layout.fragment_view_task, container, false);

        createTaskBtn = view.findViewById(R.id.createTask);
        taskListView = view.findViewById(R.id.taskLstView);

        vapld = new ViewTasksListAdapter(getActivity());
        taskListView.setAdapter(vapld);
        Utilities.getSharedInstance().setHoverForButtons(createTaskBtn);

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager3 = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction3 = manager3.beginTransaction();
                Tasks fragment3=new Tasks();
                transaction3.replace(R.id.frameContainer, fragment3);
                transaction3.commit();
            }
        });

        return view;
    }
}
