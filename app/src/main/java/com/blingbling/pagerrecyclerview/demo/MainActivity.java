package com.blingbling.pagerrecyclerview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerLinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blingbling.widget.pagerrecyclerview.PagerRecyclerView;
import com.blingbling.widget.pagerrecyclerview.transformer.ScaleTransformer;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;

    private PagerRecyclerView recyclerView1;
    private PagerRecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView2 = findViewById(R.id.recycler2);

        PagerLinearLayoutManager layoutManager1 = new PagerLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager1.setItemTransformer(new ScaleTransformer());
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setAdapter(new MyAdapter());
        recyclerView1.setOnItemSelectedListener(new PagerRecyclerView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                tv1.setText("RecyclerView1 position " + position);
                Log.e("TAG", "1-->" + position);
            }
        });

        PagerLinearLayoutManager layoutManager2 = new PagerLinearLayoutManager(this);
        layoutManager2.setItemTransformer(new ScaleTransformer());
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(new MyAdapter());
        recyclerView2.setOnItemSelectedListener(new PagerRecyclerView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                tv2.setText("RecyclerView2 position " + position);
                Log.e("TAG", "2-->" + position);
            }
        }, true);
    }

}
