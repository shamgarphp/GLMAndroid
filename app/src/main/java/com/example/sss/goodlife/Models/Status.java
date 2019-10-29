package com.example.sss.goodlife.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Status {
    @SerializedName("status")
    String Status;

    @SerializedName("message")
    List<Locations> Message;

    public Status(String status, List<Locations> message) {
        Status = status;
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Locations> getMessage() {
        return Message;
    }

    public void setMessage(List<Locations> message) {
        Message = message;
    }
}
