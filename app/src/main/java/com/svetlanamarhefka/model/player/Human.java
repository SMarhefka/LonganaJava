package com.svetlanamarhefka.model.player;

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
    }

    /**
     * Default constructor.
     */
    public Human(String a_UserName)
    {
        // Give the the human a name.
        this.playerName = a_UserName;
    }
}
