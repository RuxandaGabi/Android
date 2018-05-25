package com.example.gabi.fastfoodapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail, userPass;
    private String access;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String access0 ="0";
    private String access1 ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.userEmail);
        userPass = (EditText)findViewById(R.id.userPass);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    }

    public void signinButtonClicked(View view){
        String email = userEmail.getText().toString().trim();
        String pass = userPass.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkAccess();
                        checkUserExists();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Username or Password invalid!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(LoginActivity.this,"Fill in all the blanks!",Toast.LENGTH_LONG).show();
        }

    }

    public void checkAccess() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                access = (String) dataSnapshot.child("Access").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkUserExists() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)) {
                    if (access.equals(access0)) {
                        Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(menuIntent);
                    }
                    else if(access.equals(access1)){
                        Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(adminIntent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Oops something went wrong!",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signupButtonClicked(View view) {
        Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginIntent);
    }
}
