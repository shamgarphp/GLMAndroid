package com.example.sss.goodlife.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FundApprovalModel {  @SerializedName("status")
String Status;

    @SerializedName("funds_data")
    List<FundIds> Funds_data;



    public FundApprovalModel(String status, List<FundIds> funds_data) {
        Status = status;
        Funds_data = funds_data;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<FundIds> getFunds_data() {
        return Funds_data;
    }

    public void setFunds_data(List<FundIds> funds_data) {
        Funds_data = funds_data;
    }
}
