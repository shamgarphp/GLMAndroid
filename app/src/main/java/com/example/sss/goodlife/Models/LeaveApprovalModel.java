package com.example.sss.goodlife.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveApprovalModel {  @SerializedName("status")
String Status;

    @SerializedName("leave_data")
    List<LeaveIds> Leave_data;



    public LeaveApprovalModel(String status, List<LeaveIds> leave_data) {
        Status = status;
        Leave_data = leave_data;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<LeaveIds> getLeave_data() {
        return Leave_data;
    }

    public void setLeave_data(List<LeaveIds> leave_data) {
        this.Leave_data = leave_data;
    }
}
