package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Board;
import com.svetlanamarhefka.model.Domino;
import com.svetlanamarhefka.model.Hand;
import com.svetlanamarhefka.model.PlayFunctions;

import java.io.Serializable;
import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Player implements Serializable {

    private int m_PlayerScore;
    // To keep track of when a domino is drawn from the Boneyard
    private boolean m_DominoTaken;
    // The hand of the current player
    protected Hand m_CurrentHand;
    // The name of the player.
    protected String playerName;

    private PlayFunctions m_PlayFunctions;

    // The defaultSide for the given player
    Side m_DefaultSide;
    // The non-default side for the given player
    Side m_OtherSide;


    public Player()
    {
        m_CurrentHand = new Hand();
        m_DominoTaken = false;
        m_PlayerScore = 0;
        m_PlayFunctions = new PlayFunctions();
    }

    /**
     * Returns the name of the player.
     * @return a string containing the name of the player
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     *
     * @return
     */
    public int getM_PlayerScore()
    {
        return m_PlayerScore;
    }

    /**
     *
     * @return m_CurrentHand
     */
    public Hand getHand()
    {
        return m_CurrentHand;
    }

    public boolean playDomino(int a_InDomIndex, Board a_InBoard, Side a_InSide) {

        Domino t_Domino = m_CurrentHand.getTilesAtIndex(a_InDomIndex);

        if(t_Domino.isEngine(a_InBoard.getM_EngineValue()))
        {
            a_InBoard.setM_EngineSet();
            a_InBoard.addEngine(t_Domino, a_InSide);
            m_CurrentHand.removeTile(a_InDomIndex);
            return true;
        }
        if(a_InBoard.addToBoard(t_Domino, a_InSide))
        {
            m_CurrentHand.removeTile(a_InDomIndex);
            return true;
        }
        // This should only return if there is an issue
        return false;
    }

    public boolean play(int a_InDomIndex, Board a_InBoard, Side a_InSide, boolean a_InPrevPassed)
    {
        Domino t_Domino = m_CurrentHand.getTilesAtIndex(a_InDomIndex);
        // If the engine is set
        if (a_InBoard.isM_EngineSet()) {
            System.out.print("Placing domino" + " " + t_Domino.isDouble() + " " + a_InPrevPassed);

            if (a_InSide == m_OtherSide && (!t_Domino.isDouble() && !a_InPrevPassed))
            {
                return false;
            }
            return playDomino(a_InDomIndex, a_InBoard, a_InSide);
        }
        // If the engine is not set but the domino being played is the engine
        else if(t_Domino.isEngine(a_InBoard.getM_EngineValue()))
        {
            return playDomino(a_InDomIndex, a_InBoard, a_InSide);
        }
        return false;
    }

    public void addToScore(int a_InScore)
    {
        m_PlayerScore += a_InScore;
    }

    /**
     * Sets m_DominoTaken to true so that the program knows that a
     * tile has been taken from the boneyard
     */
    public void setM_DominoTaken()
    {
        m_DominoTaken = true;
    }

    /**
     * Check to see if a domino has already been taken
     * @return
     */
    public boolean isM_DominoTaken()
    {
        return m_DominoTaken;
    }

    /**
     * Sets the m_DominoTaken variable back to false
     */
    public void resetDrawDomino()
    {
        m_DominoTaken = false;
    }


    protected PlayerMove playMove(Board a_InBoard, Boolean a_InPrevPassed)
    {
        StringBuilder hintStrat = new StringBuilder();
        //getiing the list of all the possible moves given this state of the game
        Vector<PlayerMove> possibleMoves = m_PlayFunctions.possibleMoves (a_InBoard, a_InPrevPassed, this, m_DefaultSide, m_OtherSide);

        PlayerMove bestMove = possibleMoves.firstElement();

        return bestMove;
    }

    public void takeDomino(Domino a_InDomino)
    {
        // Set that a domino has been taken
        setM_DominoTaken();
        // add the domino to the hand
        m_CurrentHand.addTileToHand(a_InDomino);
    }

    public void clearHand()
    {
        m_CurrentHand.clearHand();
    }

    /**
     *
     * @param a_InBoard
     * @param a_InPrevPassed
     * @return
     */
    public boolean validMove(Board a_InBoard, boolean a_InPrevPassed)
    {
        // Go through the entire hand
        for(int dominoIndex = 0; dominoIndex < m_CurrentHand.getSize(); dominoIndex++)
        {
            // if a domino can be placed on the players regular side
            if(a_InBoard.validDomino(m_CurrentHand.getTilesAtIndex(dominoIndex), m_DefaultSide))
            {
                System.out.print(this.getPlayerName() + " has no valid moves on " + m_DefaultSide);
                return true;
            }

            // checks if a domino can be placed on the opposite side
            // this can only happen if the previous player has passed or the domino is a double
            if(a_InPrevPassed || m_CurrentHand.getTilesAtIndex(dominoIndex).isDouble())
            {
                if(a_InBoard.validDomino(m_CurrentHand.getTilesAtIndex(dominoIndex), m_OtherSide))
                {
                    System.out.print(this.getPlayerName() + " has no valid moves on " + m_OtherSide);
                    return true;
                }
            }
        }
        // Otherwise the player has no valid moves
        System.out.print(this.getPlayerName() + " has no valid moves");
        return false;
    }



}
