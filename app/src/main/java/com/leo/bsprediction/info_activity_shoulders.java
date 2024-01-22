package com.leo.bsprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class info_activity_shoulders extends AppCompatActivity {

    RecyclerView recyclerView_Shoulders;
    ShouldersAdapter shouldersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_shoulders);

        recyclerView_Shoulders = findViewById(R.id.recyclerView_Shoulders);


        recyclerView_Shoulders.setLayoutManager(new LinearLayoutManager(info_activity_shoulders.this));
        shouldersAdapter = new ShouldersAdapter(info_activity_shoulders.this,UserProfileActivity.connectedThread.shouldersList);
        recyclerView_Shoulders.setAdapter(shouldersAdapter);
        recyclerView_Shoulders.setItemAnimator(new DefaultItemAnimator());

    }
}