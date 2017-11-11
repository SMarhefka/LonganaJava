
package com.svetlanamarhefka.model;

import java.util.ArrayList;
import java.util.Collections;

/****************************************************************
 * Name:    Svetlana Marhefka                                   *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)*
 * Date:    11/11/2017                                          *
 ****************************************************************/
public class Boneyard {

    private ArrayList<Domino> unusedTiles;

    /**
     * Default constructor
     */
    public Boneyard()
    {
        // create the boneyard
        createBoneYard();
        // shuffle the boneyard
        shuffleBonyard();
    }

    /**
     * Overload constructor
     * @param a_inBoneyard
     */
    public Boneyard(ArrayList<Domino> a_inBoneyard)
    {
        unusedTiles = new ArrayList<Domino>();
        this.unusedTiles = a_inBoneyard;
    }

    /**
     * Creates the boneyard
     */
    public void createBoneYard()
    {
        // initialize unusedTiles
        unusedTiles = new ArrayList<Domino>();
        for (int leftSide = 0; leftSide <= 6; leftSide++)
        {
            for (int rightSide = leftSide; rightSide <= 6; rightSide++)
            {
                unusedTiles.add(new Domino(leftSide, rightSide));
            }
        }
    }

    /**
     * Shuffles the bone-yard
     */
    public void shuffleBonyard()
    {
        Collections.shuffle(unusedTiles);
    }

    /**
     *
     * @return unused domino tile if it can
     */
    public Domino dealTile()
    {
        if(unusedTiles.size() > 0)
        {
            return unusedTiles.remove(0);
        }
        else
        {
            return null;
        }
    }


    public ArrayList<Domino> getBoneyard(){
        return unusedTiles;
    }
}
