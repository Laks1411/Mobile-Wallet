package com.example.fssexample2;

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
import android.widget.Toast;

public class SetMpin extends AppCompatActivity {

    EditText Setpin,Confirmpin;
    Button nextPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mpin);

        getSupportActionBar().setTitle("     Sign Up");

        Setpin=findViewById(R.id.setPin);
        Confirmpin=findViewById(R.id.Conpin);
        nextPin=findViewById(R.id.btnPin);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        nextPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Setpin.getText().toString().isEmpty() && Setpin.getText().toString().length()==4){
                    if(!Confirmpin.getText().toString().isEmpty() && Confirmpin.getText().toString().length()==4){
                        if(Setpin.getText().toString().equals(Confirmpin.getText().toString())){
                            String pin=Setpin.getText().toString();
                            editor.putString("pin",Setpin.getText().toString());
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(),ConfirmMpin.class));
                            finish();
                        }else{
                            new AlertDialog.Builder(SetMpin.this).
                                    setTitle("Mismatch")
                                    .setMessage("Above MPIN does not match, please check again")
                                    .setNeutralButton("OK",null)
                                    .create().show();
                        }

                    }else{
                        Confirmpin.setError("Mpin is not valid");
                    }

                }else{
                    Setpin.setError("Mpin is not valid");
                }
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
                        SetMpin.super.onBackPressed();

                    }
                }).create().show();
    }

}