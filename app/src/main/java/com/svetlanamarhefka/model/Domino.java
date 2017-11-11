/********************************************************************
 * Name:    Svetlana Marhefka										*
 * Project: Project 2 - Longana									    *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)	*
 * Date:    10/14/2017                                              *
 ********************************************************************/
package com.svetlanamarhefka.model;

/**
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
    public Domino(int a_leftSide, int a_rightSide) {
        m_leftSide = a_leftSide;
        m_rightSide = a_rightSide;
    }

    /**
     * Reverses the ends of a domino tile
     * @param
     * @return nothing
     */
    public void flip() {
        int temp = m_leftSide;
        m_leftSide = m_rightSide;
        m_rightSide = temp;
    }

    /**
     * Gets the left side of the tile
     * @param @none
     * @return m_leftSide
     */
    public int getLeft() {
        return m_leftSide;
    }

    /**
     * Gets the right side of the tile
     * @param @none
     * @return m_leftSide
     */
    public int getRight() {
        return m_rightSide;
    }

    /**
     * Boolean that checks to see if the tile is blank
     * @param @none
     * @return true --> if the tile is 0-0
     * @return false --> if the tile is not 0-0
     */
    public boolean isBlank() {
        return (m_leftSide == 0 && m_rightSide == 0);
    }

    /**
     * Boolean that checks to see if the tile is a double
     * @param @none
     * @return true --> if the tile is a double
     * @return false --> if the tile is not a double
     */
    public boolean isDouble() {
        return (m_leftSide == m_rightSide);
    }

    /**
     * Fuction that returns an integer with the total sum of the tile
     * @param @none
     * @return returns the total sum of the tile
     */
    public int points() {
        return m_leftSide + m_rightSide;
    }

    void printDomino() {
        System.out.print("Domino <<" + m_leftSide + "-" + m_rightSide + ">> \n");
    }
}
