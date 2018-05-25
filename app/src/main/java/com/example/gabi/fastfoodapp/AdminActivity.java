package com.example.gabi.fastfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void addFoodButtonClicked(View view){
        Intent addFoodIntent = new Intent(AdminActivity.this,AddFood.class);
        startActivity(addFoodIntent);
    }

    public void viewOrders(View view){
        startActivity(new Intent(AdminActivity.this,OpenOrders.class));
    }
}
