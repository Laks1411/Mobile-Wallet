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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TopUpConfirm extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=5000;
    DatabaseReference reference;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_confirm);

        reference= FirebaseDatabase.getInstance().getReference("users");

        textView=findViewById(R.id.textView);

        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);

        String wallamount=preferences.getString("top",null);
        String accbal=preferences.getString("accountbal",null);
        String wallbal=preferences.getString("walletbal",null);
        String preff1=preferences.getString("num",null);

        textView.setText(wallamount+" amount has been added to wallet");

        reference.child(preff1).child("walletb").setValue(wallbal);
        reference.child(preff1).child("amount").setValue(accbal);

        Query check = reference.orderByChild("mobile").equalTo(preff1);

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