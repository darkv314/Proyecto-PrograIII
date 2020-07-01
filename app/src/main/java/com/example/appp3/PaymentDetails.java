package com.example.appp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView txtId,txtAmount, txtStatus;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.payment_details);

        txtId =  (TextView)findViewById(R.id.txtId);
        txtAmount = (TextView)findViewById((R.id.txtAmount));
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String paymentAmount){
        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText(response.getString(String.format("$%s",paymentAmount)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}