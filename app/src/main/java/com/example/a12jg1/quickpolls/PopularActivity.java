package com.example.a12jg1.quickpolls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.BufferUnderflowException;

/**
 * Created by 12jg1 on 5/5/2017.
 */

public class PopularActivity extends AppCompatActivity {

    public final String POPULAR_ACTIVITY = "popular";

    private DatabaseReference mPollCreationReference;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_popular);

        mPollCreationReference = FirebaseDatabase.getInstance().getReference("poll");

        mRecyclerView = (RecyclerView) findViewById(R.id.popular_recycler_view);

        PollAdapter mAdapter = new PollAdapter(mPollCreationReference, POPULAR_ACTIVITY);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PopularActivity.this, MainActivity.class);
        PopularActivity.this.startActivity(intent);
        finish();
    }
}
