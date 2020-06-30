package com.example.appp3;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class Settings1Activity extends AppCompatActivity {

    private Button aboutButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.drawer_layout);
        initViews();
        addEvents();

    }
    private void initViews() {
        aboutButton = findViewById(R.id.about_button);
    }

    private void addEvents()
    {
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutB = new Intent(Settings1Activity.this, AboutUsActivity.class);
                startActivity(aboutB);
            }
        });
    }
}
