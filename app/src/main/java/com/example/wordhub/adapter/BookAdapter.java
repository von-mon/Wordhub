package com.example.wordhub.adapter;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;

import com.example.wordhub.bean.WordBook;
import com.example.wordhub.utils.LogUtil;


import java.util.List;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author LindaBlack
 */
public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<WordBook> myList;
    //空布局标记
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE = 1;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView word, phonetic, meaning;
        ImageView pronounce;
        TextToSpeech textToSpeech;
        CardView book;

        MyViewHolder(View view) {
            super(view);
            word = view.findViewById(R.id.book_word);
            phonetic = view.findViewById(R.id.book_phonetic);
            meaning = view.findViewById(R.id.book_meaning);
            pronounce = view.findViewById(R.id.book_pronounce);
            book = view.findViewById(R.id.book_data);

            textToSpeech = new TextToSpeech(MyApplication.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            });
        }
    }

    //空布局
    static class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View view) {
            super(view);
        }
    }

    public BookAdapter(List<WordBook> list) {
        this.myList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //无数据时显示空布局
        if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_book_empty, parent, false);
            return new EmptyViewHolder(view);
        } else {
            //获取activity_book_item的布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_book_item, parent, false);
            return new MyViewHolder(view);
        }

    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //数据不为空
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            //防止出现java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
//            if (myList != null && myList.size() > 0) {
            final String word = myList.get(position).getWordName();
            String phonetic = myList.get(position).getWordPhonetic();
            String meaning = myList.get(position).getWordMeaning();

            myViewHolder.word.setText(word);
            myViewHolder.phonetic.setText(phonetic);
            myViewHolder.meaning.setText(meaning);
            myViewHolder.pronounce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViewHolder.textToSpeech.speak(word, TextToSpeech.QUEUE_ADD, null, null);
                }
            });
//            }
        }

    }

    @Override
    public int getItemCount() {
        //数据为空显示空布局，item设置为1
        if (myList.size() == 0) {
            return 1;
        }
        LogUtil.d("数据长度", String.valueOf(myList.size()));
        return myList.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    /**
     * getItemViewType返回的是有参数position所决定的的view的id
     *
     * @param position 位置
     * @return int
     */
    @Override
    public int getItemViewType(int position) {
        if (myList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE;
        }
    }
}
