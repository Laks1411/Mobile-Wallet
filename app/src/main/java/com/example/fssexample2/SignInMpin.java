package com.example.fssexample2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInMpin extends AppCompatActivity {

    EditText mpin;
    TextView forgot,internet;
    Button submit;
    int round=3;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_mpin);

        getSupportActionBar().setTitle("     Sign In");

        mpin=findViewById(R.id.setPin);
        submit=findViewById(R.id.btnPin);
        forgot=findViewById(R.id.forgot);
        internet=findViewById(R.id.internet);

        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");

                if(!mpin.getText().toString().isEmpty() && mpin.getText().toString().length()==4){
                    String preff=preferences.getString("pin",null);
                    String preff1=preferences.getString("num",null);
                    Query check = reference.orderByChild("mobile").equalTo(preff1);
                    if(preff !=null){
                        if(mpin.getText().toString().equals(preff)){

                            check.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        String walletdb=snapshot.child(preff1).child("wallet").getValue(String.class);
                                        String walletbal=snapshot.child(preff1).child("walletb").getValue(String.class);
                                        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                                        intent.putExtra("wallet",walletdb);
                                        intent.putExtra("walletcash",walletbal);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }else{
                            round--;
                            new AlertDialog.Builder(SignInMpin.this).
                                    setTitle("Incorrect MPIN")
                                    .setMessage("You have "+round+" attempt(s) left to enter correct MPIN")
                                    .setNeutralButton("OK",null)
                                    .create().show();
                            if(round==0){
                                submit.setEnabled(false);
                            }

                        }

                    }

                }else{
                    mpin.setError("mpin is invalid");
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove("pin");
                editor.remove("num");
                editor.clear();
                editor.commit();
                auth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });

        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignInInternet.class));
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
                        SignInMpin.super.onBackPressed();

                    }
                }).create().show();
    }


}