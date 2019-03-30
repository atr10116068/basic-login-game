package com.example.wakhid.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    public static final String URL = "https://webtopix.000webhostapp.com/";
    private ProgressDialog progress;

    @BindView(R.id.editTextUsername) EditText editTextUsername;
    @BindView(R.id.editTextNama) EditText editTextNama;
    @BindView(R.id.editTextPassword) EditText editTextPassword;
    @BindView(R.id.editTextPassword2) EditText editTextPassword2;

    @OnClick(R.id.buttonDaftar) void daftar() {
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        //mengambil data dari edittext
        String username = editTextUsername.getText().toString();
        String nama = editTextNama.getText().toString();
        String password = editTextPassword.getText().toString();
        String password2 = editTextPassword2.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("tpx","build retrofit success");
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Log.d("tpx","persiapan call");
        Call<Value> call = api.daftar(username, nama, password, password2);
        Log.d("tpx","call...");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                }
                Log.d("tpx","mendapat respon");
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Log.d("tpx",String.valueOf(t));
                Toast.makeText(Register.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
}
