package com.svetlanamarhefka.model;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                             *
 ****************************************************************/

public class Tournament {
    // Integers containing the amount of wins for each player.
    private int m_TourScore;
    private int m_RoundNumber;

    // GUI components... later.

    /**
    * MAY NOT NEED TO USE THIS...
    * Default constructor
    *
    public Tournament()
    {
        this.m_TourScore = 0;
        this.m_RoundNumber = 0;
    }
    */

    public Tournament(int a_TourScore, int a_RoundNumber)
    {
        this.m_TourScore = a_TourScore;
        this.m_RoundNumber = a_RoundNumber;
    }

    public int getRound()
    {
        return m_RoundNumber;
    }

    public int getTourScore()
    {
        return m_TourScore;
    }

    /*
    public void main(String args[])
    {
        System.out.println("\n" +
                "----------------------------------Tournament Test-------------------------------");
        Tournament tourTest = new Tournament(150, 1);
        System.out.println("Tournament Score: " + String.valueOf(getTourScore()));
        System.out.println("Round Number: " + String.valueOf(getRound()));
    }
    */
}
