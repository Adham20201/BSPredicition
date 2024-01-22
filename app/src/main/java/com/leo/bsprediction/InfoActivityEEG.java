package com.leo.bsprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoActivityEEG extends AppCompatActivity {

    RecyclerView recyclerView_EEG;
    EEGListAdapter eegListAdapter;
    FloatingActionButton goEEGGraphs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_eeg);

        recyclerView_EEG = findViewById(R.id.recyclerView_EEG);
        goEEGGraphs = findViewById(R.id.goToEEGGraphs);

        recyclerView_EEG.setLayoutManager(new LinearLayoutManager(InfoActivityEEG.this));
        eegListAdapter = new EEGListAdapter(InfoActivityEEG.this,UserProfileActivity.connectedThread.eegList);
        recyclerView_EEG.setAdapter(eegListAdapter);
        recyclerView_EEG.setItemAnimator(new DefaultItemAnimator());

        goEEGGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivityEEG.this, EEGGraphActivity.class);
                startActivity(intent);
            }
        });
    }
}