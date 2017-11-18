package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Player;

import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/12/2017                                          *
 ****************************************************************/

public class Round {

    private int m_TourScore;
    private int m_RoundNumber;
    private int m_EngineValue;
    private Vector<Player> m_GamePlayers;
    private Boneyard m_Boneyard;

    public Round(int a_TourScore, int a_RoundNumber)
    {
        this.m_TourScore = a_TourScore;
        this.m_RoundNumber = a_RoundNumber;
        this.m_EngineValue = getEngine();
        this.m_Boneyard = new Boneyard();
    }

    private int getEngine()
    {
        int a_count = 1;
        int a_engineCount = 6;
        while (a_count != m_RoundNumber)
        {
            if (a_engineCount == 0)
            {
                // reset the engine count
                a_engineCount = 6;
            }
            else
            {
                // reduce engine count by 1
                a_engineCount--;
            }
            // increment the count by 1
            a_count++;
        }
        return a_engineCount;
    }

    private void distributeTiles()
    {
        // distribute 8 tiles to each player
        for (short nextPlrIndex = 0; nextPlrIndex < 2; nextPlrIndex++)
        {
            for (int count = 0; count <= 7; count++)
            {
                m_GamePlayers.get(nextPlrIndex).getHand().addTileToHand(m_Boneyard.dealTile());
            }
        }
    }
}
