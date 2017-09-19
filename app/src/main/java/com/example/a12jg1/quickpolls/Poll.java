package com.example.a12jg1.quickpolls;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12jg1 on 4/18/2017.
 */

public class Poll {

    private String title;
    private Option option1;
    private Option option2;
    private String creator;
    private String uid;
    private List<String> popUps;
    private List<String> popDowns;

    public Poll(){
        this.title = "title";
        Option o1 = new Option("option1");
        Option o2 = new Option("option2");
        this.option1 = o1;
        this.option2 = o2;
        this.creator = "Anonymous";
        this.uid = "Anonymous";
        popUps = new ArrayList<>();
        popDowns = new ArrayList<>();
    }

    public Poll(String title,String creator, String uid, Option option1, Option option2){
        this.title = title;
        this.creator = creator;
        this.uid = uid;
        this.option1 = option1;
        this.option2 = option2;
        this.popUps = new ArrayList<>();
        this.popDowns = new ArrayList<>();
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getCreator(){return creator;}

    public void setCreator(String creator){this.creator = creator;}

    public String getUid(){return uid;}

    public void setUid(String uid){this.uid = uid;}

    public int getPopularity() {return (popUps.size() - popDowns.size());}

    public Option getOption1() {return option1;}

    public void setOption1(Option option1) {this.option1 = option1;}

    public Option getOption2() {return option2;}

    public void setOption2(Option option2) {this.option2 = option2;}

    public List<String> getPopUps() {return popUps;}

    public void setPopUps(List<String> popUps) {this.popUps = popUps;}

    public List<String> getPopDowns() {return popDowns;}

    public void setPopDowns(List<String> popDowns) {this.popDowns = popDowns;}

    public int totalVotes(){
        return (option1.getVotes()+option2.getVotes());
    }

    public int calculatePercentageOne() {
        double percent = 0.0;
            if (option1.getUniqueIDS().size() + option2.getUniqueIDS().size() != 0) {
                percent = ((double) option1.getUniqueIDS().size()) / (option1.getUniqueIDS().size() + option2.getUniqueIDS().size());
            }
            int percentInt = (int) (percent*100);

        return percentInt;
    }

    public int calculatePercentageTwo(){
        double percent = 0.0;
        if (option1.getUniqueIDS().size() + option2.getUniqueIDS().size() != 0) {
            percent = ((double) option2.getUniqueIDS().size()) / ((double) (option1.getUniqueIDS().size() + option2.getUniqueIDS().size()));
            Log.d("PERCENT", String.valueOf(percent)+" "+ option1.getUniqueIDS().size() + " " + option2.getUniqueIDS().size());
        }
        int percentInt = (int) (percent*100);
        return percentInt;
    }

    public boolean hasVoted(String uid){
        boolean voted = false;
        if ( this.option1.getUniqueIDS().contains(uid) || this.option2.getUniqueIDS().contains(uid)){
            voted = true;
        }
        return voted;
    }

    public boolean hasVotedPopularity(String uid){
        boolean hasVoted = false;
        if (popUps.contains(uid) || popDowns.contains(uid)){
            hasVoted = true;
        }
        return hasVoted;
    }

    public int compareTo(Poll comparePoll) {
        int compareQuantity = ((Poll) comparePoll).getPopularity();
        //descending order
        return  this.getPopularity() - compareQuantity;
    }

    @Override
    public String toString(){
        return title;
    }

}