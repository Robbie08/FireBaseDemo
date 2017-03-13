package com.example.robert.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.MessageFormat;

public class WallActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        //Initialize Variables
        TextView userEmail = (TextView) findViewById(R.id.tvDisplayUserEmail);

        //Initialize ProgressDialog object
        progressDialog = new ProgressDialog(this);

        //Initialize FireBase Object
        firebaseAuth = FirebaseAuth.getInstance();

        //Check if the user is not logged in
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LogInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //if the user is logged in
        assert user != null;
        userEmail.setText(MessageFormat.format("Welcome {0}", user.getEmail()));
    }

    /**
     * Will use or Firebase Authentication object to sign the user out , once
     * the user is signed they will be redirected to the LogInActivity.java class
     */
    private void logUserOut(){

        firebaseAuth.signOut();
        finish();
        progressDialog.dismiss();
        startActivity(new Intent(this,LogInActivity.class));
    }

    /**
     * onClick listener that will call our logUserOut method from WallActivity.java,
     * this will log out the user
     *
     * @param view pass in our activity_log_in.xml
     */
    public void logOut(View view){
        logUserOut();
    }
}
