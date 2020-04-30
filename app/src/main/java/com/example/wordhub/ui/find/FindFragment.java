package com.example.wordhub.ui.find;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wordhub.R;
import com.example.wordhub.adapter.FindAdapter;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_find, container, false);
        MyTask myTask = new MyTask(root);
        myTask.execute();
        return root;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private View view;
        private List<Integer> list = new ArrayList<>();
        ProgressBar progressBar;

        MyTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = view.findViewById(R.id.find_progress);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ConnectUtil.getFind();
            list.add(R.drawable.record_img);
            list.add(R.drawable.line);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            RecyclerView recyclerView = view.findViewById(R.id.find_recycler);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            FindAdapter adapter = new FindAdapter(MyApplication.findList, list);
            recyclerView.setAdapter(adapter);
        }
    }
}
