package com.example.smartagriculturalsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button CallSignUp, CallLogin_btn;
    TextInputLayout telNumLogin, passwordLogin;
    ProgressBar progress_Barlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telNumLogin = findViewById(R.id.telNum1);
        passwordLogin = findViewById(R.id.password1);
        CallLogin_btn = findViewById(R.id.loginbtn);
        CallSignUp = findViewById(R.id.signupbtn);
        progress_Barlogin = findViewById(R.id.progressBarlogin);

        progress_Barlogin.setVisibility(View.INVISIBLE);


        CallSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateSigntelNum() {
        String val = telNumLogin.getEditText().getText().toString();

        if (val.isEmpty()) {
            telNumLogin.setError("Field Can't be Empty");
            return false;
        } else {
            telNumLogin.setError(null);
            telNumLogin.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateSignPassword() {
        String val = passwordLogin.getEditText().getText().toString();


        if (val.isEmpty()) {
            passwordLogin.setError("Field Can't be Empty");
            return false;
        } else {
            passwordLogin.setError(null);
            passwordLogin.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        //validate login info
        if (!validateSigntelNum() | !validateSignPassword()) {
            progress_Barlogin.setVisibility(View.INVISIBLE);
            return;
        } else {

            isUser();
        }
    }

    private void isUser() {
        final String userEnteredTELNum = telNumLogin.getEditText().getText().toString().trim();
        final String userEnteredPassword = passwordLogin.getEditText().getText().toString().trim();

        progress_Barlogin.setVisibility(View.VISIBLE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");

        Query checkUser = reference.orderByChild("telNum").equalTo(userEnteredTELNum);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    telNumLogin.setError(null);
                    telNumLogin.setEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredTELNum).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        telNumLogin.setError(null);
                        telNumLogin.setEnabled(false);

                        String fnameFromDB = snapshot.child(userEnteredTELNum).child("fname").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredTELNum).child("username").getValue(String.class);
                        String pfNumberFromDB = snapshot.child(userEnteredTELNum).child("email").getValue(String.class);
                        String telNumberFromDB = snapshot.child(userEnteredTELNum).child("telNum").getValue(String.class);
                        String cpasswordFromDB = snapshot.child(userEnteredTELNum).child("cpassword").getValue(String.class);



                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("fname", fnameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("pfnumber", pfNumberFromDB);
                        intent.putExtra("telNum", telNumberFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("cpassword", cpasswordFromDB);




                        Intent intent2 = new Intent(login.this,MainMenu.class);
                        startActivity(intent2);
                        finish();
                    } else {
                        passwordLogin.setError("Wrong Password");
                        passwordLogin.requestFocus();
                        progress_Barlogin.setVisibility(View.INVISIBLE);
                    }
                } else {
                    telNumLogin.setError("No Such User Exists");
                    telNumLogin.requestFocus();
                    progress_Barlogin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}