package com.ihh.springtest;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class TextRequest {
    @SerializedName("text")
    private String text;

    public TextRequest(String text) {
        this.text = text;
    }

//    public String getText(){
//        return text;
//    }
//    public void setText(String text){
//        this.text = text;
//    }
}


