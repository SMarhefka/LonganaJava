package com.svetlanamarhefka.view;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.svetlanamarhefka.model.Domino;

import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/25/2017                                             *
 ****************************************************************/

public class HumanView extends View {

    private MainGame m_MainView;
    private DominoView m_DominoView;
    private Context m_Context;

    public HumanView(Context context, MainGame a_InMainGame)
    {
        super(context);
        m_Context = context;
        m_DominoView = new DominoView(m_Context);
        m_MainView = a_InMainGame;
    }

    public void displayHand(Vector<Domino> a_InDomino, LinearLayout a_InLayout)
    {
        GridLayout.LayoutParams gridLayoutParam;

        int row = 0, col = 0;
        for (int i = 0; i < a_InDomino.size(); i++) {
            if (i >= 14) row = 1;
            col = i % 14;
            GridLayout.Spec gridRow = GridLayout.spec(row, 1);
            GridLayout.Spec gridCol = GridLayout.spec(col, 1);
            gridLayoutParam = new GridLayout.LayoutParams(gridRow, gridCol);
            gridLayoutParam.setMargins(5, 0, 5, 0);
            a_InLayout.addView(m_MainView.addButton (a_InDomino.get(i), true), gridLayoutParam);
        }
    }
}
