package com.svetlanamarhefka.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svetlanamarhefka.R;
import com.svetlanamarhefka.model.Domino;
import com.svetlanamarhefka.model.Round;

import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                          *
 ****************************************************************/

public class MainGame extends AppCompatActivity {

    private BoneyardView m_BoneView;
    private ComputerView m_ComHandView;
    private HumanView m_HumanHandView;
    private Round m_Round;
    private Context m_Context;
    private Matrix m_Matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_debug);

        // Check if the user started a new game.
        Intent l_intent = getIntent();
        // Gets the round from the Start Screen Activity
        m_Round = (Round) l_intent.getSerializableExtra("EXTRA_ROUND");

        m_Context = this.getApplicationContext();
        m_BoneView = new BoneyardView(m_Context);
        m_ComHandView = new ComputerView(m_Context);
        m_HumanHandView = new HumanView(m_Context);

        // initialize the layout
        initLayout();
        // distribute hands
        distributeHands();
    }

    private void initLayout()
    {
        TextView l_TourScore = findViewById(R.id.t_TScore);
        l_TourScore.setText(String.valueOf(m_Round.getM_TourScore()));
        TextView l_RoundNum = findViewById(R.id.t_RNum);
        l_RoundNum.setText(String.valueOf(m_Round.getM_RoundNumber()));
        //t_HName_1
        TextView l_HumanName = findViewById(R.id.t_HName_1);
        l_HumanName.setText(m_Round.playerName().toString() + " ");
        //t_HName_2
        l_HumanName = findViewById(R.id.t_HName_2);
        l_HumanName.setText(m_Round.playerName().toString() + " ");

        Vector<Domino> t_Boneyard = m_Round.getBoneYard();
        LinearLayout l_Boneyard = findViewById(R.id.l_Boneyard);
        l_Boneyard.removeAllViews();

        m_BoneView.displayBoneyard(t_Boneyard, l_Boneyard);
    }

    private void distributeHands()
    {
        m_Round.distributeTiles();
        System.out.print(String.valueOf(m_Round.getComHand().size()));
        // refresh the boneyard
        Vector<Domino> t_Boneyard = m_Round.getBoneYard();
        LinearLayout l_Boneyard = findViewById(R.id.l_Boneyard);
        l_Boneyard.removeAllViews();
        // display new boneyard
        m_BoneView.displayBoneyard(t_Boneyard, l_Boneyard);

        Vector<Domino> t_ComHand = m_Round.getComHand();
        LinearLayout l_ComHand = findViewById(R.id.l_ComHand);
        l_ComHand.removeAllViews();
        m_ComHandView.addButtons(t_ComHand, l_ComHand);

        Vector<Domino> t_HumanHand = m_Round.getHumanHand();
        LinearLayout l_HumanHand = findViewById(R.id.l_HumHand);
        l_HumanHand.removeAllViews();
        m_HumanHandView.displayHumanHand(t_HumanHand, l_HumanHand);
    }
}
