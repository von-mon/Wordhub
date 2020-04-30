package com.example.wordhub.ui.center;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.wordhub.R;
import com.example.wordhub.adapter.LexiconAdapter;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class LexiconActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon);
        progressBar = findViewById(R.id.lexicon_progressbar);
        MyTask myTask = new MyTask();
        myTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            ConnectUtil.getLexicon();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
            int userId = data.getInt("userId", 0);
            progressBar.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.lexicon_recycler);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            LexiconAdapter adapter = new LexiconAdapter(MyApplication.lexiconList, userId);
            recyclerView.setAdapter(adapter);
        }
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
        edit.putInt("lexiconId", MyApplication.lexiconId);
        edit.putString("lexiconName", MyApplication.lexiconName);
        edit.apply();
        this.finish();
    }
}
