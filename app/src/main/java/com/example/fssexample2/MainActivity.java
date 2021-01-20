package com.example.fssexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=4000;
    private int flag=0;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //auth.getInstance().signOut();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        final Intent i2=new Intent(this,Register.class);
        final Intent i1=new Intent(this,SignInMpin.class);
        final Intent i3=new Intent(this,User.class);


        if(auth.getInstance().getCurrentUser()==null){

            flag=1;

        }else{
           flag=0;

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(flag==1){
                    startActivity(i2);
                    finish();
                }else{
                    startActivity(i1);
                    finish();
                }
                /*startActivity(i3);
                finish();*/

            }
        },SPLASH_TIME_OUT);




    }
}