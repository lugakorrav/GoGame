package com.lugakorrav.gogame;

import java.util.ArrayList;

/**
 * Created by Григорий on 28.06.2016.
 */
public class Field {
    private GoGame.Cell[][] cells;
    private int size;

    public Field(int size) {
        this.size = size;
        cells = new GoGame.Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = GoGame.Cell.Empty;
            }
        }
    }

    public Field(Field field) {
        this.size = field.size;
        cells = new GoGame.Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = field.cells[i][j];
            }
        }
    }

    public GoGame.Cell getCell(Coord coord) {
        if ((coord.x < size) && (coord.y < size) && (coord.x >= 0) && (coord.y >= 0)) {
            return cells[coord.x][coord.y];
        } else {
            return null;
        }
    }

    public GoGame.Cell getCell(int x, int y) {
        if ((x < size) && (y < size) && (x >= 0) && (y >= 0)) {
            return cells[x][y];
        } else {
            return null;
        }
    }

    public boolean setCell(Coord coord, GoGame.Cell cell) {
        if ((coord.x < size) && (coord.y < size) && (coord.x >= 0) && (coord.y >= 0)) {
            cells[coord.x][coord.y] = cell;
            return true;
        } else {
            return false;
        }
    }

    public boolean setCell(int x, int y, GoGame.Cell cell) {
        if ((x < size) && (y < size) && (x >= 0) && (y >= 0)) {
            cells[x][y] = cell;
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Coord> blackStones() {
        ArrayList<Coord> blackStones = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (getCell(i, j) == GoGame.Cell.Black) {
                    blackStones.add(new Coord(i, j));
                }
            }
        }
        return blackStones;
    }

    public ArrayList<Coord> whiteStones() {
        ArrayList<Coord> whiteStones = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (getCell(i, j) == GoGame.Cell.White) {
                    whiteStones.add(new Coord(i, j));
                }
            }
        }
        return whiteStones;
    }

    public int stoneCount(GoGame.Cell color) {
        int result = 0;
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (getCell(i, j) == color) {
                    result++;
                }
            }
        }
        return result;
    }

    public int getSize () {
        return size;
    }
}
