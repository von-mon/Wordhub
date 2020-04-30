package com.example.wordhub.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.bean.Find;
import com.example.wordhub.ui.find.RecordActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Find> myList;
    private List<Integer> imageList;

    public FindAdapter(List<Find> myList, List<Integer> imageList) {
        this.myList = myList;
        this.imageList = imageList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.find_name);
            img = view.findViewById(R.id.find_imge);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragement_find_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            if (myList != null && myList.size() > 0) {
                myViewHolder.name.setText(myList.get(position).getName());
                myViewHolder.img.setImageResource(imageList.get(position));

                myViewHolder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myList.get(position).getName().equals("学习记录")) {
                            Intent intent = new Intent(v.getContext(), RecordActivity.class);
                            v.getContext().startActivity(intent);
                        }
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
