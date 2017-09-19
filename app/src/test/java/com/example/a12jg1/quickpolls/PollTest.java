package com.example.a12jg1.quickpolls;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by 12jg1 on 5/5/2017.
 */

public class PollTest {
    Poll pollOne = new Poll();

    @Test
    public void voteTest(){

        assertFalse(pollOne.hasVoted("false"));
        assertFalse(pollOne.hasVoted(""));
    }

    @Test
    public void popularityTest(){
        assertFalse(pollOne.hasVotedPopularity(""));
        assertFalse(pollOne.hasVotedPopularity("false"));
    }

}
