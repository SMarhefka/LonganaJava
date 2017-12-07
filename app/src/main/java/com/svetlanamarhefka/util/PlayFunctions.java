package com.svetlanamarhefka.util;

import com.svetlanamarhefka.model.Board;
import com.svetlanamarhefka.model.Domino;
import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;
import com.svetlanamarhefka.model.player.Player;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/27/2017                                             *
 ****************************************************************/

public class PlayFunctions implements Serializable {

    // Create a new vector of possible moves
    private Vector<PlayerMove> possibleMoves;
    private Vector<Domino> m_SortedHand;
    /**
     * To get all the possible moves that can be made given the current game state
     * @param a_InBoard layout of the current game state
     * @param a_InPrevPassed indicates whether previous player passed
     * @return vector of potential moves that can be made
     */
    public Vector<PlayerMove> getBestMoves(Board a_InBoard, boolean a_InPrevPassed,
                                           Player a_InPlayer, Side a_InSide, Side a_InOtherSide)
    {
        // reset the vectors
        possibleMoves = new Vector<>();
        m_SortedHand = new Vector<>();

        if ((a_InPlayer.getClass().equals(Computer.class)) && (a_InBoard.boardRight() <= 3)
            || ((a_InPlayer.getClass().equals(Human.class)) && (a_InBoard.boardLeft() <= 3)))
        {
            m_SortedHand = a_InPlayer.getHand().sortBasedLeft(a_InPlayer.getHand().getM_PlayerTiles());
        }
        else
        {
            m_SortedHand = a_InPlayer.getHand().sortBasedRight(a_InPlayer.getHand().getM_PlayerTiles());
        }

        // Go through the entire hand
        for (int count = 0; count < m_SortedHand.size(); count++)
        {
            // Get the domino associated with the current index
            Domino t_Domino = a_InPlayer.getHand().getTilesAtIndex(count);
            // reset moves so that we have a clear slate to work with
            PlayerMove t_PotentialMoves = null;
            //if the domino can be placed on the regular side then create a new p
            if (a_InBoard.validDomino(t_Domino, a_InSide)) t_PotentialMoves = new PlayerMove(t_Domino, a_InSide);

            //if previous player passed or the domino is a double, things change
            if (a_InPrevPassed || t_Domino.isDouble())
            {
                if (a_InBoard.validDomino(t_Domino, a_InOtherSide)) {
                    //if the move can be placed in the other side
                    //if it can be placed in the regukar side, then it can be placed on any side
                    if (t_PotentialMoves != null)
                    {
                        t_PotentialMoves.setM_PlaySide(Side.EITHER);
                    }
                    //else it can only be placed on the other side
                    else
                    {
                        t_PotentialMoves = new PlayerMove(t_Domino, a_InOtherSide);
                    }
                }
            }

            // If the domino can potentially be placed then add it to the vector
            // of possible moves
            if (t_PotentialMoves != null)
            {
                possibleMoves.add(t_PotentialMoves);
            }
        }
        // Return the vector of potential moves
        return possibleMoves;
    }

    /**
     * Reorders tiles based on the bestScore
     * @param a_InVector
     * @return
     */
    public Vector<PlayerMove> sortBasedOnScore(Vector<PlayerMove> a_InVector)
    {
        Vector<PlayerMove> t_CompareMoves = new Vector<PlayerMove>();
        t_CompareMoves = a_InVector;

        // Sort potential moves based on the tiles sum
        Collections.sort(t_CompareMoves, new Comparator<PlayerMove>()
        {
            // New comparison operator
            public int compare(PlayerMove dom1, PlayerMove dom2)
            {
                return (dom2).getM_Domino().tileSum() - (dom1).getM_Domino().tileSum();
            }
        });

        return t_CompareMoves;
    }

    /**
     * Determine the best side to play.  This part is only used if there is a double that
     * can be placed on both the left and the right hand side.
     * @param a_InMove The placement option that we are trying to determine where it
     * @param a_InBoard The board
     * @param a_InPrevPassed
     * @param a_InPlayer
     * @param a_InSide
     * @param a_InOtherSide
     * @return highest sum that can be yielded
     */
    public int bestSideToPlay(PlayerMove a_InMove, Board a_InBoard, boolean a_InPrevPassed,
                         Player a_InPlayer, Side a_InSide, Side a_InOtherSide)
    {
        a_InBoard.addToBoard(a_InMove.getM_Domino(), a_InMove.getM_PlaySide());

        Vector<PlayerMove> t_PossibleMoves;
        // Getting the list of all the possible moves starting at the begging of the hand
        t_PossibleMoves = getBestMoves (a_InBoard, a_InPrevPassed, a_InPlayer, a_InSide, a_InOtherSide);

        if (t_PossibleMoves.isEmpty())
        {
            return 0;
        }

        // The best possible score
        int t_bestScore = 0;

        // Then sort the options based on the tile sum
        // Need to see if it will be used
        t_PossibleMoves = sortBasedOnScore(t_PossibleMoves);
        //
        if(a_InMove.getM_Domino().equals(t_PossibleMoves.firstElement().getM_Domino()))
        {
            // If there are more then two possible options
            if(t_PossibleMoves.size() > 2)
            {
                // then take the last
                t_bestScore = t_PossibleMoves.get(1).getM_Domino().tileSum();
            }
            else
            {
                // return 0
                return 0;
            }
        }
        else
        {
            // otherwise
            t_bestScore = t_PossibleMoves.firstElement().getM_Domino().tileSum();
        }

        a_InBoard.undoPlacement(a_InMove.getM_PlaySide());
        return t_bestScore;
    }

}
