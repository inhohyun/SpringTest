package com.ihh.springtest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/sendText")
    Call<String> sendText(@Body String text);

}
