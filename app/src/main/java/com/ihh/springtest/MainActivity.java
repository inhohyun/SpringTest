package com.ihh.springtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText inputText;
    private TextView outputText;
    private Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.input_text);
        outputText = findViewById(R.id.output_text);
        sendBtn = findViewById(R.id.btn_send);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:localhost:80")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService service = retrofit.create(ApiService.class);
                Call<String> call = service.sendText(inputText.getText().toString());

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("inhohyun","sucess");
                        outputText.setText(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("inhohyun2","fail");
                        t.printStackTrace();

                    }
                });
            }
        });



    }
}
