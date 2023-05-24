package com.ihh.springtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
                sendStringToServer(text);
            }
        });
    }

    private void sendStringToServer(String inputString) {
        // Create a request object
        StringRequest request = new StringRequest(inputString);

        // Create an instance of the Retrofit API service
        ApiService apiService = RetrofitClient.getApiService();

        // Make the network request
        Call<StringResponse> call = apiService.processString(request);
        call.enqueue(new Callback<StringResponse>() {
            @Override
            public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    StringResponse stringResponse = response.body();
                    if (stringResponse != null) {
                        // Display the result
                        responseTextView.setText(stringResponse.getResult());
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Request failed with code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StringResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}