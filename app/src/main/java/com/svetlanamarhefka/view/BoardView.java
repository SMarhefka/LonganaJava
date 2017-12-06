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
 * Date:    11/26/2017                                             *
 ****************************************************************/

public class BoardView extends View {

    private DominoView m_DominoView;
    private Context m_Context;

    BoardView(Context context)
    {
        super(context);
        m_Context = context;
        m_DominoView = new DominoView(m_Context);
    }

    public void displayBoard(Vector<Domino> a_InDomino, LinearLayout a_InLayout)
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

            if(a_InDomino.get(i).isDouble())
            {
                a_InLayout.addView(m_DominoView.imgDominoUpDwn (a_InDomino.get(i)), gridLayoutParam);
            }
            else
            {
                if (a_InDomino.get(i).getM_leftSide() < a_InDomino.get(i).getM_rightSide())
                {
                    a_InLayout.addView(m_DominoView.imgDominoAccross (a_InDomino.get(i), false), gridLayoutParam);
                }
                else
                {
                    a_InLayout.addView(m_DominoView.imgDominoAccross (a_InDomino.get(i), true), gridLayoutParam);
                }
            }
        }
    }
}
