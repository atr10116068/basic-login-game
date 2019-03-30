package com.example.wakhid.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText usernameV,passwordV;
    public static final String URL = "https://webtopix.000webhostapp.com/";
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameV = (EditText) findViewById(R.id.ETusername);
        passwordV =(EditText) findViewById(R.id.ETpassword);
        ButterKnife.bind(this);
    }

    public void regis(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void msk(View view) {
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        //mengambil data dari edittext
        String username = usernameV.getText().toString();
        String password = passwordV.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("tpx1","build retrofit success");
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Log.d("tpx1","persiapan call");
        Call<Value> call = api.masok(username, password);
        Log.d("tpx1","call...");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Menu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                Log.d("tpx1","mendapat respon");
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Log.d("tpx1",String.valueOf(t));
                Toast.makeText(MainActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
