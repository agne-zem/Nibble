package com.programele.nibble.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.programele.nibble.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextCPassword;
    private Button buttonSignup;
    private TextView textViewLogIn;
    private ProgressDialog progressDialog;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextCPassword = (EditText) findViewById(R.id.confpassword);
        buttonSignup = (Button) findViewById(R.id.sign_in);
        textViewLogIn = (TextView) findViewById(R.id.textViewLogIn);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewLogIn.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        String cpassword  = editTextCPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        //checking if pass is at least 6 letters
        if(password.length() < 6){
            Toast.makeText(this,"Password must be at least 6 letters long",Toast.LENGTH_LONG).show();
            return;
        }
        //checking if pass and conf pass are the same
        if(!password.equals(cpassword)){
            Toast.makeText(this,"Password and confirm password do not match",Toast.LENGTH_LONG).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(SignInActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));


                        }else{
                            //display some message here
                            Toast.makeText(SignInActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignup)
        {
            registerUser();

        }
        if (view == textViewLogIn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}