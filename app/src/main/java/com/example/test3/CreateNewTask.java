package com.example.test3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateNewTask extends AppCompatActivity {
    Spinner dropdown;
    EditText title;
    Button submit;
    DBHelper db;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_task);
        title = findViewById(R.id.task_title);
        submit = findViewById(R.id.butt_submit);
        db = new DBHelper(this);
        final int UserId = getIntent().getExtras().getInt("id");
        final String name = getIntent().getExtras().getString("name");


        dropdown = findViewById(R.id.task_type);
        String[] items = new String[]{"Important Urgent", "Important not Urgent", "Not Important Urgent", "Not Important not Urgent"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task_value = dropdown.getSelectedItem().toString().trim();
                String task_title = title.getText().toString().trim();

                long val = db.addTask(task_title, task_value, UserId);
                if (val > 0) {
                    Toast.makeText(CreateNewTask.this, "Successfully created", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(CreateNewTask.this, HomeActivity.class);
                    homeIntent.putExtra("name", name);
                    homeIntent.putExtra("id", UserId);
                    startActivity(homeIntent);
                } else {
                    Toast.makeText(CreateNewTask.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
