package com.example.appp3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static java.text.MessageFormat.format;


public class AboutUsActivity extends AppCompatActivity {

    private Button supportButton;
    private Button donateButton;
    private Button wappButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        context=this;
        initViews();
        addEvents();
    }
    private void initViews() {
        supportButton = findViewById(R.id.support_button);
        donateButton= findViewById(R.id.donate_button);
        wappButton= findViewById(R.id.wapp_button);
    }
    private void addEvents()
    {
        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/Q10Pvh1JoqiMc_2ccbvnMA")));
            }
        });
        wappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone=" + "+591 73260116" + "&text=" + "Tengo una duda con el producto.";
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateBB = new Intent(AboutUsActivity.this, PayPalActivity.class);
                startActivity(donateBB);
            }
        });
    }
}
