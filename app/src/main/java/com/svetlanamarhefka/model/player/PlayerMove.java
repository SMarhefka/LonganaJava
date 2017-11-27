package com.svetlanamarhefka.model.player;

import com.svetlanamarhefka.model.Domino;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/27/2017                                             *
 ****************************************************************/

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
     * Getter for the domino in the move
     *
     * @return domino in the move
     */
    public Domino getM_Domino() {
        return m_Domino;
    }

    /**
     * setter for the domino in the move
     * @param a_InDomino domino to set dor the move
     */
    public void setM_Domino(Domino a_InDomino) {
        m_Domino = a_InDomino;
    }

    /**
     * Getter for the side in the move
     * @return side in the move
     */
    public Side getM_PlaySide() {
        return m_PlaySide;
    }

    /**
     * setter for the side in the move
     * @param a_InSide side to set for the move
     */
    public void setM_PlaySide(Side a_InSide) {
        m_PlaySide = a_InSide;
    }

    /**
     * To get the stringified version of the move
     * @return string representing the move
     */
    @Override
    public String toString() {
        return m_Domino.toString() + " on the " + m_PlaySide.toString();
    }
}
