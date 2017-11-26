package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Hand;

import java.io.Serializable;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Player implements Serializable {

    private int m_PlayerScore;
    private boolean m_IsTurn;
    private boolean m_PassTurn;
    // The hand of the current player
    private Hand m_CurrentHand;
    // The name of the player.
    protected String playerName;


    public Player()
    {
        m_CurrentHand = new Hand();
        m_IsTurn = false;
        m_PassTurn = false;
        m_PlayerScore = 0;
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
    public Hand getHand()
    {
        return m_CurrentHand;
    }

    public void addToScore(int a_InScore)
    {
        m_PlayerScore += a_InScore;
    }


}
