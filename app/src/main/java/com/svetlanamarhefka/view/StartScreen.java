package com.svetlanamarhefka.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.svetlanamarhefka.R;
import com.svetlanamarhefka.R.layout;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class StartScreen extends Activity {

    private int m_UserNumber;
    private String m_UserName;
    private String m_TempString;
    private int m_TempNumber;
    private Boolean m_FreezeText;
    private Boolean m_FreezeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize all variables
        m_UserNumber = 0;
        m_UserName = "";
        m_TempString = "";
        m_TempNumber = 0;
        m_FreezeText = false;
        m_FreezeNumber = false;
        super.onCreate(savedInstanceState);
        setContentView(layout.start_screen);
    }

    public void newGameClick(View view)
    {
        System.out.print("newGame button pressed!\n");
        //Intent intent = new Intent(this, GameInfo.class);
        //startActivity(intent);

        createAlertDialog();
    }

    private void createAlertDialog()
    {
        // Create a new alert dialog
        final AlertDialog.Builder m_AlertDialog = new AlertDialog.Builder(StartScreen.this);
        View m_NewView = getLayoutInflater().inflate(layout.game_info, null, false);


        // need to define the stuff in our view
        //Get the widgets reference from XML layout
        final TextView txtView = m_NewView.findViewById(R.id.q_tourScore);
        final NumberPicker myNumPicker = m_NewView.findViewById(R.id.userNumber);
        final EditText myUserName = m_NewView.findViewById(R.id.userName);

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
        m_AlertDialog.setNeutralButton("NEXT", new DialogInterface.OnClickListener() {
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
                    m_UserName = myUserName.getText().toString();
                    m_UserNumber = myNumPicker.getValue();
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
                        m_FreezeText = true;
                        m_TempString = "Human";
                        createAlertDialog();
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
    }
}
