package com.example.wordhub.adapter;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.bean.Record;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Record> myList;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView word, phonetic, meaning;
        ImageView pronounce;
        TextToSpeech textToSpeech;

        MyViewHolder(View view) {
            super(view);
            word = view.findViewById(R.id.record_word);
            phonetic = view.findViewById(R.id.record_phonetic);
            meaning = view.findViewById(R.id.record_meaning);
            pronounce = view.findViewById(R.id.record_pronounce);

            textToSpeech = new TextToSpeech(MyApplication.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            });
        }
    }

    public RecordAdapter(List<Record> myList) {
        this.myList = myList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_record_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            if (myList != null && myList.size() > 0) {
                myViewHolder.word.setText(myList.get(position).getWordName());
                myViewHolder.phonetic.setText(myList.get(position).getWordPhonetic());
                myViewHolder.meaning.setText(myList.get(position).getWordMeaning());

                myViewHolder.pronounce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myViewHolder.textToSpeech.speak(myList.get(position).getWordName(),
                                TextToSpeech.QUEUE_ADD, null, null);
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
