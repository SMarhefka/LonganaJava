package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Hand;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Player {

    private int m_Score;
    private boolean m_Turn;
    private int wins;
    boolean endTurn;
    boolean m_PassTurn;

    // The name of the player.
    protected String playerName;
    // The hand of the current player
    protected Hand m_CurrentHand;

    /**
     * Returns the name of the player.
     * @return a string containing the name of the player
     */
    public String getPlayerName()
    {
        return this.playerName;
    }

    /**
     *
     * @return
     */
    public Hand getHand()
    {
        return this.m_CurrentHand;
    }
}
