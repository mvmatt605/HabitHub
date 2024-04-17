package com.example.expensetrackingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FitnessAdapter extends RecyclerView.Adapter<FitnessAdapter.ViewHolder> {
    private List<String> dataList;

    public FitnessAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

//    @NonNull
//    @Override
//    public FitnessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitness, parent, false);
//        return new FitnessAdapter.ViewHolder(view);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitness, parent, false);
        return new FitnessAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = dataList.get(position);
        holder.bind(name);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
        }

        public void bind(String name) {
            textViewName.setText(name);
        }
    }

}
