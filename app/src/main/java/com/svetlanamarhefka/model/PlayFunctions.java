package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Player;
import com.svetlanamarhefka.model.player.PlayerMove;
import com.svetlanamarhefka.model.player.Side;

import java.io.Serializable;
import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/27/2017                                             *
 ****************************************************************/

public class PlayFunctions implements Serializable {

    /**
     * To get all the possible moves that can be made given the current game state
     * @param a_InBoard layout of the current game state
     * @param a_InPrevPassed indicates whether previous player passed
     * @return vector of potential moves that can be made
     */
    public Vector<PlayerMove> possibleMoves(Board a_InBoard, boolean a_InPrevPassed,
                                                   Player a_InPlayer, Side a_InSide, Side a_InOtherSide)
    {
        Vector<PlayerMove> possibleMoves = new Vector<>();
        for (int count = 0; count < a_InPlayer.getHand().getSize(); count++)
        {
            Domino t_Domino = a_InPlayer.getHand().getTilesAtIndex(count);
            // reset moves
            PlayerMove t_PotMoves = null;
            //if the domino can be placed on the regular side
            if (a_InBoard.validDomino(t_Domino, a_InSide)) t_PotMoves = new PlayerMove(t_Domino, a_InSide);

            //if previous player passed or the domino is a double, things change
            if (a_InPrevPassed || t_Domino.isDouble())
            {
                if (a_InBoard.validDomino(t_Domino, a_InOtherSide)) {
                    //if the move can be placed in the other side
                    //if it can be placed in the regukar side, then it can be placed on any side
                    if (t_PotMoves != null) t_PotMoves.setM_PlaySide(Side.EITHER);
                        //else it can only be placed on the other side
                    else t_PotMoves = new PlayerMove(t_Domino, a_InOtherSide);
                }
            }

            if (t_PotMoves != null) possibleMoves.add(t_PotMoves);
        }
        return possibleMoves;
    }
}
