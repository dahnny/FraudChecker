package com.danielogbuti.spammessaging;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spam spam = new Spam("I have to get a meeting with someone");

        sendNetworkRequest(spam);
    }

    private void sendNetworkRequest(Spam spam) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:60142/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        SpamClient client = retrofit.create(SpamClient.class);
        Call<Spam> call = client.createAccount(spam);

        call.enqueue(new Callback<Spam>() {
            @Override
            public void onResponse(Call<Spam> call, Response<Spam> response) {
                Toast.makeText(MainActivity.this, response.body().getResults(), Toast.LENGTH_SHORT).show();
                Log.e("TAG",response.body().getResults());
            }

            @Override
            public void onFailure(Call<Spam> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG",t.getMessage());
            }
        });


    }
}
