package com.leo.bsprediction;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EEGListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context context;
    private ArrayList<String> eegList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        ConstraintLayout constraintlayout;

        public ViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.EEGInput);
            constraintlayout = v.findViewById(R.id.constraintLayoutEEGInfo);
        }
    }

    public EEGListAdapter(Context context, ArrayList<String> eegList) {
        this.context = context;
        this.eegList = eegList;

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eeg_info_layout, parent, false);
        EEGListAdapter.ViewHolder vh = new EEGListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EEGListAdapter.ViewHolder itemHolder = (EEGListAdapter.ViewHolder) holder;
        itemHolder.textName.setText(eegList.get(position));

    }

    @Override
    public int getItemCount() {
        int dataCount = eegList.size();
        return dataCount;
    }
}
