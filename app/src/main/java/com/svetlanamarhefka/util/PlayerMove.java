package com.svetlanamarhefka.util;
import com.svetlanamarhefka.model.Domino;
/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/27/2017                                          *
 * Assistance From: Beeshall's program                          *
 * Assistance From:                                             *
 * https://github.com/msaldana/java-Dominoes/                   *
 * blob/a29e64f88a564e801e5084d83e758bcfd5335fbc/               *
 * src/uprm/ece/icom5015/util/DominoPlayTuple.java              *
 ****************************************************************/

/**
 * I originally saw this on the listed website which implemented a different version
 * of the dominoes game.  Getting this to work took some time but it is perfect for
 * comparing the best moves.
 */
public class PlayerMove {

    // m_Domino is the domino that will be placed
    private Domino m_Domino;
    // m_PlaySide is the side that we are trying to place the tile on
    private Side m_PlaySide;

    /**
     * Constructor for the move
     *
     * @param a_InDomino domino trying to be placed
     * @param a_InSide   side the move was or to be played on
     */
    public PlayerMove(Domino a_InDomino, Side a_InSide) {
        m_Domino = a_InDomino;
        m_PlaySide = a_InSide;
    }

    /**
     * Getter function for the domino in the move
     * @return The domino for the possible move
     */
    public Domino getM_Domino() {
        return m_Domino;
    }

    /**
     * Setter function for the domino in the move
     * @param a_InDomino The domino to set for the possible move
     */
    public void setM_Domino(Domino a_InDomino) {
        m_Domino = a_InDomino;
    }

    /**
     * Getter for the side value in the move
     * @return The Side relevant to the possible move
     */
    public Side getM_PlaySide() {
        return m_PlaySide;
    }

    /**
     * Setter for the side in the move
     * @param a_InSide the side to set for the possible move
     */
    public void setM_PlaySide(Side a_InSide) {
        m_PlaySide = a_InSide;
    }

    /**
     * To get the move to a string version
     * @return string representing the move
     */
    @Override
    public String toString() {
        return m_Domino.toString() + " on the " + m_PlaySide.toString();
    }
}
