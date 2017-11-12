package com.svetlanamarhefka.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svetlanamarhefka.R;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class StartScreen extends Activity {

    private int m_UserNumber;
    private String m_UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
    }

    public void newGameClick(View view) {
        System.out.print("newGame button pressed!\n");
        Intent intent = new Intent(this, GameInfo.class);
        startActivity(intent);
    }

    public void loadGameClick(View view)
    {
        System.out.print("loadGame button pressed!\n");
    }
}
