package com.example.smartagriculturalsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextInputLayout regNameSign, regUsernameSign, regemailSign, regtelNumSign, regPasswordSign, regCpasswordSign;
    Button signupbtnSign, callLoginbtnSign;
    private static int SPLASH_SCREEN = 1000;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        regNameSign = findViewById(R.id.fNameSign);
        regUsernameSign = findViewById(R.id.uNameSign);
        regemailSign = findViewById(R.id.emailSign);
        regtelNumSign = findViewById(R.id.telNumSign);
        regPasswordSign = findViewById(R.id.pwdSign);
        regCpasswordSign = findViewById(R.id.cpwdSign);
        callLoginbtnSign = findViewById(R.id.loginbtn);
        signupbtnSign = findViewById(R.id.signup);

        callLoginbtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateName() {
        String val = regNameSign.getEditText().getText().toString();

        if (val.isEmpty()) {
            regNameSign.setError("Field Can't be Empty");
            return false;
        } else {
            regNameSign.setError(null);
            return true;
        }
    }

    private Boolean validateUserName() {
        String val = regUsernameSign.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";

        if (val.isEmpty()) {
            regUsernameSign.setError("Field Can't be Empty");
            return false;
        } else if (val.length() >= 15) {
            regUsernameSign.setError("Username too Long");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            regUsernameSign.setError("Spaces are not Allowed");
            return false;
        } else {
            regUsernameSign.setError(null);
            regUsernameSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateemail() {
        String val = regemailSign.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";
        String valemail =   "[a-zA-Z0-9._-]{3,}@[a-zA-Z0-9.-]{3,}\\.[a-zA-Z]{2,4}";

        if (val.isEmpty()) {
            regemailSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(valemail)) {
            regemailSign.setError("Wrong E-Mail");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            regemailSign.setError("Wrong E-Mail");
            return false;
        } else {
            regemailSign.setError(null);
            regemailSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatetelNum() {
        String val = regtelNumSign.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)";
        String valNumber = "[0-9]+";

        if (val.isEmpty()) {
            regtelNumSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(valNumber)) {
            regtelNumSign.setError("Wrong Mobile Number");
            return false;
        } else if (val.length() != 10) {
            regtelNumSign.setError("Wrong Mobile Number");
            return false;
        } else if (val.matches(noWhiteSpace)) {
            regtelNumSign.setError("Can't Enter Spaces");
            return false;
        } else {
            regtelNumSign.setError(null);
            regtelNumSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPasswordSign.getEditText().getText().toString();
        String pass = "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                //"(?=.*[@#$%^&-+=()])" +
                "(?=\\S+$)" +
                ".{8,}" +
                "$";


        if (val.isEmpty()) {
            regPasswordSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(pass)) {
            regPasswordSign.setError("Must be include a-z,A-Z,0-9");
            return false;
        } else {
            regPasswordSign.setError(null);
            regPasswordSign.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatecPassword() {
        String val = regCpasswordSign.getEditText().getText().toString();
        String checkpass = regPasswordSign.getEditText().getText().toString();

        if (val.isEmpty()) {
            regCpasswordSign.setError("Field Can't be Empty");
            return false;
        } else if (!val.matches(checkpass)) {
            regCpasswordSign.setError("Confirm Password Didn't Match");
            return false;
        } else {
            regCpasswordSign.setError(null);
            regCpasswordSign.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {

        if (!validateName() | !validateUserName() | !validateemail() | !validatetelNum() | !validatePassword() | !validatecPassword()) {
            return;
        } else {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Admin");
            //Get All Values
            String fname = regNameSign.getEditText().getText().toString();
            String username = regUsernameSign.getEditText().getText().toString();
            String email = regemailSign.getEditText().getText().toString();
            String telNum = regtelNumSign.getEditText().getText().toString();
            String password = regPasswordSign.getEditText().getText().toString();
            String cpassword = regCpasswordSign.getEditText().getText().toString();

            userHelper helperClass = new userHelper(fname, username, email, telNum, password, cpassword);
            reference.child(telNum).setValue(helperClass);

            Toast.makeText(this, "Your Account has been Created Successfully!", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),login.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_SCREEN);

            /*Intent intent = new Intent(getApplicationContext(),otpScreen.class);
            intent.putExtra("telNum",telNum);
            startActivity(intent);*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,login.class);
        startActivity(intent);

    }
}