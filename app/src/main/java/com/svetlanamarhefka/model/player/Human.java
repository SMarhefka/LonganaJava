package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Side;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/06/2017                                          *
 ****************************************************************/

public class Human extends Player {
    /**
     * Default constructor.
     */
    public Human()
    {
        // Give the computer its name.
        this.playerName = "Human";
        // Set the default side to play on
        m_DefaultSide = Side.LEFT;
        // Set the other side as left
        m_OtherSide = Side.RIGHT;
    }

    /**
     * Default constructor.
     */
    public Human(String a_UserName)
    {
        // Give the the human a name.
        this.playerName = a_UserName;
        // Set the default side to play on
        m_DefaultSide = Side.LEFT;
        // Set the other side as left
        m_OtherSide = Side.RIGHT;
    }
}
