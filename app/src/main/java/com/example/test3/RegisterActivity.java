package com.example.test3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextConfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    DBHelper db;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);
        mTextUsername = findViewById(R.id.username);
        mTextPassword = findViewById(R.id.password);
        mTextConfPassword = findViewById(R.id.conf_password);
        mButtonRegister = findViewById(R.id.butt_register);
        mTextViewLogin = findViewById(R.id.goToLogin);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextConfPassword.getText().toString().trim();
//                int exists = db.checkUser(user, pwd);

//                if(exists) {
                    if (pwd.equals(cnf_pwd)) {
                        long val = db.addUser(user, pwd);
                        if (val > 0) {
                            Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                            /*Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(moveToLogin);*/
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
