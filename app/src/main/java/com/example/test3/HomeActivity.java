package com.example.test3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {
    TextView userName;
    Button createNewTask;
    DBHelper db;
    ListView ImporUrgent, ImporNotUrgent, NotImporUrgent, NotImporNotUrgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DBHelper(this);
        ImporUrgent = findViewById(R.id.ImporUrgent);
        ImporNotUrgent = findViewById(R.id.ImporNotUrgent);
        NotImporUrgent = findViewById(R.id.NotImporUrgent);
        NotImporNotUrgent = findViewById(R.id.NotImporNotUrgent);

        final int UserId = getIntent().getExtras().getInt("id");
        String name = "No name user";
        name = getIntent().getExtras().getString("name");
        userName = findViewById(R.id.greetings);
        userName.setText("Welcome, " + name);

        getLists("Important Urgent", UserId, ImporUrgent);
        getLists("Important not Urgent", UserId, ImporNotUrgent);
        getLists("Not Important Urgent", UserId, NotImporUrgent);
        getLists("Not Important not Urgent", UserId, NotImporNotUrgent);


        createNewTask = findViewById(R.id.create_default);
        final String finalName = name;
        createNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(HomeActivity.this, CreateNewTask.class);
                createIntent.putExtra("id", UserId);
                createIntent.putExtra("name", finalName);
                startActivity(createIntent);
            }
        });
    }

    private void getLists(String param, int UserId, ListView container) {
        String[] vals = db.getListData(param, UserId);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, vals);

        //TODO передавати параметр куда записувати
        container.setAdapter(adapter);
    }



}
