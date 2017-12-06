package com.svetlanamarhefka.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;
import com.svetlanamarhefka.util.Side;

import java.io.File;
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

    private boolean m_InComTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_debug);

        // Check if the user started a new game.
        Intent l_intent = getIntent();
        // Gets the round from the Start Screen Activity
        m_Round = (Round) l_intent.getSerializableExtra("EXTRA_ROUND");
        m_InComTurn = (Boolean) l_intent.getSerializableExtra("COMP_TURN");
        m_Context = this.getApplicationContext();
        // Get the views needed to display to the board
        m_DominoView = new DominoView(m_Context);
        m_BoardView = new BoardView(m_Context);
        m_BoneView = new BoneyardView(m_Context);
        m_ComHandView = new ComputerView(m_Context);
        m_HumanHandView = new HumanView(m_Context, this);

        Toast.makeText(MainGame.this, "Starting Game", Toast.LENGTH_LONG).show();
        // initialize the layout
        initLayout();

        if(m_Round.getHumanHand().isEmpty())
        {
            // distribute hands
            distributeHands();
            if(m_Round.getBoard().isEmpty())
            {
                // get the first player
                getFirstPlayer();
            }
        }
        else if (m_Round.getBoard().isEmpty())
        {
            // get the first player
            getFirstPlayer();
        }

        updateLayout();

        setNextTurn();

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
        l_ComputerScore.setText(String.valueOf((m_Round.getPlayerScore(Computer.class))));

        // Display the human name to the main game screen
        TextView l_HumanName = findViewById(R.id.t_HName_1);
        l_HumanName.setText(m_Round.getPlayerName().toString() + " ");

        // Display the human score to the screen
        TextView l_HumanScore = findViewById(R.id.t_HScore);
        l_HumanScore.setText(String.valueOf((m_Round.getPlayerScore(Human.class))));

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
            messages.show();
        } else {
            setNextTurn();
            System.out.print("In the else statement...Everything is okay!");
        }
    }

    public void drawButtonClick(View view)
    {
        System.out.print("Draw Button Pressed");
        Domino t_DrawDomino = m_Round.humanDrawTile();
        if(t_DrawDomino == null)
        {
            Toast.makeText(m_Context, "You can't draw another tile\n", Toast.LENGTH_LONG).show();
            updateLayout();
        }
        else
        {
            Toast.makeText(m_Context, m_Round.getPlayerName() + " drew: " + t_DrawDomino.toString(), Toast.LENGTH_LONG).show();
            updateLayout();
        }
    }

    public void passButtonClick(View view)
    {
        System.out.print("Pass Button Pressed");
        if(m_Round.canHumanPass())
        {
            updateLayout();
            setNextTurn();
            updateLayout();
        }
        else
        {
            Toast.makeText(m_Context, "You can't pass.  Look for a move...\n", Toast.LENGTH_LONG).show();
            updateLayout();
        }
    }

    public void getHelpButtonClick(View view)
    {
        String bestMove = m_Round.getHelp();
        if (bestMove == null) {
            bestMove = "You do not have any valid moves please draw a tile or pass your turn!";
        }
        Toast.makeText(MainGame.this, bestMove, Toast.LENGTH_LONG).show();
    }

    public void saveButtonClick(View view)
    {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TestSave.txt";
        if (m_Round.saveGame(new File(filePath), m_Round.gameInformation())) {
            Toast.makeText(MainGame.this, "The game has been saved. GoodBye!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void updateLayout()
    {
        if(m_Round.roundOver())
        {
            System.out.print("Round is over!");
            AlertDialog.Builder t_EndRoundAlert = new AlertDialog.Builder(this);
            StringBuilder t_FinalRoundInfo = new StringBuilder();
            t_FinalRoundInfo.append("Round " + m_Round.getM_RoundNumber() + " Ended!\n");
            t_FinalRoundInfo.append(m_Round.getRoundWinnerAndScore());
            //calculate scores and show here
            t_EndRoundAlert.setMessage(t_FinalRoundInfo.toString())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent output = new Intent();
                            output.putExtra("HUMAN_SCORE",m_Round.getPlayerScore(Human.class));
                            output.putExtra("COM_SCORE",m_Round.getPlayerScore(Computer.class));
                            setResult(RESULT_OK, output);
                            finish();
                        }
                    });
            //save and quit, normalize the firstPlayer
            t_EndRoundAlert.show();
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
            Toast toast = Toast.makeText(MainGame.this, message, Toast.LENGTH_SHORT);
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
