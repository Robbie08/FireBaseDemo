package com.example.robert.firebasedemo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonToken;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    //Declare local variables
    private EditText email, passWord;
    private TextView alreadyRegistered;
    private Button registerUser;
    private String stringEmail="", stringPassword="";
    private ProgressDialog progressDialog;
    private FirebaseAuth fireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ProgressDialog object
        progressDialog = new ProgressDialog(this);

        //FireBase object
        fireBaseAuth = FirebaseAuth.getInstance();

        //Declare variables
        email = (EditText) findViewById(R.id.etEmail);
        passWord = (EditText) findViewById(R.id.etPassword);
        registerUser = (Button) findViewById(R.id.bRegisterUser);
        alreadyRegistered = (TextView) findViewById(R.id.tvSendLogIn);




    }

    /**
     * This method will check that the edit text is not empty. Will also send information
     * to FireBase and apply a progress bar since it handles web connection so it might
     * take time.
     */
    private void registerUser(){
        stringEmail = email.getText().toString().trim();
        stringPassword = passWord.getText().toString().trim();

        if(TextUtils.isEmpty(stringEmail)){
            //email is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(stringPassword)){
            //password is empty
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return;
        }

        //We have to let the user know that they are being registed
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        //Register user to server
        fireBaseAuth.createUserWithEmailAndPassword(stringEmail,stringPassword)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){
                                            //User is successfully registered
                                            //start profile activity here
                                            //also display toast
                                            Toast.makeText(MainActivity.this,"Registration was successful",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(MainActivity.this,"Registration was unsuccessful, Please try again",Toast.LENGTH_LONG).show();
                                        }
                    }
                });
    }

    /**
     * Once TextView sendLogIn is clicked, the user will be redirected to the sign in page
     * @param view
     */
    public void sendLogIn(View view){


    }

    /**
     * Once the Button is clicked, the information will be sent to FireBase
     * @param view
     */
    public void register(View view){

        registerUser();

    }


}