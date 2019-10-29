package com.example.sss.goodlife.Approvals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.FundsApproveListAdapter;
import com.example.sss.goodlife.Adapters.TransportApproveListAdapter;
import com.example.sss.goodlife.Adapters.VendorApproveListAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.Models.FundApprovalModel;
import com.example.sss.goodlife.Models.FundIds;
import com.example.sss.goodlife.Models.VendorApprovalModel;
import com.example.sss.goodlife.Models.VendorIds;
import com.example.sss.goodlife.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class VendorEnrollmentApprovals extends AppCompatActivity {

    private VendorApproveListAdapter vapld;
    private ApiService apiService;
    private List<VendorIds> programIds;
    private ListView leaveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_enrollment_approvals);

        leaveList = findViewById(R.id.approvalsList);



        //Spinner Dropdown for location
        apiService= APIUrl.getApiClient().create(ApiService.class);
        final retrofit2.Call<VendorApprovalModel> call=apiService.getVendorApprovals();
        call.enqueue(new Callback<VendorApprovalModel>() {
            @Override
            public void onResponse(retrofit2.Call<VendorApprovalModel> call, Response<VendorApprovalModel> response) {
                if (response.body()==null){
                    Toast.makeText(getApplicationContext(),"responce null",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("Response from Server",""+new Gson().toJson(response));
                if (response.body().getVendor_data().size()!=0) {
                    programIds = response.body().getVendor_data();
//                    for (int i = 0;i<programIds.size();i++){
//                        Log.e("Funds resp","1   "+programIds.get(i).getFund_id());
//                        Log.e("Funds resp","2   "+programIds.get(i).getProgram_name());
//                        Log.e("Funds resp","3   "+programIds.get(i).getCreated_on());
//                        Log.e("Funds resp","4   "+programIds.get(i).getAllocate_fund());
//                        Log.e("Funds resp","5   "+programIds.get(i).getAdvance_issued());
//                        Log.e("Funds resp","6   "+programIds.get(i).getApply_for_advance());
//                    }

                    vapld = new VendorApproveListAdapter(getApplicationContext(),programIds);
                    leaveList.setAdapter(vapld);

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
            public void onFailure(retrofit2.Call<VendorApprovalModel> call, Throwable t) {
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
