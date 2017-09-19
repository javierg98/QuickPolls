package com.example.a12jg1.quickpolls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12jg1 on 4/18/2017.
 */

public class Option {

    private String optionTitle;
    private List<String> uniqueIDs;

    public Option(){
        this.optionTitle = "optionTitle";
        this.uniqueIDs = new ArrayList<>();
    }

    public Option(String optionTitle){
        this.optionTitle = optionTitle;
        this.uniqueIDs = new ArrayList<>();
    }

    public Option(String optionTitle, List<String> uids,  String uid){
        this.optionTitle = optionTitle;
        this.uniqueIDs = uids;
        this.uniqueIDs.add(uid);
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public int getVotes(){
        return uniqueIDs.size();
    }

    public List<String> getUniqueIDS(){return uniqueIDs;}

    public void setUniqueIDS(List<String> uniqueIDs){this.uniqueIDs = uniqueIDs;}

}