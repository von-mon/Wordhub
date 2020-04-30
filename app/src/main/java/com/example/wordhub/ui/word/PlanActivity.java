package com.example.wordhub.ui.word;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.wordhub.MainActivity;
import com.example.wordhub.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        List<String> list = new ArrayList<>();
        list.add("10");
        list.add("30");
        list.add("50");
        list.add("100");
        list.add("200");
        list.add("300");
        list.add("500");
        list.add("1000");
        final WheelView<String> wheelView = findViewById(R.id.wheelview);
        // 文本数据源
        wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        // common皮肤
        wheelView.setSkin(WheelView.Skin.Holo);
        // 数据集合
        wheelView.setWheelData(list);
        wheelView.setWheelClickable(true);
        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener<String>() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onItemClick(int position, String s) {
                SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
                edit.putInt("plan", Integer.parseInt(wheelView.getSelectionItem()));
                edit.apply();
                Intent intent = new Intent(PlanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
