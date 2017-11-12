package com.svetlanamarhefka.model;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                             *
 ****************************************************************/

public class Tournament {
    // Integers containing the amount of wins for each player.
    private int humanWins;
    private int computerWins;

    // GUI components... later.

    /**
     * Default constructor, which initializes the amount of times each player won.
     */
    public Tournament()
    {
        this.humanWins = 0;
        this.computerWins = 0;
    }
}
