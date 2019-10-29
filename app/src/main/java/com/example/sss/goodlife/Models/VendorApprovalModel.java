package com.example.sss.goodlife.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorApprovalModel {  @SerializedName("status")
String Status;

    @SerializedName("vendor_data")
    List<VendorIds> Vendor_data;



    public VendorApprovalModel(String status, List<VendorIds> vendor_data) {
        Status = status;
        Vendor_data = vendor_data;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<VendorIds> getVendor_data() {
        return Vendor_data;
    }

    public void setVendor_data(List<VendorIds> vendor_data) {
        Vendor_data = vendor_data;
    }
}
