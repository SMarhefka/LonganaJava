package com.svetlanamarhefka.model;

/********************************************************************
 * Name:    Svetlana Marhefka										*
 * Project: Project 2 - Longana									    *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)	*
 * Date:    10/14/2017                                              *
 ********************************************************************/

/*
 * This is the domino class.  Its main purpose is to manipulate a domino tile
 */
public class Domino {

    private int m_leftSide;
    private int m_rightSide;

    /**
     * Overloaded constructor
     * @param a_leftSide  -->
     * @param a_rightSide -->
     * @return nothing
     */
    public Domino(int a_leftSide, int a_rightSide)
    {
        this.m_leftSide = a_leftSide;
        this.m_rightSide = a_rightSide;
    }

    /**
     * Reverses the ends of a domino tile
     * @return nothing
     */
    public void flipTile()
    {
        int temp = m_leftSide;
        m_leftSide = m_rightSide;
        m_rightSide = temp;
        //return new Domino(m_leftSide, m_rightSide);
    }

    /**
     * Gets the left side of the tile
     * @return m_leftSide
     */
    public int getM_leftSide() {
        return m_leftSide;
    }

    /**
     * Gets the right side of the tile
     * @return m_leftSide
     */
    public int getM_rightSide() {
        return m_rightSide;
    }

    /**
     * Boolean that checks to see if the tile is blank
     * @return true --> if the tile is 0-0
     *         false --> if the tile is not 0-0
     */
    public boolean isBlank() {
        return (m_leftSide == 0 && m_rightSide == 0);
    }

    /**
     * Boolean that checks to see if the tile is a double
     * @return true --> if the tile is a double
     *         false --> if the tile is not a double
     */
    public boolean isDouble() {
        return (m_leftSide == m_rightSide);
    }

    /**
     * Fuction that returns an integer with the total sum of the tile
     * @return returns the total sum of the tile
     */
    public int tileSum() {
        return m_leftSide + m_rightSide;
    }

    @Override
    public String toString()
    {
        String DominoString = "";
        DominoString = String.valueOf(m_leftSide) + "-" + String.valueOf(m_rightSide);
        return DominoString;
    }

    /**
     * Used for testing reasons
     */
    private void printDomino() {
        System.out.print("Domino: " + toString() + "\n");
    }

    /******************************CAUTION: TESTING AREA**********************************/
    /*
    public static void main(String[] args)
    {
        System.out.println("\n" +
                "----------------------------------Domino Test-------------------------------");
        Domino t_DominoTest1 = new Domino(1,6);
        t_DominoTest1.printDomino();
        t_DominoTest1.flipTile();
        t_DominoTest1.printDomino();

        Domino t_DominoTest2 = new Domino(6, 6);
        t_DominoTest2.printDomino();
        System.out.println("Is Double: " + t_DominoTest2.isDouble());
    }
    */
}
