package com.example.fssexample2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TopUpPin extends AppCompatActivity {

    EditText pin;
    Button submit;
    int round=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_pin);

        getSupportActionBar().setTitle("     TopUp");

        pin=findViewById(R.id.setPin);
        submit=findViewById(R.id.btnPin);

        final SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        String wallamount=preferences.getString("top",null);
        String accbal=preferences.getString("accountbal",null);
        String wallbal=preferences.getString("walletbal",null);
        String preff=preferences.getString("pin",null);

        int a= Integer.parseInt(wallamount);
        int b= Integer.parseInt(accbal);
        int c= Integer.parseInt(wallbal);
        if(b>a){
            b=b-a;
            c=c+a;
        }
        String wallamount1=String.valueOf(a);
        String accbal1=String.valueOf(b);
        String wallbal1=String.valueOf(c);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pin.getText().toString().isEmpty() && pin.getText().toString().length()==4){
                    if(preff !=null){
                        if(pin.getText().toString().equals(preff)){
                            editor.putString("walletbal",wallbal1);
                            editor.putString("accountbal",accbal1);
                            editor.commit();
                            Intent i=new Intent(getApplicationContext(),TopUpConfirm.class);
                            startActivity(i);
                            finish();

                        }else{
                            round--;
                            new AlertDialog.Builder(TopUpPin.this).
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
                    pin.setError("enter valid pin");
                }

            }
        });

    }
}