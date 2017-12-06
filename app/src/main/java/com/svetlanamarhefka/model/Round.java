package com.svetlanamarhefka.model;

import com.svetlanamarhefka.model.player.Computer;
import com.svetlanamarhefka.model.player.Human;
import com.svetlanamarhefka.util.PlayerMove;
import com.svetlanamarhefka.util.Side;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
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
        // Method is called for testing reasons
        //m_Boneyard.printBoneyard(1);
        return m_Boneyard.getM_UnusedTiles();
    }

    public Vector<Domino> getComHand()
    {
        Vector<Domino> t_ComHand;
        m_Computer.getHand().printHand(0);
        m_Computer.getHand().resetHand();
        //m_Computer.getHand().sortForDisplay(m_Computer.getHand().getM_PlayerTiles());
        m_Computer.getHand().printHand(0);
        //m_Computer.getHand().printHand(0);
        return m_Computer.getHand().getM_PlayerTiles();
    }

    public Vector<Domino> getHumanHand()
    {
        m_Human.getHand().printHand(0);
        m_Human.getHand().resetHand();
        m_Human.getHand().printHand(0);
        return m_Human.getHand().getM_PlayerTiles();
    }

    public boolean getPrevPass()
    {
        return m_PrevPass;
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
        // If the first player has been chosen then we don't need to do anything
        if(m_Board.isM_EngineSet())
        {
            return null;
        }
        // Create a new String Builder so that we can describe step by step what is going on
        StringBuilder t_MoveDesc = new StringBuilder();
        // Have the board know what the correct engine value should be
        m_Board.setM_EngineValue(this.m_EngineValue);
        // While nobody has the engine
        while (!engineInHand(t_MoveDesc))
        {
            // Give a tile to the player
            this.m_Human.getHand().addTileToHand(m_Boneyard.dealTile());
            //
            t_MoveDesc.append("Human drew: " + m_Human.getHand().getTilesAtIndex(
                    m_Human.getHand().getSize() - 1) + "\n");
            // Give a tile to the computer
            this.m_Computer.getHand().addTileToHand(m_Boneyard.dealTile());
            t_MoveDesc.append("Computer drew: " + m_Computer.getHand().getTilesAtIndex(
                    m_Computer.getHand().getSize() - 1) + "\n");
        }
        return t_MoveDesc.toString();
    }

    /**
     *
     * @param a_InMoveDesc The stringbuilder that is appending text
     * @return
     */
    private boolean engineInHand(StringBuilder a_InMoveDesc)
    {
        // checks to see if the human has the engine
        if(m_Human.getHand().hasEngine(m_EngineValue))
        {
            // Print out to the console for testing reasons
            System.out.print("In Round: " + m_RoundNumber + " " + m_Human.getPlayerName() +
                    " has the engine!\n");
            // Append it to
            a_InMoveDesc.append("\n");
            a_InMoveDesc.append(m_Human.getPlayerName().toUpperCase() + " has the engine ( " +
                    m_EngineValue + "-" + m_EngineValue + " )\n");

            // Sets the computer turn to false so it becomes the humans turn to play
            m_ComputerTurn = false;

            return true;
        }
        // check if the computer has the engine
        else if(m_Computer.getHand().hasEngine(m_EngineValue))
        {
            System.out.print("In Round: " + m_RoundNumber + " Computer has the engine!\n");
            a_InMoveDesc.append("\n");
            a_InMoveDesc.append(m_Computer.getPlayerName() + " has the engine ( " +
                    m_EngineValue + "-" + m_EngineValue + " )");
            // If the computer has the engine it will automatically play it
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

    /**
     *
     * @return
     */
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
            m_Computer.setM_CurrentHand(m_Computer.getHand());
            // If the computer still can't make a move
            if (m_Computer.play(m_Board, m_PrevPass) == false)
            {
                // pass the turn
                passTurn();
                return "Computer still can't do anything";
            }

            resetPass();
            m_Computer.resetDrawDomino();
            return m_Computer.getBestMove().toString();
        }
        resetPass();
        return m_Computer.getBestMove().toString();
    }

    /**
     *
     * @param a_InDomino
     * @param a_InSide
     * @return
     */
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

    /**
     *
     * @return
     */
    public boolean canHumanPass()
    {
        if(m_Human.validMove(m_Board, m_PrevPass))
        {
            return false;
        }
        if(!m_Human.isM_DominoTaken())
        {
            System.out.print(m_Human.isM_DominoTaken());
            return false;
        }
        System.out.print("Player has drawn: " + m_Human.isM_DominoTaken());
        m_Human.resetDrawDomino();
        passTurn();
        m_ComputerTurn = true;
        return true;
    }

    /**
     *
     * @return
     */
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

    public String getHelp()
    {
        PlayerMove bestMove = m_Human.askForHelp(m_Board, m_PrevPass);
        // if it is null
        if (bestMove == null)
        {
            return null;
        }
        else
        {
            StringBuilder t_Suggestion = new StringBuilder();
            t_Suggestion.append("Best Play is: ");
            t_Suggestion.append(m_Human.askForHelp(m_Board, m_PrevPass).toString());
            t_Suggestion.append("\n");
            t_Suggestion.append("Strategy: ");
            t_Suggestion.append("\n");
            t_Suggestion.append(m_Human.getHelp());

            return  t_Suggestion.toString();
        }
    }

    /**
     *
     */
    private void passTurn()
    {
        m_PrevPass = true;
        m_PassCount++;
    }


    /**
     * resets
     */
    private void resetPass()
    {
        m_PrevPass = false;
        m_PassCount = 0;
    }

    public void LoadRound(BufferedReader reader)
    {
        try
        {
            // Read in blank lines
            reader.readLine();
            reader.readLine();

            //computer hand
            String line = reader.readLine();
            String[] lineData = line.split(":");
            Vector<Domino> t_ComHand = getDominoesFromFile(lineData[1].toString());
            m_Computer.setM_CurrentHand(new Hand(t_ComHand));


            //computer score
            line = reader.readLine();
            lineData = line.split(":");
            int t_ComScore = Integer.parseInt(lineData[1].trim());
            m_Computer.setM_PlayerScore(t_ComScore);

            // Read in blank lines
            reader.readLine();
            reader.readLine();

            //human hand
            line = reader.readLine();
            lineData = line.split(":");
            Vector<Domino> t_HumanHand = getDominoesFromFile(lineData[1]);
            m_Human.setM_CurrentHand(new Hand(t_HumanHand));

            //human score
            line = reader.readLine();
            lineData = line.split(":");
            int t_HumanScore = Integer.parseInt(lineData[1].trim());
            m_Human.setM_PlayerScore(t_HumanScore);

            // Read in blank lines
            reader.readLine();
            reader.readLine();

            line = reader.readLine().trim();
            line = line.substring(1, line.length() - 2);
            Vector<Domino> t_BoardVector = getDominoesFromFile(line);
            m_Board.setM_BoardVector(t_BoardVector);

            // Read in blank lines
            reader.readLine();
            reader.readLine();

            line = reader.readLine().trim();
            Vector<Domino> t_BoneYardVector = getDominoesFromFile(line);
            Collections.reverse(t_BoneYardVector);
            m_Boneyard.setM_TilesBoneYard(t_BoneYardVector);

            reader.readLine();

            line = reader.readLine();
            lineData = line.split(":");
            lineData[1] = lineData[1].trim().toUpperCase();
            if (lineData[1].equals("YES"))
            {
                m_PrevPass = true;
            }
            else if (lineData[1].equals("NO"))
            {
                m_PrevPass = false;
            }

            reader.readLine();

            line = reader.readLine();
            lineData = line.split(":");
            lineData[1] = lineData[1].trim().toUpperCase();
            if (lineData[1].equals("HUMAN")) m_ComputerTurn = false;
            else if (lineData[1].equals("COMPUTER")) m_ComputerTurn = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vector<Domino> getDominoesFromFile(String a_InString) {
        if (a_InString.trim().equals(""))
        {
            return new Vector<>();
        }

        String[] dominoInString = a_InString.trim().split(" ");
        // Create a new vector of dominoes
        Vector<Domino> t_NewDominoes = new Vector<>();
        for (String t_InString : dominoInString)
        {
            // Trim the string
            t_InString = t_InString.trim();
            t_NewDominoes.add(new Domino(Character.getNumericValue(t_InString.charAt(0)), Character.getNumericValue(t_InString.charAt(2))));
        }
        return t_NewDominoes;
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
        StringBuilder t_RoundEnd = new StringBuilder();
        //holds sum of all tiles for human
        int l_HumanTotal = m_Human.getHand().getHandTotal();
        //holds sum of all tiles for computer
        int l_CompTotal = m_Computer.getHand().getHandTotal();
        t_RoundEnd.append("Computer Score: ");
        t_RoundEnd.append(l_CompTotal);
        t_RoundEnd.append("\n");
        t_RoundEnd.append("Human Score: ");
        t_RoundEnd.append(l_HumanTotal).append("\n");

        int t_Score = 0;
        //if human emptied the hand , he wins
        if (m_Human.getHand().isEmpty()) {
            t_RoundEnd.append("Human");
            m_Human.addToScore(l_CompTotal);
            t_Score = l_CompTotal;
        }
        //if computer emptied the hand, computer wins
        else if (m_Computer.getHand().isEmpty()) {
            t_RoundEnd.append("Computer");
            m_Computer.addToScore(l_HumanTotal);
            t_Score = l_HumanTotal;
        }
        //if human has less sum than computer, human wins
        else if (l_HumanTotal < l_CompTotal) {
            t_RoundEnd.append("Human");
            m_Human.addToScore(l_CompTotal);
            t_Score = l_CompTotal;
        }
        //if computer has less sum, computer wins
        else if (l_HumanTotal > l_CompTotal) {
            t_RoundEnd.append("Computer");
            m_Computer.addToScore(l_HumanTotal);
            t_Score = l_HumanTotal;
        }
        //if equal, its a draw
        else {
            t_RoundEnd.append("The round ended with a draw!");
            return t_RoundEnd.toString();
        }
        return t_RoundEnd.append(" won this round with a score of ").append(t_Score).toString();
    }

    public String gameInformation()
    {
        // Use stringbuilder so that we can append multiple things
        StringBuilder t_SaveText = new StringBuilder();

        t_SaveText.append("Tournament Score: ");
        t_SaveText.append(m_TourScore);
        t_SaveText.append("\n");

        t_SaveText.append("Round No.: ");
        t_SaveText.append(m_RoundNumber);
        t_SaveText.append("\n\n");

        t_SaveText.append("Computer: ");
        t_SaveText.append("\n");

        t_SaveText.append("\t");
        t_SaveText.append(m_Computer.saveHand());
        t_SaveText.append("\n");
        t_SaveText.append("\t");
        t_SaveText.append("Score: ");
        t_SaveText.append(m_Computer.getM_PlayerScore());
        t_SaveText.append("\n").append("\n");

        t_SaveText.append(m_Human.getPlayerName()).append(": ");
        t_SaveText.append("\n");
        t_SaveText.append("\t");
        t_SaveText.append(m_Human.saveHand());
        t_SaveText.append("\n");
        t_SaveText.append("\t");
        t_SaveText.append("Score: ");
        t_SaveText.append(m_Human.getM_PlayerScore());
        t_SaveText.append("\n").append("\n");

        t_SaveText.append(m_Board.toString());
        t_SaveText.append("\n").append("\n");

        t_SaveText.append(m_Boneyard.toString());
        t_SaveText.append("\n").append("\n");

        if(m_Board.getM_BoardVector().isEmpty())
        {
            t_SaveText.append("Previous Player Passed: ");
            t_SaveText.append("\n\n");
            t_SaveText.append("Next Player: ");
            t_SaveText.append("\n");
        }
        else
        {
            t_SaveText.append("Previous Player Passed: ");
            if(m_PrevPass)
            {
                t_SaveText.append("Yes");
            }
            else
            {
                t_SaveText.append("No");
            }
            t_SaveText.append("\n\n");

            t_SaveText.append("Next Player: ");
            if(m_ComputerTurn)
            {
                t_SaveText.append("Computer");
            }
            else
            {
                t_SaveText.append(m_Human.getPlayerName());
            }
            t_SaveText.append("\n");
        }
        return t_SaveText.toString();
    }

    public boolean saveGame(File a_InFile, String a_InGameInfo)
    {
        StringBuilder t_GameInfo = new StringBuilder();
        t_GameInfo.append(a_InGameInfo);

        try
        {
            FileOutputStream t_OutputStream = new FileOutputStream(a_InFile);
            t_OutputStream.write(t_GameInfo.toString().getBytes());
            t_OutputStream.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
