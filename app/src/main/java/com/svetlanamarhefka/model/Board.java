package com.svetlanamarhefka.model;

import java.util.Vector;
/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/11/2017                                          *
 ****************************************************************/


public class Board {

    private Vector<Domino> m_boardVector;

    /**
     * Default Constructor
     */
    public Board()
    {
    }

    /**
     * Overloaded Constructor
     */
    Board(Vector<Domino> a_inGameBoard)
    {
        m_boardVector = new Vector<Domino>();
        this.m_boardVector = a_inGameBoard;
    }

}
