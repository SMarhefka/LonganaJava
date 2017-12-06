package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public void setPlayerScore(Class player, int a_InScore){
        if(player == Human.class)
        {
            m_Human.setM_PlayerScore(a_InScore);
        }
        else
        {
            m_Computer.setM_PlayerScore(a_InScore);
        }
    }

    public boolean isOver()
    {
        return m_Human.getM_PlayerScore() > m_TourScore || m_Computer.getM_PlayerScore() > m_TourScore;
    }

    public String getGameWinnerAndScore() {
        StringBuilder t_EndMessage = new StringBuilder();
        //holds sum of all tiles for human
        int t_HumanScore = m_Human.getM_PlayerScore();
        //holds sum of all tiles for computer
        int t_CompScore = m_Computer.getM_PlayerScore();

        t_EndMessage.append("Computer Score: ");
        t_EndMessage.append(t_CompScore);
        t_EndMessage.append("\n");
        t_EndMessage.append("Human Score: ");
        t_EndMessage.append(t_HumanScore);
        t_EndMessage.append("\n");

        //if computer has more point, computer wins
        if (t_CompScore > t_HumanScore) {
            t_EndMessage.append("Computer has won with ");
            t_EndMessage.append(t_CompScore);
            t_EndMessage.append(" points");
        }
        //otherwise we can assume that the human has won
        else {
            t_EndMessage.append("Human has won with ");
            t_EndMessage.append(t_HumanScore);
            t_EndMessage.append(" points");
        }
        return t_EndMessage.toString();
    }


    public Round loadGame(InputStream a_InputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(a_InputStream));
        try {
            String t_InLine = reader.readLine();
            // Split the read in line by the ':'
            String[] lineData = t_InLine.split(":");
            this.m_TourScore = Integer.parseInt(lineData[1].trim());

            t_InLine = reader.readLine();
            lineData = t_InLine.split(":");
            m_RoundNumber = Integer.parseInt(lineData[1].trim());

            createPlayers();

            m_Round = new Round(m_TourScore, m_RoundNumber, m_Computer, m_Human);



            m_Round.LoadRound(reader);
            m_RoundNumber++;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
