package com.svetlanamarhefka.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.svetlanamarhefka.R;
import com.svetlanamarhefka.model.Round;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                          *
 ****************************************************************/

public class MainGame extends AppCompatActivity {

    //private BoneyardViewFail hand;
    private Round m_Round;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_debug);

        // Check if the user started a new game.
        Intent l_intent = getIntent();
        // Gets the round from the Start Screen Activity
        m_Round = (Round) l_intent.getSerializableExtra("EXTRA_ROUND");
        //TextView l_TourScore = (TextView) findViewById(R.id.t_TScore);
        //TextView l_RoundNum = (TextView) findViewById(R.id.t_RNum);

        initLayout();
    }

    private void initLayout()
    {
        TextView l_TourScore = findViewById(R.id.t_TScore);
        l_TourScore.setText(String.valueOf(m_Round.getM_TourScore()));
        TextView l_RoundNum = findViewById(R.id.t_RNum);
        l_RoundNum.setText(String.valueOf(m_Round.getM_RoundNumber()));
    }
}
