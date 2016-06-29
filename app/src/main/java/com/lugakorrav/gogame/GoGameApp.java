package com.lugakorrav.gogame;

import android.app.Application;

/**
 * Created by Григорий on 28.06.2016.
 */
public class GoGameApp extends Application {
    private DataSGF dataSGF;
    private GoGame goGame;

    public void setDataSGF(DataSGF dataSGF) {
        this.dataSGF = dataSGF;
    }

    public DataSGF getDataSGF() {
        return dataSGF;
    }

    public void setGoGame(GoGame goGame) {
        this.goGame = goGame;
    }

    public GoGame getGoGame() {
        return goGame;
    }
}
