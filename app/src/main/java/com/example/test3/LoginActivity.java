package com.example.test3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DBHelper db;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        mTextUsername = findViewById(R.id.username);
        mTextPassword = findViewById(R.id.password);
        mButtonLogin = findViewById(R.id.butt_login);
        mTextViewRegister = findViewById(R.id.goToRegister);


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename = mTextUsername.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                int res = db.checkUser(usename, password);

                Log.d(TAG, "resuuult"+res);

                if(res != -1){
                    Toast.makeText(LoginActivity.this, "Successfully logged", Toast.LENGTH_SHORT).show();

                    Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
                    toHome.putExtra("name", usename);
                    toHome.putExtra("id", res);
                    startActivity(toHome);
                } else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
