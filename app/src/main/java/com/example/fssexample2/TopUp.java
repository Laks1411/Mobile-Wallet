package com.example.fssexample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TopUp extends AppCompatActivity {
    EditText wallamount,promo;
    Button top;
    TextView bal,account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        getSupportActionBar().setTitle("     TopUp");

        wallamount=findViewById(R.id.wallamount);
        promo=findViewById(R.id.promo);
        bal=findViewById(R.id.wallbal);
        account=findViewById(R.id.account);
        top=findViewById(R.id.top);

        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        String number=preferences.getString("num",null);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query check = reference.orderByChild("mobile").equalTo(number);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String walletbal=snapshot.child(number).child("walletb").getValue(String.class);
                    String accountbal=snapshot.child(number).child("amount").getValue(String.class);
                    bal.setText("Wallet Balance: Rs. "+ walletbal);
                    account.setText("Account Balance: Rs. "+ accountbal);
                    editor.putString("walletbal",walletbal);
                    editor.putString("accountbal",accountbal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!wallamount.getText().toString().isEmpty()){

                    editor.putString("top",wallamount.getText().toString());
                    editor.commit();
                    Intent i=new Intent(getApplicationContext(),TopUpPin.class);
                    startActivity(i);
                    finish();

                }else {
                    wallamount.setError("add some amount");
                }
            }
        });




    }
}