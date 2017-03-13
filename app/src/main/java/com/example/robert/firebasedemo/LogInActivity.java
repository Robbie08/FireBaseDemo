package com.example.robert.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private EditText emailLogIn, passwordLogIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initialize Progress Dialog
        progressDialog = new ProgressDialog(this);

        //Initialize Firebase object
        firebaseAuth = FirebaseAuth.getInstance();

        //check if the user exists/signed in
        if(firebaseAuth.getCurrentUser() != null){
            //CloseActivity
            finish();
            //Open new activity, WallActivity
            startActivity(new Intent(LogInActivity.this,WallActivity.class));
        }
        //Initialize variables
        emailLogIn = (EditText) findViewById(R.id.etEmailLogIn);
        passwordLogIn = (EditText) findViewById(R.id.etPasswordLogIn);


    }

    /**
     * Method will log in the user utilizing Firebase Json object Authentication, To
     * be implemented in the onClick event listener
     */
    private void logInUser(){

        //Get and convert user input and store it as String
        String stringEmailLogIn = emailLogIn.getText().toString().trim();
        String stringPasswordLogIn = passwordLogIn.getText().toString().trim();

        //Check if our email and password are populated to avoid null pointer
        if(TextUtils.isEmpty(stringEmailLogIn)){
            //email is empty
            Toast.makeText(this,"Please Enter a Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(stringPasswordLogIn)){
            //password is empty
            Toast.makeText(this,"Please Enter a Password",Toast.LENGTH_LONG).show();
            return;
        }

        //Prompt the user with a Progress Dialog
        progressDialog.setMessage("Signing In...");
        progressDialog.show();


        /**
         * Using Firebase Json object will Authenticate the user through FireBase
         *
         * Requirements: 1) Take in Password & Email
         *               2) Check if Password and Email match up
         *               3) If the user is successfully authenticated send them to the wall
         *                  fragment, else display a Toast letting them know, the password
         *                  or Email is incorrect
         *
         *  @param Email the input from the email edit text
         *  @param Password the input from the password edit text
         */
        firebaseAuth.signInWithEmailAndPassword(stringEmailLogIn, stringPasswordLogIn)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                                //CloseActivity
                                finish();
                                //Open new activity, WallActivity
                                startActivity(new Intent(getApplicationContext(), WallActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"Email or Password is not correct," +
                                    " Please try again ",
                                    Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    /**
     * onClick listener that will call our logInUser method that will then log the user
     * in or prompt them to try again
     * @param view will pass in our View from the activity_log_in.xml
     */
    public void logIn(View view){
        logInUser();
    }

    /**
     * onClick listener that will send the user to the registration page
     * @param view will pass in our View from the activity_main.xml
     */
    public void sendRegister(View view){
        finish();
        Log.d("SHIT","This happens");
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
