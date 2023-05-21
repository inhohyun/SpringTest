package com.ihh.springtest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://172.30.1.14:8080"; // Replace with your server URL
    private static Retrofit retrofit;
    private EditText editText;
    private Button sendButton;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.input_text);
        sendButton = findViewById(R.id.btn_send);
        responseTextView = findViewById(R.id.output_text);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                sendTextToServer(text);
            }
        });
    }

    private void sendTextToServer(String text) {

        if (retrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        ApiService apiService = retrofit.create(ApiService.class);

//        TextRequest textRequest = new TextRequest(text);

        Call<String> call = apiService.sendText(text);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String receivedText = response.body();
                    responseTextView.setText(receivedText);
                } else {
                    responseTextView.setText("Request Failed1: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseTextView.setText("Request Failed2: " + t.getMessage());
            }
        });
    }
}
