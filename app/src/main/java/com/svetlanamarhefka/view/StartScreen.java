package com.svetlanamarhefka.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svetlanamarhefka.R.layout;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class StartScreen extends Activity {

    private int m_TourScore;
    private String m_UserName;
    private String m_TempString;
    private int m_TempNumber;
    private boolean m_FreezeText;
    private boolean m_FreezeNumber;
    private boolean m_AlertComplete;

    private final static String EXTRA_NEWGAME = "com.svetlanamarhefka.newGame";
    public final static String EXTRA_TOURSCORE = "com.svetlanamarhefka.mTScore";
    public final static String EXTRA_USERNAME = "com.svetlanamarhefka.mUName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize all variables
        m_TourScore = 0;
        m_UserName = "";
        m_TempString = "";
        m_TempNumber = 0;
        m_FreezeText = false;
        m_FreezeNumber = false;
        m_AlertComplete = false;

        super.onCreate(savedInstanceState);
        setContentView(layout.start_screen);
    }

    public void newGameClick(View view) {
        System.out.print("newGame button pressed!\n");
        Intent intent = new Intent(this, GameInfo.class);
        startActivity(intent);

    }

    public void loadGameClick(View view)
    {
        System.out.print("loadGame button pressed!\n");
        Intent intent = new Intent(this, MainGame.class);
        intent.putExtra("EXTRA_NEWGAME", false);
        startActivity(intent);
    }
}
