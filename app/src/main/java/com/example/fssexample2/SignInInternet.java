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

public class SignInInternet extends AppCompatActivity {
    EditText interID,interPass;
    Button sign;
    TextView forgot,pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_internet);

        getSupportActionBar().setTitle("     Sign In");

        interID=findViewById(R.id.interID);
        interPass=findViewById(R.id.interPass);
        sign=findViewById(R.id.btnPin);
        forgot=findViewById(R.id.forgot);
        pin=findViewById(R.id.pin);

        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preff1=preferences.getString("num",null);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=interID.getText().toString().trim();
                String userPass=interPass.getText().toString().trim();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
                Query check = reference.orderByChild("internetID").equalTo(userID);

                check.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){


                            String passdb=snapshot.child(preff1).child("internetPass").getValue(String.class);

                            if(passdb.equals(userPass)){
                                String walletdb=snapshot.child(preff1).child("wallet").getValue(String.class);
                                String walletbal=snapshot.child(preff1).child("walletb").getValue(String.class);

                                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                                intent.putExtra("wallet",walletdb);
                                intent.putExtra("walletcash",walletbal);
                                startActivity(intent);
                                finish();


                            }else{
                                interPass.setError("wrong password");
                                interPass.requestFocus();
                            }


                        }else{
                            interID.setError("no such ID exist");
                            interID.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),SignInMpin.class);
                startActivity(i);
                finish();
            }
        });



    }
}