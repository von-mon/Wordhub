package com.example.wordhub.ui.word;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wordhub.R;
import com.example.wordhub.adapter.BookAdapter;
import com.example.wordhub.application.MyApplication;
import com.example.wordhub.utils.ConnectUtil;
import com.example.wordhub.utils.LogUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class BookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BookTask task = new BookTask();
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class BookTask extends AsyncTask<Void, String, Boolean> {
        SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);

        @Override
        protected Boolean doInBackground(Void... voids) {
            ConnectUtil.getBook(String.valueOf(data.getInt("userId", 0)));
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                setContentView(R.layout.activity_book);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                //显示回退按钮
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                //监听回退按钮
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                RecyclerView recyclerView = findViewById(R.id.recyclerview_book);
                LinearLayoutManager linearLayout = new LinearLayoutManager(BookActivity.this);
                recyclerView.setLayoutManager(linearLayout);
                BookAdapter bookAdapter = new BookAdapter(MyApplication.bookList);
                recyclerView.setAdapter(bookAdapter);
            }
        }
    }

    /**
     * 添加菜单布局
     *
     * @param menu 菜单
     * @return Boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 监听菜单
     *
     * @param item 菜单条目
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.book_delete) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ConnectUtil.deleteBook();
                    MyApplication.bookList.clear();
                }
            }).start();
            RecyclerView recyclerView = findViewById(R.id.recyclerview_book);
            LinearLayoutManager linearLayout = new LinearLayoutManager(BookActivity.this);
            recyclerView.setLayoutManager(linearLayout);
            BookAdapter bookAdapter = new BookAdapter(MyApplication.bookList);
            recyclerView.setAdapter(bookAdapter);
            Toast.makeText(BookActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 同时显示文字和图片
     *
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
