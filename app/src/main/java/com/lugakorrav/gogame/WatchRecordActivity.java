package com.lugakorrav.gogame;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class WatchRecordActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "INDEX";
    GameView gameView;
    GoGame goGame;
    GoGameApp app;
    DataSGF dataSGF;
    ArrayList<Field> fields;
    int index;
    int count = 19;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_record);
        app = (GoGameApp) getApplicationContext();
        dataSGF = app.getDataSGF();
        if (dataSGF.getParam("]SZ") != null) {
            count = Integer.parseInt(dataSGF.getParam("]SZ"));
        }
        gameView = (GameView) findViewById(R.id.watch_record_view_game);
        gameView.setSectorCount(count);
        goGame = new GoGame(count, GoGame.Cell.Black);

        if (dataSGF != null) {

            ArrayList<Coord> moves = dataSGF.getMoves();
            fields = new ArrayList<>();
            fields.add(new Field(count));

            for (Coord elem : moves) {
                goGame.makeTurn(elem);
                fields.add(new Field(goGame.getField()));
            }
            if (savedInstanceState != null) {
                index = savedInstanceState.getInt(KEY_INDEX, 0);
                gameView.setStones(fields.get(index).blackStones(), fields.get(index).whiteStones());
            } else {
                index = 0;
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, index);
    }

    public void onFirstClick(View view) {
        index = 0;
        gameView.setStones(fields.get(index).blackStones(), fields.get(index).whiteStones());
    }

    public void onPrevClick(View view) {
        if (index > 0) {
            index--;
        }
        gameView.setStones(fields.get(index).blackStones(), fields.get(index).whiteStones());
    }

    public void onNextClick(View view) {
        if (index < fields.size() - 1) {
            index++;
        }
        gameView.setStones(fields.get(index).blackStones(), fields.get(index).whiteStones());
    }

    public void onLastClick(View view) {
        index = fields.size() - 1;
        gameView.setStones(fields.get(index).blackStones(), fields.get(index).whiteStones());
    }

    public void onInfoClick(View view) {
        String info = new String();

        info += getResources().getString(R.string.komi);
        info += ": ";
        info += dataSGF.getParam("]KM");
        info += '\n';

        info += getResources().getString(R.string.date);
        info += ": ";
        info += dataSGF.getParam("]DT");
        info += '\n';

        info += getResources().getString(R.string.result);
        info += ": ";
        info += dataSGF.getParam("]RE");
        info += '\n';

        info += getResources().getString(R.string.rule);
        info += ": ";
        info += dataSGF.getParam("]RU");
        info += '\n';

        Intent intent = new Intent(WatchRecordActivity.this, InfoActivity.class);
        intent.putExtra("info", info);
        startActivity(intent);

        Log.v("log", info);

    }
}
