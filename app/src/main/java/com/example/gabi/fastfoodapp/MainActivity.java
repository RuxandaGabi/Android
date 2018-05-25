package com.example.gabi.fastfoodapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText email,pass,pass2,phone;
    private String access;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPass);
        pass2 =(EditText) findViewById(R.id.editPass2);
        phone = (EditText) findViewById(R.id.editPhone);
        access = "0";
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    }

    public void signupButtonClicked(View view){
        final String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();
        String pass2_text = pass2.getText().toString().trim();
        final String phone_text = phone.getText().toString().trim();

        if(!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text) && !TextUtils.isEmpty(pass2_text)){
            if( pass_text.equals(pass2_text)) {
                mAuth.createUserWithEmailAndPassword(email_text, pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user = mDatabase.child(user_id);
                            current_user.child("Name").setValue(email_text);
                            current_user.child("Access").setValue(access);
                            current_user.child("Phone").setValue(phone_text);

                            Intent login = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(login);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Email already in use!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(MainActivity.this,"Passwords do not match!",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(MainActivity.this,"Fill in all the blanks!",Toast.LENGTH_LONG).show();
        }
    }

    public void signinButtonClicked(View view){
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
