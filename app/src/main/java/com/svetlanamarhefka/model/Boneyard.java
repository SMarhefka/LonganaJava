package com.svetlanamarhefka.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Boneyard{

    private int m_LastIndex;
    //private ArrayList<Domino> m_UnusedTiles;
    private Vector<Domino> m_UnusedTiles;
    //private Vector<Domino> m_SortedTiles;

    /**
     * Default constructor
     */
    public Boneyard()
    {
        this.m_LastIndex = 0;
        // initialize unusedTiles
        this.m_UnusedTiles = new Vector<Domino>();
        // create the boneyard
        createBoneYard();
        // shuffle the boneyard
        shuffleBonyard();
    }

    /**
     * Overload constructor
     * @param a_InBoneyard --> takes in a Vector of Domino objects
     */
    public Boneyard(Vector<Domino> a_InBoneyard)
    {
        this.m_LastIndex = 0;
        // initialize unusedTiles
        this.m_UnusedTiles = new Vector<Domino>();
        m_UnusedTiles = a_InBoneyard;
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
                m_UnusedTiles.add(new Domino(leftSide, rightSide));
            }
        }
    }

    /**
     * Shuffles the bone-yard
     */
    private void shuffleBonyard()
    {
        Collections.shuffle(m_UnusedTiles);
    }

    /**
     *
     * @return unused domino tile if it can
     */
    public Domino dealTile()
    {
        if(!m_UnusedTiles.isEmpty())
        {
            Domino tempDomino = m_UnusedTiles.get(getM_LastIndex());
            m_UnusedTiles.remove(getM_LastIndex());
            return tempDomino;
        }
        throw new ArrayStoreException();
    }

    /**
     *
     * Getter function for last index
     * @return m_LastIndex --> The location of the last element in the boneyard
     */
    public int getM_LastIndex()
    {
        return m_LastIndex = m_UnusedTiles.indexOf(m_UnusedTiles.lastElement());
    }

    /**
     * Retrieves the size of the current boneyard (sizeofVector - 1)
     * @return int m_UnusedTile.size() --> returns the size of the m_UnusedTiles Vector
     */
    public int getSize()
    {
        return m_UnusedTiles.size();
    }



    /**
     * THIS FUNCTION MAY NEVER NEED TO BE USED
     * @return

    private void setM_SortedTiles(Vector<Domino> a_InVector)
    {
        m_SortedTiles = sortItems(a_InVector);
    }
     */
    /**
     * THIS FUNCTION MAY NEVER NEED TO BE USED
     * @return

    public Vector<Domino> getM_SortedTiles(Vector<Domino> a_InVector)
    {
        setM_SortedTiles(a_InVector);
        return m_SortedTiles;
    }
    */
    /**
     * Sorts the the boneyard for easier printing and visualization
     * @param a_InVector --> Vector that will be sorted by the function
     */
    public void sortItems(Vector<Domino> a_InVector)
    {
        Collections.sort(a_InVector, new Comparator()
        {
            /*
            public int compare(Object dom1, Object dom2)
            {
                Integer tileSum1 = ((Domino)dom1).tileSum();
                Integer tileSum2 = ((Domino)dom2).tileSum();

                if(tileSum1 == tileSum2)
                {
                    Integer left1 = ((Domino)dom1).getLeft();
                    Integer left2 = ((Domino)dom2).getLeft();
                    return left1.compareTo(left2);
                }
                int sComp = tileSum1.compareTo(tileSum2);
                return sComp;
            }
            */

            // New comparison operator
            public int compare(Object dom1, Object dom2)
            {
                Integer left1 = ((Domino)dom1).getM_leftSide();
                Integer left2 = ((Domino)dom2).getM_leftSide();
                int resultComp = left1.compareTo(left2);
                // This sorts according to the left hand side
                if(resultComp != 0)
                {
                    return resultComp;
                }
                else
                {
                    // This will sort by the right hand side
                    Integer right1 = ((Domino)dom1).getM_rightSide();
                    Integer right2 = ((Domino)dom2).getM_rightSide();
                    return right1.compareTo(right2);
                }
            }
        });
    }


    /**
     * Print the boneyard
     */
    public void printBoneyard(int a_PrintType)
    {
        switch (a_PrintType)
        {
            // If we want the boneyard to be sorted
            case 1:
                // Sort the boneyard
                sortItems(m_UnusedTiles);
                System.out.print("Boneyard Sorted\n");
                break;
            default:
                System.out.print("Boneyard Unsorted\n");
        }
        String fullString = "";

        for(int itemVal = 0; itemVal < m_UnusedTiles.size(); itemVal++)
        {
            //m_UnusedTiles.get(itemVal).printDomino();
            fullString = fullString.concat(m_UnusedTiles.get(itemVal).toString() + " ");
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
