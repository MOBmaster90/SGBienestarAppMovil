package com.mobmasterp.bienestarapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteAPI {

    private static final String HOST = "http://10.187.130.178:3000/";
    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
