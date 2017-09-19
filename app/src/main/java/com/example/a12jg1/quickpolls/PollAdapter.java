package com.example.a12jg1.quickpolls;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by 12jg1 on 5/1/2017.
 */

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollViewHolder> {

    public ArrayList<Poll> polls = new ArrayList<>();
    public ArrayList<String> pollKeys = new ArrayList<>();
    public final String PROFILE_ACTIVITY = "profile";
    public final String POPULAR_ACTIVITY = "popular";

    public FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
    public String uid = user.getUid();


    public DatabaseReference pollReference;

    public PollAdapter(final DatabaseReference pollRef, final String requestedActivity) {

            this.pollReference = pollRef;
            pollReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (requestedActivity.equals(PROFILE_ACTIVITY)){
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            Poll p = post.getValue(Poll.class);
                            if (p.getUid().equals(uid)){
                                polls.add(p);
                                pollKeys.add(post.getKey());
                                Log.d("Testing", p.getTitle());
                            }
                        }
                    }
                    else if(requestedActivity.equals(POPULAR_ACTIVITY)){
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            Poll p = post.getValue(Poll.class);
                            polls.add(p);
                            pollKeys.add(post.getKey());
                            Log.d("Testing", p.getTitle());
                        }

                        Collections.sort(polls, new Comparator<Poll>() {
                            @Override
                            public int compare(Poll poll, Poll t1) {
                                return t1.compareTo(poll);
                            }
                        });

                    }
                    else{
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            Poll p = post.getValue(Poll.class);
                            polls.add(p);
                            pollKeys.add(post.getKey());
                            Log.d("Testing", p.getTitle());
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("CANCELLED", "Did not get poll");
                }
            });
    }


    @Override
    public PollViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        final View pollListItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_poll_item, parent, false);
        return new PollViewHolder(pollListItem);
    }

    @Override
    public void onBindViewHolder(final PollViewHolder holder, final int position){

        final Poll poll = polls.get(position);

        holder.mTitleTextView.setText(poll.getTitle());
        holder.mOption1TextView.setText(poll.getOption1().getOptionTitle());
        holder.mOption2TextView.setText(poll.getOption2().getOptionTitle());
        holder.mCreator.setText(poll.getCreator());
        holder.mPopularity.setText(String.valueOf(poll.getPopularity()));

        if (poll.getOption1().getUniqueIDS().contains(uid)){
            holder.mOption1Percent.setText(poll.calculatePercentageOne() + "%");
            holder.mOption1TextView.setTextColor(ContextCompat.getColor(holder.view.getContext(),
                    R.color.colorPrimaryDark));
            holder.mOption2Percent.setText(poll.calculatePercentageTwo() + "%");
        }
        if (poll.getOption2().getUniqueIDS().contains(uid)){
            holder.mOption1Percent.setText(poll.calculatePercentageOne() + "%");
            holder.mOption2TextView.setTextColor(ContextCompat.getColor(holder.view.getContext(),
                    R.color.colorPrimaryDark));
            holder.mOption2Percent.setText(poll.calculatePercentageTwo() + "%");
        }
        if (poll.getPopUps().contains(uid)){
            holder.mUpArrow.setImageDrawable(ContextCompat.getDrawable(holder.view.getContext(),
                    R.drawable.ic_keyboard_arrow_up_post_click));
        }
        if (poll.getPopDowns().contains(uid)){
            holder.mDownArrow.setImageDrawable(ContextCompat.getDrawable(holder.view.getContext(),
                    R.drawable.ic_keyboard_arrow_down_post_click));
        }

        View.OnClickListener listener = new itemClicks(poll, holder, uid, position);

        holder.mOption1TextView.setOnClickListener(listener);
        holder.mOption2TextView.setOnClickListener(listener);
        holder.mUpArrow.setOnClickListener(listener);
        holder.mDownArrow.setOnClickListener(listener);
    }

    @Override
    public int getItemCount(){
        if (polls != null){
            return polls.size();
        }
        else return 0;
    }

    private class itemClicks implements View.OnClickListener{

        private Poll poll;
        private PollViewHolder holder;
        private String uid;
        private int position;

        public itemClicks(Poll poll, PollViewHolder holder, String uid, int position){

            this.poll = poll;
            this.holder = holder;
            this.uid = uid;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.option_one_TextView && !poll.hasVoted(uid)){
                holder.mOption1TextView.setTextColor(ContextCompat.getColor(view.getContext(),
                        R.color.colorPrimaryDark));

                DatabaseReference pollLevel = pollReference.child(pollKeys.get(position));
                DatabaseReference optionLevel = pollLevel.child("option1");
                optionLevel.setValue(new Option(poll.getOption1().getOptionTitle(),
                        poll.getOption1().getUniqueIDS(), uid));
                holder.mOption1Percent.setText(poll.calculatePercentageOne() + "%");
                holder.mOption2Percent.setText(poll.calculatePercentageTwo() + "%");

            }
            if (view.getId() == R.id.option_two_TextView && !poll.hasVoted(uid)){
                holder.mOption2TextView.setTextColor(ContextCompat.getColor(view.getContext(),
                        R.color.colorPrimaryDark));
                DatabaseReference pollLevel = pollReference.child(pollKeys.get(position));
                DatabaseReference optionLevel = pollLevel.child("option2");
                optionLevel.setValue(new Option(poll.getOption2().getOptionTitle(),
                        poll.getOption2().getUniqueIDS(), uid));

                holder.mOption1Percent.setText(poll.calculatePercentageOne() + "%");
                holder.mOption2Percent.setText(poll.calculatePercentageTwo() + "%");
            }
            if (view.getId() == R.id.imageButtonUp && !poll.hasVotedPopularity(uid)){
                holder.mUpArrow.setImageDrawable(ContextCompat.getDrawable(view.getContext(),
                        R.drawable.ic_keyboard_arrow_up_post_click));

                DatabaseReference pollLevel = pollReference.child(pollKeys.get(position));
                DatabaseReference popularityUp = pollLevel.child("popUps");

                ArrayList<String> popUpTemp = (ArrayList<String>) poll.getPopUps();
                popUpTemp.add(uid);

                popularityUp.setValue(popUpTemp);

                holder.mPopularity.setText(String.valueOf(poll.getPopularity()));
            }
            if (view.getId() == R.id.imageButtonDown && !poll.hasVotedPopularity(uid)){
                holder.mDownArrow.setImageDrawable(ContextCompat.getDrawable(view.getContext(),
                        R.drawable.ic_keyboard_arrow_down_post_click));

                DatabaseReference pollLevel = pollReference.child(pollKeys.get(position));
                DatabaseReference popularityDown = pollLevel.child("popDowns");

                ArrayList<String> popDownTemp = (ArrayList<String>) poll.getPopDowns();
                popDownTemp.add(uid);

                popularityDown.setValue(popDownTemp);

                holder.mPopularity.setText(String.valueOf(poll.getPopularity()));
            }
        }
    }

    public static class PollViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView mTitleTextView;
        public TextView mOption1TextView;
        public TextView mOption2TextView;
        public ImageView mUpArrow;
        public ImageView mDownArrow;
        public TextView mCreator;
        public TextView mOption1Percent;
        public TextView mOption2Percent;
        public TextView mPopularity;

        public PollViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            this.mTitleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            this.mOption1TextView = (TextView) itemView.findViewById(R.id.option_one_TextView);
            this.mOption2TextView = (TextView) itemView.findViewById(R.id.option_two_TextView);
            this.mUpArrow = (ImageView) itemView.findViewById(R.id.imageButtonUp);
            this.mDownArrow = (ImageView) itemView.findViewById(R.id.imageButtonDown);
            this.mCreator = (TextView) itemView.findViewById(R.id.poll_creator);
            this.mOption1Percent = (TextView) itemView.findViewById(R.id.option_one_percentage);
            this.mOption2Percent = (TextView) itemView.findViewById(R.id.option_two_percentage);
            this.mPopularity = (TextView) itemView.findViewById(R.id.popularity_counter);
        }
    }
}
