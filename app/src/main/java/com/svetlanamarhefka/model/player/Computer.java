package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Side;

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
}
