package com.example.fssexample2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button signOut;
    TextView wall2,wall4;
    ImageButton fund;
    CardView send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        signOut=findViewById(R.id.signOut);
        wall2=findViewById(R.id.wall2);
        wall4=findViewById(R.id.wall4);
        fund=findViewById(R.id.fund);
        send=findViewById(R.id.bankcardId);

        Intent intent=getIntent();
        String walle=intent.getStringExtra("wallet");
        String walleb=intent.getStringExtra("walletcash");
        wall2.setText(walle);
        wall4.setText(walleb);

        final Intent i1=new Intent(this,MainActivity.class);

        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(),TopUp.class);
                startActivity(i2);
                finish();
            }
        });



        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i1);
                finish();


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 =new Intent(getApplicationContext(),Send.class);
                startActivity(i3);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this).
                setTitle("Exit")
                .setMessage("Are you sure you want to exit")
                .setNegativeButton("no",null)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dashboard.super.onBackPressed();

                    }
                }).create().show();
    }


}