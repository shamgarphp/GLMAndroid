package com.example.sss.goodlife.Approvals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.FundsApproveListAdapter;
import com.example.sss.goodlife.Adapters.LeaveApproveListAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.Models.LeaveApprovalModel;
import com.example.sss.goodlife.Models.LeaveIds;
import com.example.sss.goodlife.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class LeaveApprovals extends AppCompatActivity {

    LeaveApproveListAdapter lapld;
    ListView leaveList;
    private ApiService apiService;
    private List<LeaveIds> programIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_approvals);

        leaveList= findViewById(R.id.approvalsList);




        //Spinner Dropdown for location
        apiService= APIUrl.getApiClient().create(ApiService.class);
        final retrofit2.Call<LeaveApprovalModel> call=apiService.getLeaveApprovals();
        call.enqueue(new Callback<LeaveApprovalModel>() {
            @Override
            public void onResponse(retrofit2.Call<LeaveApprovalModel> call, Response<LeaveApprovalModel> response) {
                if (response.body()==null){
                    Toast.makeText(getApplicationContext(),"responce null",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("Response from Server",""+new Gson().toJson(response));
                if (response.body().getLeave_data().size()!=0) {
                    programIds = response.body().getLeave_data();

                    lapld = new LeaveApproveListAdapter(getApplicationContext(),programIds);
                    leaveList.setAdapter(lapld);
                }
                else {
//                    Toast.makeText(getApplicationContext(),"Programs not forund",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LeaveApprovalModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
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
