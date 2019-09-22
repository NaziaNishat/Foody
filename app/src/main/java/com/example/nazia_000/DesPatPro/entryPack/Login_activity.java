package com.example.nazia_000.DesPatPro.entryPack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.homePack.Homepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_activity extends AppCompatActivity {


    private FirebaseAuth loginAuth;

    private ProgressDialog progressDialog;

    private Button login_btn,signup_btn;
    private EditText mail_login,pass_login;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        progressDialog = new ProgressDialog(this);

        loginAuth = FirebaseAuth.getInstance();

        login_btn = findViewById(R.id.login);
        signup_btn = findViewById(R.id.signup);

        mail_login = findViewById(R.id.login_email);
        pass_login = findViewById(R.id.login_password);

        Handler handler = new Handler();
        login_btn.setOnClickListener(handler);
        signup_btn.setOnClickListener(handler);

    }

    private void loginUser(){

        progressDialog.cancel();

        email = mail_login.getText().toString().trim();
        final String password = pass_login.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()) {

            progressDialog.setMessage("Login is in progress...");
            progressDialog.show();

            if (FirebaseAuth.getInstance().getCurrentUser() == null)
                loginAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //if (loginAuth.getCurrentUser().isEmailVerified()) {
                                    loginPage();
                                    progressDialog.cancel();
                                    //}
                                } else {
                                    progressDialog.cancel();
                                    Toast.makeText(Login_activity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            else loginPage();
        }else{
            Toast.makeText(getApplicationContext(),"Please fill up all field",Toast.LENGTH_SHORT).show();
        }

    }

    private void loginPage(){
        Intent intent = new Intent(Login_activity.this, Homepage.class);
        intent.putExtra("email",email);
        startActivity(intent);
        finish();

        mail_login.setText("");
        pass_login.setText("");
    }

    private void signupPage(){

        Intent intent = new Intent(Login_activity.this, signup_activity.class);
        startActivity(intent);
        finish();
    }

    class Handler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.login){
                loginUser();
            }

            if(v.getId() == R.id.signup){
                signupPage();
            }
        }
    }
}
