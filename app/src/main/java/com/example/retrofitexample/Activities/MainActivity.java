package com.example.retrofitexample.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofitexample.R;
import com.example.retrofitexample.RetrofitClient;
import com.example.retrofitexample.modelResponse.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textViewLogin;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initLayout();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOnLogin();
            }
        });
    }

    private void initLayout() {
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
    }

    private void registerUser() {
        String userName = editTextName.getText().toString();
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (userName.isEmpty()) {
            Toast.makeText(this, "enter user name", Toast.LENGTH_SHORT).show();
            return;
        } else if (userEmail.isEmpty()) {
            Toast.makeText(this, "enter user email", Toast.LENGTH_SHORT).show();
            return;
        } else if (!userEmail.matches(emailPattern)) {
            Toast.makeText(this, "enter valid email", Toast.LENGTH_SHORT).show();
            return;
        } else if (userPassword.isEmpty()) {
            Toast.makeText(this, "enter user password", Toast.LENGTH_SHORT).show();
            return;
        } else if (userPassword.length() < 5) {
            Toast.makeText(this, "password length must be greater than four", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
        }

        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(userName, userEmail, userPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, registerResponse.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }


    private void switchOnLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
