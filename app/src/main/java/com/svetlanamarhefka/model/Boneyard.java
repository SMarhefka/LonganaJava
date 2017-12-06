package com.svetlanamarhefka.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;


/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Boneyard implements Serializable {

    private int m_LastIndex;
    //private ArrayList<Domino> m_UnusedTiles;
    private Vector<Domino> m_TilesBoneYard;
    //private Vector<Domino> m_SortedTiles;

    /**
     * Default constructor
     */
    public Boneyard()
    {
        this.m_LastIndex = 0;
        // initialize unusedTiles
        this.m_TilesBoneYard = new Vector<Domino>();
        // create the boneyard
        createBoneYard();
        // shuffle the boneyard
        shuffleBonyard();
    }

    /**
     * Overload constructor
     * @param a_InBoneyard --> takes in a Vector of Domino objects
     */
    public void setM_TilesBoneYard(Vector<Domino> a_InBoneyard)
    {
        // initialize unusedTiles
        m_TilesBoneYard = new Vector<Domino>();
        m_TilesBoneYard = a_InBoneyard;
    }

    /**
     * Creates the boneyard
     */
    private void createBoneYard()
    {
        for (int leftSide = 0; leftSide <= 6; leftSide++)
        {
            for (int rightSide = leftSide; rightSide <= 6; rightSide++)
            {
                m_TilesBoneYard.add(new Domino(leftSide, rightSide));
            }
        }
    }

    /**
     * Shuffles the bone-yard
     */
    private void shuffleBonyard()
    {
        Collections.shuffle(m_TilesBoneYard);
    }

    /**
     *
     * @return unused domino tile if it can
     */
    public Domino dealTile()
    {
        if(!m_TilesBoneYard.isEmpty())
        {
            Domino tempDomino = m_TilesBoneYard.get(getM_LastIndex());
            m_TilesBoneYard.remove(getM_LastIndex());
            return tempDomino;
        }
        throw new ArrayStoreException();
    }

    Vector<Domino> getM_UnusedTiles()
    {
        return (Vector<Domino>) m_TilesBoneYard.clone();
    }

    /**
     *
     * Getter function for last index
     * @return m_LastIndex --> The location of the last element in the boneyard
     */
    public int getM_LastIndex()
    {
        return m_LastIndex = m_TilesBoneYard.indexOf(m_TilesBoneYard.lastElement());
    }

    /**
     * Retrieves the size of the current boneyard (sizeofVector - 1)
     * @return int m_UnusedTile.size() --> returns the size of the m_UnusedTiles Vector
     */
    public int getSize()
    {
        return m_TilesBoneYard.size();
    }


    public boolean isEmpty()
    {
        return m_TilesBoneYard.isEmpty();
    }

    public String toString()
    {
        StringBuilder t_BoneYardString = new StringBuilder();
        t_BoneYardString.append("Boneyard: \n");

        for(Domino t_Domino: m_TilesBoneYard)
        {
            t_BoneYardString.append(t_Domino.toString());
            t_BoneYardString.append(" ");
        }
        return t_BoneYardString.toString();
    }

    /**
     * Print the boneyard
     */
    public void printBoneyard(int a_PrintType)
    {

        System.out.print("Boneyard\n");
        String fullString = "";

        for(int itemVal = 0; itemVal < m_TilesBoneYard.size(); itemVal++)
        {
            //m_UnusedTiles.get(itemVal).printDomino();
            fullString = fullString.concat(m_TilesBoneYard.get(itemVal).toString() + " ");
        }
        System.out.println(fullString + "\n");
    }

    public class EmptyBoneyardException extends Exception
    {
        public EmptyBoneyardException()
        {
            super("The boneyard is empty");
        }
    }


    /******************************CAUTION: TESTING AREA**********************************/
    /**
     * This will test the boneyard class
     * @param args
     */
    /*
    public static void main(String[] args)
    {
        System.out.println("\n" +
                "----------------------------------Boneyard Test-------------------------------");
        Boneyard boneyardTest = new  Boneyard();
        boneyardTest.printBoneyard();
        boneyardTest.printSortedBoneyard();
        System.out.println("Boneyard Size: " + boneyardTest.getSize());
    }
    */
}
