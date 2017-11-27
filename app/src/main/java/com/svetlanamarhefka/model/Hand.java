package com.svetlanamarhefka.model;

import java.io.Serializable;
import java.util.Vector;
/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Hand implements Serializable {

    /* Going to try using a vector because it is thread safe and it
    *  was in my original code
    */
    private Vector<Domino> m_PlayerTiles;
    private static Vector<Domino> m_SortedHand = new Vector<Domino>();
    private int m_EngineIndex;

    /**
     * Default constructor
     */
    public Hand()
    {
        m_PlayerTiles = new Vector<Domino>();
        m_EngineIndex = 0;
    }

    /**
     * Overload consturctor
     * @param a_UserHand --> the handVector that will be read in from a file
     */
    public Hand(Vector<Domino> a_UserHand)
    {
        m_PlayerTiles = new Vector<Domino>();
        m_PlayerTiles = a_UserHand;
    }

    public int getDomIndex(Domino a_InDomino)
    {
        // go through the vector and find the tile that is going to be deleted.
        for (int itemVal = 0; itemVal != m_PlayerTiles.size(); itemVal++)
        {
            Domino t_Domino = m_PlayerTiles.get(itemVal);
            if (t_Domino.equals(a_InDomino))
            {
                System.out.print("Tiles the same: " + t_Domino.equals(a_InDomino) + "\n");
                return itemVal;
            }
        }
        return -1;
    }

    /**
     * Adds a tile to the players hand
     * @param a_InTile --> the tile that will be added into the player's hand
     */
    public void addTileToHand(Domino a_InTile)
    {
        // pushes back that tile to the end of the vector playerTiles
        m_PlayerTiles.add(a_InTile);
    }

    // this function will be used when a player places their tile on the board
    public void removeTile(int a_InTileIndex)
    //public void removeTile(Domino a_InTile)
    {
        /*
        // go through the vector and find the tile that is going to be deleted.
        for (int itemVal = 0; itemVal != m_PlayerTiles.size(); itemVal++)
        {
            // cout << "item: " << item->getLeftSide() << "\n";
            // cout << "tile: " << a_tileToRemove.getLeftSide() << "\n";

            int tempLeft = m_PlayerTiles.get(itemVal).getM_leftSide();
            int tempRight = m_PlayerTiles.get(itemVal).getM_rightSide();

            if ((tempLeft == a_InTile.getM_leftSide()) && (tempRight == a_InTile.getM_rightSide()))
            {
                m_PlayerTiles.remove(itemVal);
                break;
            }
            else if ((tempLeft == a_InTile.getM_rightSide()) && (tempRight == a_InTile.getM_leftSide()))
            {
                m_PlayerTiles.remove(itemVal);
                break;
            }
        }
        */
        m_PlayerTiles.removeElementAt(a_InTileIndex);
    }

    /**
     *
     * @return
     */
    public int getM_EngineIndex()
    {
        return m_EngineIndex;
    }

    /**
     * NOT ENTIRELY SURE IF WE WILL NEED THIS
     * @param a_InIndex --> The index value at which to retrieve a tile
     * @return Domino --> returns a Domino tile object
     */
    public Domino getTilesAtIndex(int a_InIndex)
    {
        return m_PlayerTiles.get(a_InIndex);
    }

    /**
     * Checks to see if the hand has the engine
     * @param a_InEngine --> the value of the which is calculated at the beginning of a round
     * @return true --> returns true if the hand contains the engine
     *         false --> returns false if the hand does not contain the engine
     */
    public boolean hasEngine(int a_InEngine)
    {
        for (int itemCount = 0; itemCount != m_PlayerTiles.size(); itemCount++)
        {
            if (((m_PlayerTiles.get(itemCount).isDouble()) && ((m_PlayerTiles.get(itemCount).getM_leftSide() == a_InEngine))))
            {
                // Provides the index number that the engine is stored in
                m_EngineIndex = itemCount;
                return true;
            }
        }
        return false;
    }

    public int getSize()
    {
        return m_PlayerTiles.size();
    }

    public boolean isEmpty()
    {
        return m_PlayerTiles.isEmpty();
    }

    public int getHandTotal()
    {
        int l_HandTotal = 0;
        for(Domino l_Domino: m_PlayerTiles)
        {
            l_HandTotal += l_Domino.tileSum();
        }
        return l_HandTotal;
    }

    public Vector<Domino> getM_PlayerTiles()
    {
        return m_PlayerTiles;
    }

    public void clearHand()
    {
        m_PlayerTiles.removeAllElements();
    }

    public void printHand(int a_PrintType)
    {
        Vector<Domino> tempTiles;
        switch (a_PrintType)
        {
            // If we want the boneyard to be sorted
            case 1:
                // Sort the hand
                System.out.print("Hand Sorted\n");
                tempTiles = m_SortedHand;
                break;
            default:
                System.out.print("Hand Unsorted\n");
                tempTiles = m_PlayerTiles;
        }

        String fullString = "";

        for(int itemVal = 0; itemVal < tempTiles.size(); itemVal++)
        {
            //m_UnusedTiles.get(itemVal).printDomino();
            fullString = fullString.concat(tempTiles.get(itemVal).toString() + " ");
        }
        System.out.println(fullString + "\n");
    }


    /******************************CAUTION: TESTING AREA**********************************/
    /**
     * Main method to test the methods in hand
     * @param args --> any arguement parameters
     */
    /*
    public static void main(String[] args)
    {
        System.out.println("\n" +
                "----------------------------------Hand Test-------------------------------");
        // Create an empty hand
        Hand t_HandTest = new Hand();
        Boneyard t_BoneyardTest = new Boneyard();

        t_BoneyardTest.printBoneyard(0);

        t_HandTest.addTileToHand(t_BoneyardTest.dealTile());
        t_HandTest.printHand(0);

        // Print out the boneyard
        t_BoneyardTest.printBoneyard(0);
        System.out.println("Boneyard Size: " + t_BoneyardTest.getSize() + "\n");
        // add another 7 dominos to the test hand
        for(int count = 1; count < 8; count++)
        {
            t_HandTest.addTileToHand(t_BoneyardTest.dealTile());
        }

        t_HandTest.printHand(0);
        t_BoneyardTest.printBoneyard(0);

        m_SortedHand = m_PlayerTiles;
        t_BoneyardTest.sortItems(m_SortedHand);
        t_HandTest.printHand(1);
        t_BoneyardTest.printBoneyard(1);

        System.out.println("Has Engine: " + t_HandTest.hasEngine(3));
    }
    */
}
