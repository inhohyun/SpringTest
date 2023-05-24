package com.ihh.springtest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("processString")
    Call<StringResponse> processString(@Body StringRequest request);

}