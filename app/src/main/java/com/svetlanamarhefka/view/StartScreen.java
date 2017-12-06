package com.svetlanamarhefka.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.svetlanamarhefka.R;
import com.svetlanamarhefka.model.Round;
import com.svetlanamarhefka.model.Tournament;
import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;

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

        //load the tournament
        //get the round and start the roundActivity
        AlertDialog.Builder alert = new AlertDialog.Builder(StartScreen.this);
        alert.setTitle("Select a game");

        final File[] files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });


        String[] items = new String[files.length];
        for (int i = 0; i < files.length; ++i) {
            items[i] = files[i].getName();
        }

        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView lw = ((AlertDialog) dialog).getListView();
                String fileName = (String) lw.getAdapter().getItem(which);

                //de-serialize and create round;
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + fileName;
                try {
                    InputStream t_InputFile = new FileInputStream(filePath);
                    startNewRound(m_Tournament.loadGame(t_InputFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.show();
    }

    private void startNewRound(Round a_InRound)
    {
        Intent intent = new Intent(this, MainGame.class);
        intent.putExtra("EXTRA_ROUND", a_InRound);
        intent.putExtra("COMP_TURN", a_InRound.getPrevPass());
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int a_InRequestCode, int a_InResultCode, Intent a_InData)
    {
        if (a_InResultCode == RESULT_CANCELED) {
            recreate();
            return;
        }

        // Get the human score from the last round
        int t_HumanScore = a_InData.getIntExtra("HUMAN_SCORE", 0);
        // Get the computer score from the last round
        int t_ComScore = a_InData.getIntExtra("COM_SCORE", 0);

        // Set the overall score for the human
        m_Tournament.setPlayerScore(Human.class, t_HumanScore);
        // Set the overall score for the computer class
        m_Tournament.setPlayerScore(Computer.class, t_ComScore);

        // If the overall game is over
        if(m_Tournament.isOver())
        {
            AlertDialog.Builder t_EndTournament = new AlertDialog.Builder(StartScreen.this);
            t_EndTournament.setTitle("Tournament Ended");
            t_EndTournament.setMessage(m_Tournament.getGameWinnerAndScore())
                    .setPositiveButton("I'm Done with This", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            t_EndTournament.create().show();
        }
        else
        {
            // Create a new round
            startNewRound(m_Tournament.createNewRound());
        }
    }
}
