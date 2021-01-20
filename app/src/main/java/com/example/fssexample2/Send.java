package com.example.fssexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Send extends AppCompatActivity {
    Button contact,account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        contact=findViewById(R.id.contact);
        account=findViewById(R.id.account);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 =new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i1);
                finish();
            }
        });


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}