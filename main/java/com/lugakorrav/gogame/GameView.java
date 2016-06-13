package com.lugakorrav.gogame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Григорий on 11.06.2016.
 */
public class GameView extends ImageView {

    int size;
    int sectorSize;
    int count = 9;

    ArrayList<Coord> blackStones;
    ArrayList<Coord> whiteStones;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.count = count;
    }

    @Override
    public void onDraw(Canvas canvas) {
        size = getResources().getDimensionPixelSize(R.dimen.field_size);
        sectorSize = size / count;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        if (blackStones != null) {
            for (Coord elem : blackStones) {
                canvas.drawCircle(elem.x*sectorSize + sectorSize/2,
                        elem.y*sectorSize + sectorSize/2, sectorSize / 2, paint);
            }
        }
        paint.setColor(Color.WHITE);
        if (whiteStones != null) {
            for (Coord elem : whiteStones) {
                canvas.drawCircle(elem.x*sectorSize + sectorSize/2,
                        elem.y*sectorSize + sectorSize/2, sectorSize / 2, paint);
            }
        }
        Log.v("kek", "onDraw");
    }

    public void setStones(ArrayList<Coord> blackStones, ArrayList<Coord> whiteStones) {
        this.blackStones = blackStones;
        this.whiteStones = whiteStones;
        invalidate();
        requestLayout();
        Log.v("kek", "onDraw");
    }
}
