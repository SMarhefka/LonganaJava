package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;

import java.io.Serializable;
import java.util.Vector;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/12/2017                                          *
 ****************************************************************/

public class Round implements Serializable {

    private int m_TourScore;
    private int m_RoundNumber;
    private int m_PassCount;
    private int m_EngineValue;

    private Boneyard m_Boneyard;
    private Board m_Board;
    private Human m_Human;
    private Computer m_Computer;

    private boolean m_PrevPass;
    private boolean m_ComputerTurn;


    public Round()
    {
        this.m_TourScore = 150;
        this.m_RoundNumber = 1;
        this.m_EngineValue = getEngine();
        this.m_Boneyard = new Boneyard();
    }

    public Round(int a_InTourScore, int a_InRoundNum, Computer a_InComputer, Human a_InHuman)
    {
        m_TourScore = a_InTourScore;
        m_RoundNumber = a_InRoundNum;
        m_PassCount = 0;
        m_EngineValue = getEngine();

        m_Boneyard = new Boneyard();
        m_Board = new Board();
        m_Computer = a_InComputer;
        m_Human = a_InHuman;

        m_PrevPass = false;
    }

    public int getM_TourScore()
    {
        return m_TourScore;
    }

    public int getM_RoundNumber()
    {
        return m_RoundNumber;
    }

    public int getM_EngineValue()
    {
        return m_EngineValue;
    }

    public String playerName()
    {
        return m_Human.getPlayerName();
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

    public Vector<Domino> getBoard()
    {
        return m_Board.getM_BoardVector();
    }

    public Vector<Domino> getBoneYard()
    {
        return m_Boneyard.getM_UnusedTiles();
    }

    public Vector<Domino> getComHand()
    {
        return m_Computer.getHand().getM_PlayerTiles();
        /**
         Vector<Domino> tempVector = new Vector<Domino>();
         for(int count = 0; count < m_Computer.getHand().getSize(); count++)
         {
         tempVector.add(m_Computer.getHand().getTilesAtIndex(count));
         }
         return tempVector;
         */
    }

    public Vector<Domino> getHumanHand()
    {
        return m_Human.getHand().getM_PlayerTiles();
    }

    public void distributeTiles()
    {
        // distribute 8 tiles to each player
        for (int count = 0; count <= 7; count++)
        {
            // Give a tile to the player
            this.m_Human.getHand().addTileToHand(m_Boneyard.dealTile());
            // Give a tile to the computer
            this.m_Computer.getHand().addTileToHand(m_Boneyard.dealTile());
        }
    }

    public String firstPlayer()
    {
        StringBuilder t_Log = new StringBuilder();
        // While nobody has the engine
        while (!engineInHand(t_Log))
        {
            // Give a tile to the player
            this.m_Human.getHand().addTileToHand(m_Boneyard.dealTile());
            t_Log.append("Human drew: " + m_Human.getHand().getTilesAtIndex(
                    m_Human.getHand().getSize() - 1) + "\n");
            // Give a tile to the computer
            this.m_Computer.getHand().addTileToHand(m_Boneyard.dealTile());
            t_Log.append("Computer drew: " + m_Computer.getHand().getTilesAtIndex(
                    m_Computer.getHand().getSize() - 1) + "\n");
        }
        return t_Log.toString();
    }

    private boolean engineInHand(StringBuilder a_InLog)
    {
        // checks to see if the human has the engine
        if(m_Human.getHand().hasEngine(m_EngineValue))
        {
            System.out.print("Round: " + m_RoundNumber + " " + m_Human.getPlayerName() +
                    " has the engine!\n");
            a_InLog.append(m_Human.getPlayerName() + " has the engine ( " +
                    m_EngineValue + "-" + m_EngineValue + " )\n");
            m_Board.setM_EngineVal(getM_EngineValue());
            m_ComputerTurn = false;
            return true;
        }
        // check if the computer has the engine
        else if(m_Computer.getHand().hasEngine(m_EngineValue))
        {
            System.out.print("Round: " + m_RoundNumber + " Computer has the engine!\n");
            a_InLog.append(m_Human.getPlayerName() + " has the engine ( " +
                    m_EngineValue + "-" + m_EngineValue + " )\n");

            m_Board.setM_EngineVal(getM_EngineValue());

            m_Computer.playDomino(m_Computer.getHand().getM_EngineIndex(), m_Board,Side.RIGHT);
            m_ComputerTurn = false;
            return true;
        }
        return false;
    }

    //
    public String setHumanTurn()
    {
        if (m_ComputerTurn)
        {
            // set the computer turn to false
            m_ComputerTurn = false;
        }
        // otherwise don't return anything
        return null;
    }

    private boolean canHumanPass()
    {
        if(m_Human.validMove(m_Board, m_PrevPass))
        {
            return false;
        }
        if(m_Human.isM_DominoTaken())
        {
            return false;
        }
        m_Human.unsetDominoTaken();
        m_ComputerTurn = true;
        return true;
    }

    public Domino humanDrawTile()
    {
        // if the human doesn't have any valid moves
        if(m_Human.validMove(m_Board, m_PrevPass))
        {
            // return nothing
            return null;
        }
        // if the human has already taken a tile
        if(m_Human.isM_DominoTaken())
        {
            // return nothing
            return null;
        }
        System.out.print("Player has drawn: " + m_Human.isM_DominoTaken());
        // if the Boneyard is empty
        if(m_Boneyard.isEmpty())
        {
            m_Human.setM_DominoTaken();
            return null;
        }

        Domino t_Domino = m_Boneyard.dealTile();
        m_Human.takeDomino(t_Domino);
        return t_Domino;
    }

    private void passTurn()
    {
        m_PrevPass = true;
        m_PassCount++;
    }

    private void resetPass()
    {
        m_PrevPass = false;
        m_PassCount = 0;
    }

    /**
     * To check if the round is over
     * @return true is round ended, else false
     */
    public boolean roundOver() {
        return (m_Human.getHand().isEmpty() || m_Computer.getHand().isEmpty()) || (m_PassCount > 2);
    }

    /**
     * To find the round winner and update the scores
     * @return String including the round winner and respective player scores
     */
    public String getRoundWinnerAndScore() {
        StringBuilder scores = new StringBuilder();
        //holds sum of all tiles for human
        int l_HumanTotal = m_Human.getHand().getHandTotal();
        //holds sum of all tiles for computer
        int l_CompTotal = m_Computer.getHand().getHandTotal();
        scores.append("Computer Score: ").append(l_HumanTotal)
                .append("\nHuman Score: ").append(l_CompTotal).append("\n");

        int score = 0;
        //if human emptied the hand , he wins
        if (m_Human.getHand().isEmpty()) {
            scores.append("Human");
            m_Human.addToScore(l_CompTotal);
            score = l_CompTotal;
        }
        //if computer emptied the hand, computer wins
        else if (m_Computer.getHand().isEmpty()) {
            scores.append("Computer");
            m_Computer.addToScore(l_HumanTotal);
            score = l_HumanTotal;
        }
        //if human has less sum than computer, human wins
        else if (l_HumanTotal < l_CompTotal) {
            scores.append("Human");
            m_Human.addToScore(l_CompTotal);
            score = l_CompTotal;
        }
        //if computer has less sum, computer wins
        else if (l_HumanTotal > l_CompTotal) {
            scores.append("Computer");
            m_Computer.addToScore(l_HumanTotal);
            score = l_HumanTotal;
        }
        //if equal, its a draw
        else {
            scores.append("The round ended with a draw!");
            return scores.toString();
        }
        return scores.append(" won this round with a score of ").append(score).toString();
    }
}
