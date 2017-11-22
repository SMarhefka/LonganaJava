package com.svetlanamarhefka.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.svetlanamarhefka.R;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/17/2017                                             *
 ****************************************************************/

public class DominoView {
    private Bitmap[] m_Tiles;
    private Bitmap t_Normal;
    private Bitmap t_Vertical;
    private Context m_Context;

    public DominoView(Context a_InContext)
    {
        m_Context = a_InContext;
        m_Tiles = new Bitmap[7];
        Matrix m = new  Matrix();
        m.setRotate(90);

        t_Normal = BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.tile);
        t_Vertical = Bitmap.createBitmap(t_Normal, 0, 0, t_Normal.getWidth(), t_Normal.getHeight(), m, true);
    }

    public Bitmap getBackground(boolean a_InVertical)
    {
        return a_InVertical ? t_Vertical : t_Normal;
    }

    public Bitmap getTile(int i)
    {
        Bitmap ret = m_Tiles[i];

        if(ret == null)
        {
            Resources res = m_Context.getResources();
            String pkg = m_Context.getPackageName();

            String resName = "d" + String.valueOf(i);
            int resId = res.getIdentifier(resName, "drawable", pkg);

            ret = BitmapFactory.decodeResource(res, resId);
            m_Tiles[i] = ret;
        }

        return ret;
    }
}
