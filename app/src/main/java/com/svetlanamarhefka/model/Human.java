package com.svetlanamarhefka.model;

/**
 * Created by Svetlana Marhefka on 11/6/2017.
 */

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
