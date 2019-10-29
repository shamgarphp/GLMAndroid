package com.example.sss.goodlife.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUrl {
//    public static final String BASE_URL = "http://www.yesinteriors.online/goodlf/";
    public static final String BASE_URL = "http://www.shamgarhosting.org.in/goodlf/";

    public static Retrofit retrofit=null;

    public static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
