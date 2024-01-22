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

import java.util.List;

public class ShouldersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Object> shoulderList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView texLeftShoulder, textRightShoulder;
        public ViewHolder(View v) {
            super(v);
            texLeftShoulder = v.findViewById(R.id.LeftShoulderInput);
            textRightShoulder = v.findViewById(R.id.RightShouldersInput);
        }
    }

    public ShouldersAdapter(Context context, List<Object> shoulderList) {
        this.context = context;
        this.shoulderList = shoulderList;

    }


    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoulders_info_layout, parent, false);
        ShouldersAdapter.ViewHolder vh = new ShouldersAdapter.ViewHolder(v);
        return vh;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ShouldersAdapter.ViewHolder itemHolder = (ShouldersAdapter.ViewHolder) holder;
        final ShouldersListModel shouldersListModel = (ShouldersListModel) shoulderList.get(position);
        itemHolder.texLeftShoulder.setText(shouldersListModel.getLeftShoulders());
        itemHolder.textRightShoulder.setText(shouldersListModel.getRightShoulders());

    }

    @Override
    public int getItemCount() {
        int dataCount = shoulderList.size();
        return dataCount;
    }
}
