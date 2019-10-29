package com.example.sss.goodlife.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransportApprovalModel {  @SerializedName("status")
String Status;

    @SerializedName("transport_data")
    List<TransportIds> Transport_data;



    public TransportApprovalModel(String status, List<TransportIds> transport_data) {
        Status = status;
        Transport_data = transport_data;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<TransportIds> getTransport_data() {
        return Transport_data;
    }

    public void setTransport_data(List<TransportIds> transport_data) {
        Transport_data = transport_data;
    }
}
