package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;
import com.svetlanamarhefka.model.player.Side;

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

    /**
     * This is an overloaded Round
     * @param a_InTourScore
     * @param a_InRoundNum
     * @param a_InComputer
     * @param a_InHuman
     */
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

    /**
     * Gets the Tournament Score
     * @return
     */
    public int getM_TourScore()
    {
        return m_TourScore;
    }

    public int getM_RoundNumber()
    {
        return m_RoundNumber;
    }

    public String getPlayerName()
    {
        return m_Human.getPlayerName();
    }

    /**
     * To get the score for a particular player
     * @param a_InPlayer Class for the player to get the score for
     * @return score for the player
     */
    public int getPlayerScore(Class a_InPlayer) {
        // If the player is the computer
        if (a_InPlayer == Computer.class)
        {
            return m_Computer.getM_PlayerScore();
        }
        // Otherwise get the score associated with the computer
        else if (a_InPlayer == Human.class)
        {
            return m_Human.getM_PlayerScore();
        }
        return -1;
    }

    /**
     * Finds the engine that is required to start the round
     * @return int t_EngineCount --> temporary engine value which
     * will be used for the round.
     */
    private int getEngine()
    {
        int t_Count = 1;
        int t_EngineCount = 6;
        while (t_Count != m_RoundNumber)
        {
            if (t_EngineCount == 0)
            {
                // reset the engine count
                t_EngineCount = 6;
            }
            else
            {
                // reduce engine count by 1
                t_EngineCount--;
            }
            // increment the count by 1
            t_Count++;
        }
        return t_EngineCount;
    }

    /**
     *
     * @return
     */
    public Vector<Domino> getBoard()
    {
        m_Board.printBoard();
        return m_Board.getM_BoardVector();
    }

    /**
     * Gets the boneyard
     * @return m_Boneyard.getM_UnusedTiles() --> the tiles in the boneyard
     */
    public Vector<Domino> getBoneYard()
    {
        // Method is called for testing reasons
        m_Boneyard.printBoneyard(0);
        return m_Boneyard.getM_UnusedTiles();
    }

    public Vector<Domino> getComHand()
    {
        m_Computer.getHand().printHand(0);
        return m_Computer.getHand().getM_PlayerTiles();
        /*
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
        m_Human.getHand().printHand(0);
        return m_Human.getHand().getM_PlayerTiles();
    }


    /**
     * Distributes 8 tiles to both players
     */
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

    /**
     * Determines who the first player is
     * @return
     */
    public String firstPlayer()
    {
        if(m_Board.isM_EngineSet())
        {
            return null;
        }

        StringBuilder t_Log = new StringBuilder();
        m_Board.setM_EngineValue(this.m_EngineValue);
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
        t_Log.append("------------------------------------");
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

            m_ComputerTurn = false;
            return true;
        }
        // check if the computer has the engine
        else if(m_Computer.getHand().hasEngine(m_EngineValue))
        {
            System.out.print("Round: " + m_RoundNumber + " Computer has the engine!\n");
            a_InLog.append(m_Computer.getPlayerName() + " has the engine ( " +
                    m_EngineValue + "-" + m_EngineValue + " )\n");
            m_Computer.playDomino(m_Computer.getHand().getM_EngineIndex(), m_Board, Side.RIGHT);
            m_ComputerTurn = false;
            return true;
        }
        return false;
    }



    /**
     *
     * @return
     */
    public String setHumanTurn()
    {
        if (m_ComputerTurn)
        {
            // set the computer turn to false
            m_ComputerTurn = false;
            // Play the computer turns
            return computerPlay();
        }
        // otherwise don't return anything
        return null;
    }

    private String computerPlay()
    {
        if(!m_Computer.play(m_Board, m_PrevPass))
        {
            System.out.print("Computer doesn't have a valid move");
            // if the boneyard is empty then the computer has to pass it's turn
            if(m_Boneyard.isEmpty())
            {
                passTurn();
                return "Stock is empty and there are no valid moves!";
            }

            // Get a domino from the boneyard
            Domino t_Domino = m_Boneyard.dealTile();
            // Place it into the computer hand
            m_Computer.takeDomino(t_Domino);
            // If the computer still can't make a move
            if (!m_Computer.play(m_Board, m_PrevPass))
            {
                // pass the turn
                passTurn();
                return "Computer still can't do anything";
            }

            resetPass();
            m_Computer.resetDrawDomino();
            return "Computer made it's move";
        }
        resetPass();
        return "Computer is done...now for the human move";
    }

    public String humanPlay(Domino a_InDomino, Side a_InSide)
    {
        if(!m_Human.play(m_Human.getHand().getDomIndex(a_InDomino), m_Board, a_InSide, m_PrevPass))
        {
            return "Error: Invalid Move!";
        }
        resetPass();
        m_Human.resetDrawDomino();
        m_ComputerTurn = true;
        // return nothing because the play was valid
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
        m_Human.resetDrawDomino();
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
            // Set the domino taken boolean
            m_Human.setM_DominoTaken();
            // Return nothing
            return null;
        }
        // Get a domino from the boneyard
        Domino t_Domino = m_Boneyard.dealTile();
        // Place it into the human hand
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
