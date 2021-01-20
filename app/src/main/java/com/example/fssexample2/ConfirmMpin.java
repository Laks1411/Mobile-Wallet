package com.example.fssexample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConfirmMpin extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_mpin);

        getSupportActionBar().setTitle("");
        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preff1=preferences.getString("num",null);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query check = reference.orderByChild("mobile").equalTo(preff1);

         Intent intent=new Intent(getApplicationContext(),Dashboard.class);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String walletdb=snapshot.child(preff1).child("wallet").getValue(String.class);
                    String walletbal=snapshot.child(preff1).child("walletb").getValue(String.class);
                    intent.putExtra("wallet",walletdb);
                    intent.putExtra("walletcash",walletbal);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);


    }
}