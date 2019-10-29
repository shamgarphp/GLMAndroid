package com.example.sss.goodlife.Approvals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.ProgramApproveListAdapter;
import com.example.sss.goodlife.Adapters.TransportApproveListAdapter;
import com.example.sss.goodlife.Adapters.VendorApproveListAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.Models.FundIds;
import com.example.sss.goodlife.Models.TransportApprovalModel;
import com.example.sss.goodlife.Models.TransportIds;
import com.example.sss.goodlife.Models.VendorApprovalModel;
import com.example.sss.goodlife.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class TransportApprovals extends AppCompatActivity {

    private TransportApproveListAdapter tapld;
    private ListView leaveList;
    private ApiService apiService;
    private List<TransportIds> programIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_approvals);
        leaveList = findViewById(R.id.approvalsList);




        //Spinner Dropdown for location
        apiService= APIUrl.getApiClient().create(ApiService.class);
        final retrofit2.Call<TransportApprovalModel> call=apiService.getTransportApprovals();
        call.enqueue(new Callback<TransportApprovalModel>() {
            @Override
            public void onResponse(retrofit2.Call<TransportApprovalModel> call, Response<TransportApprovalModel> response) {
                Log.e("Response from Server",""+new Gson().toJson(response));
                if (response.body()==null){
                    Toast.makeText(getApplicationContext(),"responce null",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.body().getTransport_data().size()!=0) {
                    programIds = response.body().getTransport_data();
//                    for (int i = 0;i<programIds.size();i++){
//                        Log.e("Funds resp","1   "+programIds.get(i).getFund_id());
//                        Log.e("Funds resp","2   "+programIds.get(i).getProgram_name());
//                        Log.e("Funds resp","3   "+programIds.get(i).getCreated_on());
//                        Log.e("Funds resp","4   "+programIds.get(i).getAllocate_fund());
//                        Log.e("Funds resp","5   "+programIds.get(i).getAdvance_issued());
//                        Log.e("Funds resp","6   "+programIds.get(i).getApply_for_advance());
//                    }

                    tapld = new TransportApproveListAdapter(getApplicationContext(),programIds);
                    leaveList.setAdapter(tapld);

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
            public void onFailure(retrofit2.Call<TransportApprovalModel> call, Throwable t) {
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
