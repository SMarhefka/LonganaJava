package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Board;
import com.svetlanamarhefka.util.PlayerMove;
import com.svetlanamarhefka.util.Side;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Computer extends Player {
    /**
     * Default constructor.
     */
    public Computer()
    {
        // Give the computer its name.
        this.playerName = "Computer";
        // Set the default side to play on
        m_DefaultSide = Side.RIGHT;
        // Set the other side as left
        m_OtherSide = Side.LEFT;
    }

    public String getBestMove()
    {
        return bestMove;
    }

    public boolean play(int a_InDominoIndex, Board a_InBoard, Side a_InSide, boolean a_InPrevPassed)
    {
        return super.play(a_InDominoIndex, a_InBoard, a_InSide, a_InPrevPassed);
    }

    public boolean play(Board a_InBoard, boolean a_InPrevPass)
    {
        System.out.print("Prev Pass: " + a_InPrevPass);
        PlayerMove t_PlayMove = playMove(a_InBoard, a_InPrevPass);
        if(t_PlayMove == null)
        {
            return false;
        }
        bestMove = new StringBuilder(bestMove)
                .append("\nComputer played ")
                .append(t_PlayMove.getM_Domino().toString())
                .append(" to the ")
                .append(t_PlayMove.getM_PlaySide().toString()).toString();
        return super.play(m_CurrentHand.getDomIndex(t_PlayMove.getM_Domino()), a_InBoard, t_PlayMove.getM_PlaySide(), a_InPrevPass);
    }
}
