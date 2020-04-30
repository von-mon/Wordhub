package com.example.wordhub.ui.center;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wordhub.R;
import com.example.wordhub.ui.word.BookActivity;
import com.example.wordhub.utils.LogUtil;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CenterFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_center, container, false);
        setRedirect(root);
        return root;
    }

    //跳转界面
    @SuppressLint("SetTextI18n")
    private void setRedirect(View view) {
        TextView login = view.findViewById(R.id.center_login);
        TextView book = view.findViewById(R.id.word_book);
        TextView lexicon = view.findViewById(R.id.lexicon);
        TextView register = view.findViewById(R.id.center_register);
        SharedPreferences data = Objects.requireNonNull(getActivity()).getSharedPreferences("data", Context.MODE_PRIVATE);
        String nick_name = data.getString("nickname", "登录");
        int user_id = data.getInt("userId", 0);
        //用户名
        login.setText(nick_name);
        //ID
        register.setText("ID" + user_id);
        LogUtil.d("信息", data.getString("nickname", null) + " " + nick_name);
        //单词本
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookActivity.class);
                startActivity(intent);
            }
        });
        //词库
        lexicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LexiconActivity.class);
                startActivity(intent);
            }
        });
    }
}
