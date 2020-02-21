package com.danielogbuti.spammessaging;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageListener {

    TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);


        MessageReceiver.bindListener(this);

    }

    private void sendNetworkRequest(Spam spam) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://fraud-sms-app.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        SpamClient client = retrofit.create(SpamClient.class);
        Call<Spam> call = client.createAccount(spam);

        call.enqueue(new Callback<Spam>() {
            @Override
            public void onResponse(Call<Spam> call, Response<Spam> response) {
                Toast.makeText(MainActivity.this, response.body().getResults(), Toast.LENGTH_SHORT).show();
                textView.setText(response.body().getResults());
                Log.e("TAG",response.body().getResults());
            }

            @Override
            public void onFailure(Call<Spam> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG",t.getMessage());
            }
        });
    }

    @Override
    public void messageReceived(List<String> message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.i("TAG",message.get(1));
        textView2.setText(message.get(1));

        if(!getb(message.get(0),MainActivity.this)) {
            Spam spam = new Spam(message.get(1));
            sendNetworkRequest(spam);
        }


    }

    public Boolean getb(String name, Context context) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
            Toast.makeText(context, ""+ret, Toast.LENGTH_SHORT).show();
        }
        c.close();
        if(ret==null)
            return false;
        else {
            return true;
        }
    }
}

