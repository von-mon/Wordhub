package com.example.wordhub.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.bean.Review;
import com.example.wordhub.ui.train.ReviewActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Review> myList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.train_name);
        }
    }

    public TrainAdapter(List<Review> list) {
        this.myList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_train_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            if (myList != null && myList.size() > 0) {
                myViewHolder.name.setText(myList.get(position).getMode());

                myViewHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ReviewActivity.class);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (myList != null) {
            return myList.size();
        }
        return 0;
    }
}
