package com.svetlanamarhefka.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.svetlanamarhefka.R;
import com.svetlanamarhefka.model.Domino;
import com.svetlanamarhefka.model.Round;
import com.svetlanamarhefka.model.player.Side;

import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                          *
 ****************************************************************/

public class MainGame extends AppCompatActivity {

    private DominoView m_DominoView;
    private BoardView m_BoardView;
    private BoneyardView m_BoneView;
    private ComputerView m_ComHandView;
    private HumanView m_HumanHandView;

    private Round m_Round;
    private Context m_Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_debug);

        // Check if the user started a new game.
        Intent l_intent = getIntent();
        // Gets the round from the Start Screen Activity
        m_Round = (Round) l_intent.getSerializableExtra("EXTRA_ROUND");
        m_Context = this.getApplicationContext();

        m_DominoView = new DominoView(m_Context);
        m_BoardView = new BoardView(m_Context);
        m_BoneView = new BoneyardView(m_Context);
        m_ComHandView = new ComputerView(m_Context);
        m_HumanHandView = new HumanView(m_Context, this);

        // initialize the layout
        initLayout();
        // distribute hands
        distributeHands();
        // get the first player
        getFirstPlayer();
    }

    private void initLayout()
    {
        // Display Tournament Score to the screen
        TextView l_TourScore = findViewById(R.id.t_TScore);
        l_TourScore.setText(String.valueOf(m_Round.getM_TourScore()));
        // Display the Round Number to the screen
        TextView l_RoundNum = findViewById(R.id.t_RNum);
        l_RoundNum.setText(String.valueOf(m_Round.getM_RoundNumber()));

        // Display the computer score to the screen
        TextView l_ComputerScore = findViewById(R.id.t_CScore);

        // Display the human name to the main game screen
        TextView l_HumanName = findViewById(R.id.t_HName_1);
        l_HumanName.setText(m_Round.getPlayerName().toString() + " ");
        // Display the human score to the screen

        // Display the human name to the main game screen
        l_HumanName = findViewById(R.id.t_HName_2);
        l_HumanName.setText(m_Round.getPlayerName().toString() + " ");

        // show the initial boneyard
        updateBoneyard();
    }

    private void distributeHands()
    {
        m_Round.distributeTiles();
        updateLayout();
    }

    public void getFirstPlayer()
    {
        String t_FirstPlayer = m_Round.firstPlayer();
        if (t_FirstPlayer != null) {
            updateLayout();
            AlertDialog.Builder messages = new AlertDialog.Builder(MainGame.this);
            messages.setMessage(t_FirstPlayer)
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setNextTurn();
                            System.out.print("Continue pressed!");
                        }
                    });
            //save and quit, normalize the firstPlayer
            messages.show();
        } else {
            setNextTurn();
            System.out.print("In the else statment...Everything is okay!");
        }
    }

    public void drawButtonClick(View view)
    {
        System.out.print("Draw Button Pressed");
    }


    private void updateLayout()
    {
        if(m_Round.roundOver())
        {
            System.out.print("Round is over!");
        }
        updateBoard();
        updateBoneyard();
        updateHumanHand();
        updateComputerHand();
    }

    private void updateBoard()
    {
        Vector<Domino> t_Board = m_Round.getBoard();
        LinearLayout l_Board = findViewById(R.id.l_Board);
        l_Board.removeAllViews();
        m_BoardView.displayBoard(t_Board, l_Board);
    }

    private void updateBoneyard()
    {
        // refresh the boneyard
        Vector<Domino> t_Boneyard = m_Round.getBoneYard();
        LinearLayout l_Boneyard = findViewById(R.id.l_Boneyard);
        l_Boneyard.removeAllViews();
        // display new boneyard
        m_BoneView.displayBoneyard(t_Boneyard, l_Boneyard);
    }

    private void updateHumanHand()
    {
        Vector<Domino> t_HumanHand = m_Round.getHumanHand();
        LinearLayout l_HumanHand = findViewById(R.id.l_HumHand);
        l_HumanHand.removeAllViews();
        m_HumanHandView.displayHand(t_HumanHand, l_HumanHand);
    }

    private void updateComputerHand()
    {
        Vector<Domino> t_ComHand = m_Round.getComHand();
        LinearLayout l_ComHand = findViewById(R.id.l_ComHand);
        l_ComHand.removeAllViews();
        m_ComHandView.displayHand(t_ComHand, l_ComHand);
    }

    protected void setNextTurn()
    {
        String playerResult = m_Round.setHumanTurn();
        if(playerResult != null)
        {
            updateLayout();
            Toast.makeText(MainGame.this, playerResult, Toast.LENGTH_LONG).show();
            findViewById(android.R.id.content).invalidate();

        }
    }

    /**
     * Plays the move and updates the view
     * @param a_InDomino domino to play
     * @param a_InSide side to play
     * @return true if move was possible, false if not
     */

    protected boolean playRound(Domino a_InDomino, Side a_InSide) {
        String message = m_Round.humanPlay(a_InDomino, a_InSide);
        if (message != null) {
            Toast toast = Toast.makeText(MainGame.this, message, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        updateLayout();
        return true;
    }

    public ImageButton addButton(final Domino a_InDomino, boolean buttonsEnabled) {
        ImageButton button = new ImageButton(m_Context);
        button.setLayoutParams(new ViewGroup.LayoutParams(1, 2));

        if (buttonsEnabled) {
            //eventListenerForButton
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder sideSelector = new AlertDialog.Builder(MainGame.this);
                    sideSelector.setMessage("Select a side to play: ")
                            .setPositiveButton("RIGHT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    if (playRound(a_InDomino, Side.RIGHT)) {
                                        {
                                            setNextTurn();
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("LEFT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (playRound(a_InDomino, Side.LEFT)) {
                                        {
                                            setNextTurn();
                                        }
                                    }
                                }
                            });
                    sideSelector.show();
                }
            });
        }

        button.setBackground(m_DominoView.drawDomino(a_InDomino.getM_leftSide(), a_InDomino.getM_rightSide(), true, false));
        return button;
    }
}
