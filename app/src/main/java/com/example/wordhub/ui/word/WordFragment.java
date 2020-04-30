package com.example.wordhub.ui.word;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wordhub.R;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;

import java.util.HashMap;
import java.util.Objects;

public class WordFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_word, container, false);
        MyTask myTask = new MyTask(root);
        myTask.execute();
        return root;
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Void, Void> {
        private View view;
        private ProgressBar progressBar;

        MyTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = view.findViewById(R.id.load_progress);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences data = Objects.requireNonNull(getActivity()).getSharedPreferences("data", Context.MODE_PRIVATE);
            int userId = data.getInt("userId", 0);
            int lexiconId = data.getInt("lexiconId", 0);
            //初次加载
            if (!MyApplication.frag) {
                //判断是否有记录
                if (ConnectUtil.isPlan(String.valueOf(userId))) {
                    ConnectUtil.getPlan(String.valueOf(userId));
                    SharedPreferences.Editor edit = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                    edit.putInt("userId", MyApplication.plan.getUserId());
                    edit.putInt("lexiconId", MyApplication.plan.getLexiconId());
                    edit.putInt("plan", MyApplication.plan.getPlan());
                    edit.putInt("days", MyApplication.plan.getDays());
                    edit.putString("lexiconName", MyApplication.plan.getLexiconName());
                    edit.apply();
                }
            } else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", data.getInt("userId", 0));
                map.put("lexiconId", data.getInt("lexiconId", 0));
                map.put("plan", data.getInt("plan", 0));
                map.put("days", data.getInt("days", 0));
                map.put("lexiconName", data.getString("lexiconName", null));
                if (ConnectUtil.isPlan(String.valueOf(userId))) {
                    //如果已添加就更新
                    ConnectUtil.updatePlan(map);
                } else {
                    //未添加，就添加
                    ConnectUtil.addPlan(map);
                }
            }

            if(lexiconId != 0) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("userId",userId);
                map.put("lexiconId",lexiconId);
                //获取当前词库的学习记录
                ConnectUtil.getRecordLexicon(map);
                //获取单词信息
                ConnectUtil.getWord(String.valueOf(lexiconId));
                //获取需要复习的单词列表
                //ConnectUtil.getRecordMode(String.valueOf(userId));
            }
            MyApplication.frag = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            setRedirect(view);
        }
    }

    @SuppressLint({"CommitPrefEdits", "SetTextI18n"})
    private void setRedirect(View view) {
        TextView plan, day, progress, lexicon;
        Button plan_set;
        ProgressBar progressBar;
        SharedPreferences data = Objects.requireNonNull(getActivity()).getSharedPreferences("data", Context.MODE_PRIVATE);
        plan = view.findViewById(R.id.show_plan);
        plan_set = view.findViewById(R.id.plan_set);
        day = view.findViewById(R.id.show_days);
        progress = view.findViewById(R.id.progress);
        progressBar = view.findViewById(R.id.show_progress);
        lexicon = view.findViewById(R.id.lexicon_name);
        Button recite = view.findViewById(R.id.button_recite);
        ImageView search = view.findViewById(R.id.imageView_search);
        //判断是否学习完
        if(MyApplication.recordLexiconList.size() == MyApplication.wordList.size()){
            Toast.makeText(getActivity(),"该词库已经学习完",Toast.LENGTH_SHORT).show();
            //设置背单词按钮不可以点击
            recite.setClickable(false);
        }
        int plan1 = data.getInt("plan", 0);
        String lexiconName = data.getString("lexiconName", null);
        if (plan1 != 0) {
            plan.setText(String.valueOf(plan1));
            //计算剩余天数，（单词总数-学习记录数）/ 计划数
            int temp = (MyApplication.wordList.size() - MyApplication.recordList.size()) / plan1;
            day.setText(String.valueOf(temp));
            SharedPreferences.Editor edit = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
            edit.putInt("days", temp);
            edit.apply();
        }
        if (lexiconName != null) {
            lexicon.setText(lexiconName);
        } else {
            lexicon.setText("未选择词库");
            //设置背单词按钮不可以点击
            recite.setClickable(false);
        }
        //设置进度
        progress.setText(MyApplication.recordList.size() + "/" + MyApplication.wordList.size());
        progressBar.setProgress(MyApplication.recordList.size());
        progressBar.setMax(MyApplication.wordList.size());

        plan_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlanActivity.class);
                startActivity(intent);
            }
        });

        //背单词
        recite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoadActivity.class);
                startActivity(intent);
            }
        });

        //查词
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.wordhub.ui.word.SearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
