package com.example.wordhub.ui.find;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.wordhub.R;
import com.example.wordhub.adapter.RecordAdapter;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;

public class RecordActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        progressBar = findViewById(R.id.record_progressbar);
        MyTask myTask = new MyTask();
        myTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
            int userId = data.getInt("userId", 0);
            ConnectUtil.getRecord(String.valueOf(userId));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.record_recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(RecordActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            RecordAdapter adapter = new RecordAdapter(MyApplication.recordList);
            recyclerView.setAdapter(adapter);
        }
    }
}
