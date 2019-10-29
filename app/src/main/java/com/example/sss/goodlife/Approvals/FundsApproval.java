package com.example.sss.goodlife.Approvals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.FundsApproveListAdapter;
import com.example.sss.goodlife.Adapters.LeaveApproveListAdapter;
import com.example.sss.goodlife.Adapters.ProgramsIdAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.Models.FundApprovalModel;
import com.example.sss.goodlife.Models.FundIds;
import com.example.sss.goodlife.Models.ProgramIds;
import com.example.sss.goodlife.Models.ProgramIdsStatus;
import com.example.sss.goodlife.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class FundsApproval extends AppCompatActivity {

    private FundsApproveListAdapter fapld;
    private ApiService apiService;
    private List<FundIds> programIds;
    ListView leaveList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_approval);
         leaveList = findViewById(R.id.approvalsList);


        //Spinner Dropdown for location
        apiService= APIUrl.getApiClient().create(ApiService.class);
        final retrofit2.Call<FundApprovalModel> call=apiService.getFundApprovals();
        call.enqueue(new Callback<FundApprovalModel>() {
            @Override
            public void onResponse(retrofit2.Call<FundApprovalModel> call, Response<FundApprovalModel> response) {
                if (response.body()==null){
                    Toast.makeText(getApplicationContext(),"responce null",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("Response from Server",""+new Gson().toJson(response));
                if (response.body().getFunds_data().size()!=0) {
                    programIds = response.body().getFunds_data();
//                    for (int i = 0;i<programIds.size();i++){
//                        Log.e("Funds resp","1   "+programIds.get(i).getFund_id());
//                        Log.e("Funds resp","2   "+programIds.get(i).getProgram_name());
//                        Log.e("Funds resp","3   "+programIds.get(i).getCreated_on());
//                        Log.e("Funds resp","4   "+programIds.get(i).getAllocate_fund());
//                        Log.e("Funds resp","5   "+programIds.get(i).getAdvance_issued());
//                        Log.e("Funds resp","6   "+programIds.get(i).getApply_for_advance());
//                    }

                    fapld = new FundsApproveListAdapter(getApplicationContext(),programIds);
                    leaveList.setAdapter(fapld);
                  /*  transportProgramIdSpinner.setPrompt("Select Location");
                    programidsAdapter = new ProgramsIdAdapter(getActivity(), (ArrayList<ProgramIds>) programIds);
                    transportProgramIdSpinner.setAdapter(programidsAdapter);
                    progressDialog.dismiss();
*/
                }
                else {
//                    Toast.makeText(getApplicationContext(),"Programs not forund",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FundApprovalModel> call, Throwable t) {
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
