package com.example.a12jg1.quickpolls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by 12jg1 on 5/5/2017.
 */

public class ProfilePageActivity extends AppCompatActivity {

    public final String PROFILE_ACTIVITY = "profile";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private RecyclerView mRecyclerView;
    private DatabaseReference mPollCreationReference;

    private String username;
    private String mPhotoUrl;

    /**
     * sets the layout to the profile page which displays the polls created by the user
     * @param savedInstanceState onCreate stuff
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        username = mUser.getDisplayName();

        if (mUser.getPhotoUrl() != null){
            mPhotoUrl = mUser.getPhotoUrl().toString();

            TextView name = (TextView) this.findViewById(R.id.profile_name);
            ImageView profilePic = (ImageView) this.findViewById(R.id.profile_picture);

            name.setText(username);
            Picasso.with(this).load(mPhotoUrl).into(profilePic);
        }

        mPollCreationReference = FirebaseDatabase.getInstance().getReference("poll");

        mRecyclerView = (RecyclerView) findViewById(R.id.personal_recycler_view);

        PollAdapter mAdapter = new PollAdapter(mPollCreationReference, PROFILE_ACTIVITY);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfilePageActivity.this, MainActivity.class);
        ProfilePageActivity.this.startActivity(intent);
        finish();
    }
}
