package com.example.fssexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User extends AppCompatActivity {
    EditText dname,dwallet,damount,daccount,dinternetID,dinternetPass,dmobile;
    Button save;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dname=findViewById(R.id.name);
        dwallet=findViewById(R.id.wallet);
        damount=findViewById(R.id.amount);
        daccount=findViewById(R.id.account);
        dinternetID=findViewById(R.id.internetID);
        dinternetPass=findViewById(R.id.internetPass);
        dmobile=findViewById(R.id.mobile);
        save=findViewById(R.id.btnPin);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode= FirebaseDatabase.getInstance(); //rootNode represent our whole data
                reference=rootNode.getReference("users"); //to where we want data from

                String name=dname.getText().toString();
                String wallet=dwallet.getText().toString();
                String amount=damount.getText().toString();
                String account=daccount.getText().toString();
                String internetID=dinternetID.getText().toString();
                String internetPass=dinternetPass.getText().toString();
                String mobile=dmobile.getText().toString();

                UserHelper help=new UserHelper(name,wallet,amount,account,internetID,internetPass,mobile);

                reference.child(mobile).setValue(help);

            }
        });


    }
}