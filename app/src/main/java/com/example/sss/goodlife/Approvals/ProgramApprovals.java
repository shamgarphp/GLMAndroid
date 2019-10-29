package com.example.sss.goodlife.Approvals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.FundsApproveListAdapter;
import com.example.sss.goodlife.Adapters.ProgramApproveListAdapter;
import com.example.sss.goodlife.Adapters.ProgramsIdAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.Models.ProgramIds;
import com.example.sss.goodlife.Models.ProgramIdsStatus;
import com.example.sss.goodlife.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ProgramApprovals extends AppCompatActivity {

    private ProgramApproveListAdapter papld;
    private List<ProgramIds> programIds;
    private ApiService apiService;
    private ListView leaveList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_approvals);
        leaveList = findViewById(R.id.approvalsList);


        //Spinner Dropdown for ProgramNames
        apiService= APIUrl.getApiClient().create(ApiService.class);
        final retrofit2.Call<ProgramIdsStatus> call=apiService.getProgramids();
        call.enqueue(new Callback<ProgramIdsStatus>() {
            @Override
            public void onResponse(retrofit2.Call<ProgramIdsStatus> call, Response<ProgramIdsStatus> response) {
                if (response.body()==null){
                    Toast.makeText(getApplicationContext(),"responce null",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getMessage().size()!=0) {
                    programIds = response.body().getMessage();
                    papld = new ProgramApproveListAdapter(getApplicationContext(),programIds);
                    leaveList.setAdapter(papld);
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ProgramIdsStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.back_nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
