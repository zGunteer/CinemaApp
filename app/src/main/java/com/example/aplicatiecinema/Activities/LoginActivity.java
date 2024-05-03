package com.example.aplicatiecinema.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicatiecinema.R;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdt, passEdt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView()
    {
        userEdt=findViewById(R.id.editTextText);
        passEdt=findViewById(R.id.editTextPassword);
        loginBtn=findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEdt.getText().toString().isEmpty() || passEdt.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Please fill your Username and Password", Toast.LENGTH_SHORT).show();

                }
                else if(userEdt.getText().toString().equals("test") && passEdt.getText().toString().equals("test"))
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}