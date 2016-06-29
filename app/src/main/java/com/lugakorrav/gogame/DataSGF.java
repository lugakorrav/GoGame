package com.lugakorrav.gogame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Григорий on 28.06.2016.
 */
public class DataSGF {
    private ArrayList<Coord> moves;
    private HashMap<String, String> data;

    public ArrayList<Coord> getMoves() {
        return moves;
    }

    public void setMoves (ArrayList<Coord> moves) {
        this.moves = moves;
    }

    public void setData (HashMap<String, String> data) {
        this.data = data;
    }

    public String getParam(String key) {
        return data.get(key);
    }
}
