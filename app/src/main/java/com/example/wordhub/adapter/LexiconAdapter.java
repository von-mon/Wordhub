package com.example.wordhub.adapter;

import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.bean.Lexicon;
import com.example.wordhub.utils.ConnectUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LexiconAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Lexicon> myList;
    private int userId;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(MyApplication.getContext(), "更换为当前词库", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MyApplication.getContext(), "添加成功，并更换为当前词库", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.lexicon_name);
        }
    }

    public LexiconAdapter(List<Lexicon> myList, int userId) {
        this.myList = myList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lexicon_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            if (myList != null && myList.size() > 0) {
                myViewHolder.name.setText(myList.get(position).getName());

                myViewHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lexiconId = myList.get(position).getId();
                        String lexiconName = myList.get(position).getName();
                        MyApplication.lexiconId = lexiconId;
                        MyApplication.lexiconName = lexiconName;
                        final HashMap<String, Object> map = new HashMap<>();
                        map.put("lexiconId", lexiconId);
                        map.put("userId", userId);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (ConnectUtil.isAddUserLexicon(map)) {
                                    Message message = new Message();
                                    message.what = 1;
                                    handler.sendMessage(message);
                                } else {
                                    ConnectUtil.addUserLexicon(map);
                                    Message message = new Message();
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                            }
                        }).start();

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
