package com.svetlanamarhefka.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.svetlanamarhefka.model.Domino;

/****************************************************************
 * Name:    Svetlana Marhefka                                             *
 * Project: Project 2 - Longana                                 *
 * Class:   CMPS 366 Organization of Programming Languages (OPL)* 
 * Date:    11/17/2017                                             *
 ****************************************************************/

public class DominoView extends View {

    private Context m_Context;

    DominoView(Context context)
    {
        super(context);
        m_Context = context;
    }

    public Drawable drawDomino(int a_InSide1, int a_InSide2, boolean a_InHorVer, boolean a_InFlip)
    {
        Log.v("drawable", "d" + String.valueOf(a_InSide1) + "_" + String.valueOf(a_InSide2));

        Bitmap final_Bitmap;

        Resources res = m_Context.getResources();
        String pkg = m_Context.getPackageName();

        String resName = "d" + String.valueOf(a_InSide1) + "_" + String.valueOf(a_InSide2);
        int resId = res.getIdentifier(resName, "drawable", pkg);

        Bitmap l_Bitmap = BitmapFactory.decodeResource(res, resId);
        final_Bitmap = l_Bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Matrix m_Matrix = new Matrix();

        if(a_InHorVer == true)
        {
            m_Matrix.postRotate(90);
        }

        if(a_InFlip == true)
        {
            m_Matrix.postRotate(180);
        }

        return new BitmapDrawable(res, Bitmap.createBitmap(final_Bitmap, 0, 0, final_Bitmap.getWidth(), final_Bitmap.getHeight(), m_Matrix, true));
    }

    public ImageView imgDominoUpDwn(final Domino a_InDomino)
    {
        ImageView t_Image = new ImageView(m_Context);
        t_Image.setLayoutParams(new ViewGroup.LayoutParams(1, 2));
        t_Image.setBackground(drawDomino(a_InDomino.getM_leftSide(), a_InDomino.getM_rightSide(), true, false));
        return t_Image;
    }

    public ImageView imgDominoAccross(final Domino a_InDomino, boolean a_InFlip)
    {
        ImageView t_Image = new ImageView(m_Context);
        t_Image.setLayoutParams(new ViewGroup.LayoutParams(1, 2));
        t_Image.setBackground(drawDomino(a_InDomino.getM_leftSide(), a_InDomino.getM_rightSide(), false, a_InFlip));
        return t_Image;
    }
}
