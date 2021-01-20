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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    FirebaseAuth fAuth;
    EditText phoneNumber,codeEnter;
    Button nextBtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("     Sign Up");

        fAuth = FirebaseAuth.getInstance();
        phoneNumber=findViewById(R.id.phone);
        codeEnter=findViewById(R.id.codeEnter);
        progressBar=findViewById(R.id.progressBar);
        nextBtn= findViewById(R.id.nextBtn);
        state=findViewById(R.id.state);
        codePicker=findViewById(R.id.ccp);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verificationInProgress){
                    String num=phoneNumber.getText().toString();
                    if(!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length()==10){

                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
                        Query check= reference.orderByChild("mobile").equalTo(num);

                        check.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    editor.putString("num",phoneNumber.getText().toString());
                                    editor.commit();
                                    String phoneNum = "+"+codePicker.getSelectedCountryCode()+phoneNumber.getText().toString();
                                    progressBar.setVisibility(View.VISIBLE);
                                    state.setText("Sending OTP...");
                                    state.setVisibility(View.VISIBLE);
                                    requestOTP(phoneNum);
                                }else{
                                    phoneNumber.setError("phone number does not exist");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        /*String phoneNum = "+"+codePicker.getSelectedCountryCode()+phoneNumber.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP...");
                        state.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);*/

                    }
                    else{
                        phoneNumber.setError("Phone Number is not valid");
                    }
                }
                else{
                    String userOTP= codeEnter.getText().toString();
                    if(!userOTP.isEmpty() && userOTP.length()==6){

                        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,userOTP);
                        verifyAuth(credential);

                    }else{
                        codeEnter.setError("valid OTP is required");
                    }

                }
            }
        });


    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),SetMpin.class));
                    finish();
                }else{
                    Toast.makeText(Register.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestOTP(String phoneNum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId=s;
                token=forceResendingToken;
                nextBtn.setText("Verify");
                verificationInProgress=true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Register.this, "cannot create account"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}