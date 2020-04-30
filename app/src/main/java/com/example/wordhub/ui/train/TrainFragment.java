package com.example.wordhub.ui.train;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wordhub.R;
import com.example.wordhub.adapter.TrainAdapter;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;

public class TrainFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_train, container, false);
        MyTask myTask = new MyTask(root);
        myTask.execute();
        return root;
    }

    @SuppressLint("StaticFieldLeak")
    private static class MyTask extends AsyncTask<Void, Void, Void> {

        private View view;

        MyTask(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ConnectUtil.getReview();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView = view.findViewById(R.id.train_recycler);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            TrainAdapter adapter = new TrainAdapter(MyApplication.reviewList);
            recyclerView.setAdapter(adapter);
        }
    }
}
