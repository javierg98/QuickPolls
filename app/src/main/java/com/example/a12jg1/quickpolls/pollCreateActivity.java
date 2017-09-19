package com.example.a12jg1.quickpolls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 12jg1 on 4/14/2017.
 */

public class pollCreateActivity extends AppCompatActivity{

    private FirebaseDatabase mDatabase;
    private DatabaseReference mpollCreationReference;

    private FirebaseAuth mAuth;
    private String creator;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_creation);

        mDatabase = FirebaseDatabase.getInstance();
        mpollCreationReference = mDatabase.getReference("poll");

        Button pollCreateButton = (Button) findViewById(R.id.create_poll_button);

        //Sets onClickListener for when to create the new Poll and push it into the database
        pollCreateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText mTitle = (EditText) findViewById(R.id.new_poll_title);
                EditText mOption1 = (EditText) findViewById(R.id.poll_option_1);
                EditText mOption2 = (EditText) findViewById(R.id.poll_option_2 );
                CheckBox isAnonymous = (CheckBox) findViewById(R.id.checkBox);

                String title = mTitle.getText().toString();
                String optionLabel1 = mOption1.getText().toString();
                String optionLabel2 = mOption2.getText().toString();

                if (!isAnonymous.isChecked()){
                    mAuth = FirebaseAuth.getInstance();
                    creator = mAuth.getCurrentUser().getDisplayName();
                    uid = mAuth.getCurrentUser().getUid();
                }
                else {creator = "Anonymous";}

                Option option1 = new Option(optionLabel1);
                Option option2 = new Option(optionLabel2);

                Poll mPoll = new Poll(title,creator, uid, option1,option2);

                mpollCreationReference.push().setValue(mPoll);

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
                finish();
            }


        });
    }

    @Override
    public void onBackPressed() {
    Intent intent = new Intent(pollCreateActivity.this, MainActivity.class);
        pollCreateActivity.this.startActivity(intent);
        finish();
    }
}