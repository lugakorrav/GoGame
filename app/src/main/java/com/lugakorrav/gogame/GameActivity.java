package com.lugakorrav.gogame;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    GameView gameView;
    GoGame goGame;
    GoGameApp app;
    TextView textView;
    int count = 9;
    int size;
    int sectorSize;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView = (TextView) findViewById(R.id.textView_whoseTurn);
        textView.setText(R.string.textView_whoseTurn_Black);
        gameView = (GameView) findViewById(R.id.view_game);
        count = getIntent().getExtras().getInt("count");
        gameView.setSectorCount(count);

        app = (GoGameApp) getApplicationContext();

        if (app.getGoGame() != null) {
            goGame = app.getGoGame();
            gameView.setStones(goGame.blackStones(), goGame.whiteStones());
        } else {
            goGame = new GoGame(count, GoGame.Cell.Black);
        }

        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                size = getResources().getDimensionPixelSize(R.dimen.field_size);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                size = getResources().getDimensionPixelSize(R.dimen.field_size_landscape);
        }

        sectorSize = size / count;
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = ((int) event.getX()) / sectorSize;
                int y = ((int) event.getY()) / sectorSize;
                String strx = (new Integer(x)).toString();
                String stry = (new Integer(y)).toString();
                Log.v("log", strx + " " + stry);
                if (goGame.makeTurn(new Coord(x, y))) {
                    gameView.setStones(goGame.blackStones(), goGame.whiteStones());
                    switch (goGame.whoseTurn()) {
                        case Black:
                            textView.setText(R.string.textView_whoseTurn_Black);
                            break;
                        case White:
                            textView.setText(R.string.textView_whoseTurn_White);
                            break;
                        }
                    }
                    return false;
                }
            }
            );
        }

        @Override
        protected void onPause () {
            app.setGoGame(goGame);
            super.onPause();
        }
    }
