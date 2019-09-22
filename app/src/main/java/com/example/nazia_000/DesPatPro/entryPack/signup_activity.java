package com.example.nazia_000.DesPatPro.entryPack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup_activity extends AppCompatActivity{

    private static int PReqCode = 1;
    private static int REQUESCODE = 1;
    private Uri pickedImgUri;

    private FirebaseAuth signupAuth;

    private ProgressDialog progressDialog;
    private Button submit_btn;
    private EditText mail_signup,pass_signup,con_pass;

    private String email;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        signupAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        mail_signup = findViewById(R.id.signup_email);
        pass_signup = findViewById(R.id.signup_password);
        con_pass = findViewById(R.id.conPassField);
        submit_btn = findViewById(R.id.submit);

        Handler handler = new Handler();
        submit_btn.setOnClickListener(handler);

    }

    private void signupUser(){

        email = mail_signup.getText().toString().trim();
        String password = pass_signup.getText().toString().trim();
        String con_password = con_pass.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(con_password) ){
            Toast.makeText(this,"Please fill up all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.matches(con_password)){
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(this,"Please enter at least 6-digits characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user....");
        progressDialog.show();


        signupAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.cancel();

                        Toast.makeText(signup_activity.this,
                                "Registered successfully.Please check your email for verification. " ,
                                Toast.LENGTH_LONG).show();

                        nxtState();
                    }
                    else{
                        progressDialog.cancel();
                        Toast.makeText(signup_activity.this, " " + task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void nxtState(){
        intent = new Intent(signup_activity.this , ProfileActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
        }



    class Handler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.submit){
               signupUser();
            }

        }
    }
}
