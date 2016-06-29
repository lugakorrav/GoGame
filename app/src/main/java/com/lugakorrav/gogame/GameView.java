package com.lugakorrav.gogame;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Григорий on 11.06.2016.
 */
public class GameView extends ImageView {

    float size;
    float sectorSize;
    int sectorCount;

    ArrayList<Coord> blackStones;
    ArrayList<Coord> whiteStones;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GameView,
                0, 0);
        sectorCount = a.getInt(R.styleable.GameView_sectorCount, 9);
    }

    @Override
    public void onDraw(Canvas canvas) {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                size = getResources().getDimensionPixelSize(R.dimen.field_size);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                size = getResources().getDimensionPixelSize(R.dimen.field_size_landscape);
        }

        sectorSize = size / sectorCount;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        for (int i = 1; i <= sectorCount; i++) {
            float step = i*sectorSize - sectorSize/2;
            canvas.drawLine(step, sectorSize/2, step, size - sectorSize/2, paint);
            canvas.drawLine(sectorSize/2, step, size - sectorSize/2, step, paint);
        }

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

        paint.setColor(Color.GRAY);
        float textSize = sectorSize/2;
        paint.setTextSize(textSize);
        char c_letter = 'A';
        for (int i = 1; i <= sectorCount; i++) {
            String str_letter = new String();
            str_letter += c_letter;
            float step_letter = size + sectorSize/2 - i*sectorSize;
            float step_number = -sectorSize/2 + i*sectorSize;
            canvas.drawText(str_letter, 0, step_letter, paint);
            canvas.drawText(new Integer(i).toString(), step_number, size, paint);
            c_letter++;
            if (c_letter == 'I') {
                c_letter++;
            }
        }
    }

    public void setStones(ArrayList<Coord> blackStones, ArrayList<Coord> whiteStones) {
        this.blackStones = blackStones;
        this.whiteStones = whiteStones;
        invalidate();
        requestLayout();
    }

    public void setSectorCount (int sectorCount) {
        this.sectorCount = sectorCount;
    }
}
