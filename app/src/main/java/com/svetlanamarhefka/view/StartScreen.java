package com.svetlanamarhefka.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.svetlanamarhefka.R;
import com.svetlanamarhefka.model.Round;
import com.svetlanamarhefka.model.Tournament;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class StartScreen extends AppCompatActivity {

    Tournament m_Tournament;
    private String m_TempString;
    private int m_TempNumber;
    private Boolean m_FreezeText;
    private Boolean m_FreezeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize all variables
        m_Tournament = new Tournament();
        m_TempString = "";
        m_TempNumber = 0;
        m_FreezeText = false;
        m_FreezeNumber = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
    }

    public void newGameClick(View view) {
        System.out.print("newGame button pressed!\n");
        //Intent intent = new Intent(this, GameInfo.class);
        //intent.putExtra("EXTRA_NEWGAME", true);
        //startActivity(intent);

        createAlertDialog();

    }

    private void createAlertDialog()
    {
        // Create a new alert dialog
        final AlertDialog.Builder m_AlertDialog = new AlertDialog.Builder(StartScreen.this);
        View m_NewView = getLayoutInflater().inflate(R.layout.game_info, null, false);


        // need to define the stuff in our view
        //Get the widgets reference from XML layout
        final TextView txtView = m_NewView.findViewById(R.id.q_tourScore);
        final NumberPicker myNumPicker = m_NewView.findViewById(R.id.a_tourScore);
        final EditText myUserName = m_NewView.findViewById(R.id.a_userName);

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        myNumPicker.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        myNumPicker.setMaxValue(999);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        myNumPicker.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        myNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                txtView.setTextSize(25);
                //Display the newly selected number from picker
                if(newVal > 0)
                {
                    //Set TextView text color
                    txtView.setTextColor(Color.parseColor("#ffd32b3b"));
                    txtView.setText("Tournament Will End On/After: " + newVal + " Points");
                }
                else
                {
                    txtView.setTextColor(Color.parseColor("Black"));
                    txtView.setText(R.string.ask_score);
                }
            }
        });

        if (m_FreezeNumber == true)
        {
            myNumPicker.setValue(m_TempNumber);
            myNumPicker.getDisplay();
            m_FreezeNumber = false;
            m_TempNumber = 0;
        }
        if(m_FreezeText == true)
        {
            // Set the value
            myUserName.setText(m_TempString);
            m_FreezeText = false;
            m_TempString = "";
        }

        m_AlertDialog.setView(m_NewView);
        m_AlertDialog.create();
        m_AlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        m_AlertDialog.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(myNumPicker.getValue() == 0 && myUserName.getText().toString().isEmpty())
                {
                    ScoreWarning();
                }
                else if(myNumPicker.getValue() == 0)
                {
                    m_FreezeText = true;
                    m_TempString = myUserName.getText().toString();
                    ScoreWarning();
                }
                else if(myUserName.getText().toString().isEmpty())
                {
                    m_FreezeNumber = true;
                    m_TempNumber = myNumPicker.getValue();
                    NameWarning();
                }
                else
                {
                    m_TempNumber = myNumPicker.getValue();
                    m_TempString = myUserName.getText().toString();

                    m_Tournament.setM_TourScore(m_TempNumber);
                    m_Tournament.setM_PlayerName(m_TempString);

                    m_Tournament.createPlayers();

                    startNewRound(m_Tournament.createNewRound());
                }

                System.out.print("**I want to build a snowman!**\n");

            }
        });
        m_AlertDialog.show();
    }

    private void NameWarning()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(StartScreen.this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Player name will be set to Human is that okay?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.print("Tournament Score: " + m_TempNumber + "\n");
                        m_Tournament.setM_TourScore(m_TempNumber);
                        m_Tournament.setM_PlayerName("");
                        m_Tournament.createPlayers();
                        startNewRound(m_Tournament.createNewRound());
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO, Wait I want a name!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        m_FreezeText = false;
                        m_TempString = "";
                        createAlertDialog();
                    }
                });
        alertDialog.show();
    }

    private void ScoreWarning()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StartScreen.this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage(R.string.invalid_score)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        createAlertDialog();
                    }
                });
        alertDialog.create().show();
    }

    public void loadGameClick(View view)
    {
        System.out.print("loadGame button pressed!\n");

        //Intent intent = new Intent(this, MainGame.class);
        //intent.putExtra("EXTRA_NEWGAME", false);
        //startActivity(intent);

        m_Tournament.setM_TourScore(150);
        m_Tournament.setM_RoundNumber(6);
        m_Tournament.setM_PlayerName("Lana");
        m_Tournament.createPlayers();
        startNewRound(m_Tournament.createNewRound());
    }

    private void startNewRound(Round a_InRound)
    {
        Intent intent = new Intent(this, MainGame.class);
        intent.putExtra("EXTRA_ROUND", a_InRound);
        startActivity(intent);
    }
}
