package com.lugakorrav.gogame;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    GameView gameView;
    GoGame goGame;
    SaveFragment saveFragment;
    int count = 9;
    int size;
    int sectorSize;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView) findViewById(R.id.view_game);

        saveFragment = (SaveFragment) getFragmentManager().findFragmentByTag("SAVE_FRAGMENT");

        if (saveFragment != null) {
            goGame = saveFragment.getGoGame();
            gameView.setStones(goGame.blackStones(), goGame.whiteStones());
        } else {
            goGame = new GoGame(count, GoGame.Cell.Black);
        }
        saveFragment = new SaveFragment();
        getFragmentManager().beginTransaction()
                .add(saveFragment, "SAVE_FRAGMENT")
                .commit();

        size = getResources().getDimensionPixelSize(R.dimen.field_size);
        sectorSize = size / count;
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = ((int) event.getX())/sectorSize;
                int y = ((int) event.getY())/sectorSize;
                String strx = (new Integer(x)).toString();
                String stry = (new Integer(y)).toString();
                Log.v("kek", strx + " " + stry);
                if (goGame.makeTurn(x, y)) {
                    gameView.setStones(goGame.blackStones(), goGame.whiteStones());
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        saveFragment.setGoGame(goGame);
        super.onPause();
    }
}
