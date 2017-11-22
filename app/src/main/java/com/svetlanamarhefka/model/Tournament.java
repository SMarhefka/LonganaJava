package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                             *
 ****************************************************************/

public class Tournament {
    private String m_PlayerName;
    private int m_TourScore;
    private int m_RoundNumber;
    private Round m_Round;
    private Human m_Human;
    private Computer m_Computer;
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

    public Tournament()
    {
        m_Round = null;
        this.m_PlayerName = "";
        this.m_TourScore = 0;
        this.m_RoundNumber = 1;
    }

    public void setM_PlayerName(String a_InPlayerName)
    {
        m_PlayerName = a_InPlayerName;
    }

    public String getM_PlayerName()
    {
        return m_PlayerName;
    }

    public void setM_TourScore(int a_InTourScore)
    {
        m_TourScore = a_InTourScore;
    }

    public int getM_TourScore()
    {
        return m_TourScore;
    }

    public void setM_RoundNumber(int a_InRoundNum)
    {
        m_RoundNumber = a_InRoundNum;
    }

    public int getM_RoundNumber()
    {
        return m_RoundNumber;
    }

    public void createPlayers()
    {
        if(getM_PlayerName().length() == 0)
        {
            m_Human = new Human();
        }
        else
        {
            m_Human = new Human(getM_PlayerName());
        }
        m_Computer = new Computer();

    }

    public Round createNewRound()
    {
        m_Round = new Round(getM_TourScore(), getM_RoundNumber(), m_Computer, m_Human);
        m_RoundNumber++;
        return m_Round;
    }

    public Round getRound()
    {
        return m_Round;
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
