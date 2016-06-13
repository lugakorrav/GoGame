package com.lugakorrav.gogame;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ArrayList;

/**
 * Created by Григорий on 11.06.2016.
 */
public class GoGame {

    public enum Cell {
        Empty, Black, White
    }

    private class Field {
        private Cell[][] cells;
        private int size;

        public Field(int size) {
            this.size = size;
            cells = new Cell[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = Cell.Empty;
                }
            }
        }

        public Cell getCell(Coord coord) {
            if ((coord.x < size) && (coord.y < size) && (coord.x >= 0) && (coord.y >= 0)) {
                return cells[coord.x][coord.y];
            } else {
                return null;
            }
        }

        public Cell getCell(int x, int y) {
            if ((x < size) && (y < size) && (x >= 0) && (y >= 0)) {
                return cells[x][y];
            } else {
                return null;
            }
        }

        public boolean setCell(Coord coord, Cell cell) {
            if ((coord.x < size) && (coord.y < size) && (coord.x >= 0) && (coord.y >= 0)) {
                cells[coord.x][coord.y] = cell;
                return true;
            } else {
                return false;
            }
        }

        public boolean setCell(int x, int y, Cell cell) {
            if ((x < size) && (y < size) && (x >= 0) && (y >= 0)) {
                cells[x][y] = cell;
                return true;
            } else {
                return false;
            }
        }
    }

    private Field field;
    private Cell whoseTurn;
    private Cell winner;
    private int blackScore;
    private int whiteScore;

    GoGame(int fieldSize, Cell whoseTurn) {

        field = new Field(fieldSize);
        this.whoseTurn = whoseTurn;
        winner = Cell.Empty;
        blackScore = 0;
        whiteScore = 0;
    }

    public boolean makeTurn(int x, int y) {
        Coord coord = new Coord(x, y);
        Cell whoWaits = whoWaits();
        if (!canBeFilled(coord)) {
            return false;
        }
        field.setCell(coord, whoseTurn);

        ArrayList<Coord> enclosedGroup = checkForEnclosed(coord, whoWaits);
        int outsideCellsCount = 0;
        if (enclosedGroup == null) {
            whoseTurn = whoWaits;
            return true;
        }
        for (Coord elem : enclosedGroup) {
            if (field.setCell(elem, Cell.Empty)) {
                outsideCellsCount++;
            }
        }
        int score = enclosedGroup.size() - outsideCellsCount;
        if (whoseTurn == Cell.Black) {
            blackScore += score;
        }
        else {
            whiteScore += score;
        }
        whoseTurn = whoWaits;
        return true;
    }

    private boolean canBeFilled(Coord coord) {
        int x = coord.x;
        int y = coord.y;
        if (field.getCell(coord) != Cell.Empty) {
            return false;
        }
//        if ((field.getCell(x + 1, y) != Cell.Empty) &&
//                (field.getCell(x - 1, y) != Cell.Empty) &&
//                (field.getCell(x, y + 1) != Cell.Empty) &&
//                (field.getCell(x, y - 1) != Cell.Empty)) {
//            return false;
//        }
        return true;
    }

    private Cell whoWaits() {
        if (whoseTurn == Cell.Black) {
            return Cell.White;
        } else if (whoseTurn == Cell.White) {
            return Cell.Black;
        }
        return Cell.Empty;
    }

    private ArrayList<Coord> checkForEnclosed(Coord start, Cell groupColor) {
        Coord[] startNeighbors = neighbors(start);

        ArrayList<Coord> enclosed = new ArrayList<>();

        for (int j = 0; j < 4; j++) {
            if (field.getCell(startNeighbors[j]) == groupColor) {

                ArrayDeque<Coord> toCheck = new ArrayDeque<>();
                ArrayList<Coord> checked = new ArrayList<>();

                toCheck.addFirst(startNeighbors[j]);

                while (!toCheck.isEmpty()) {
                    Coord current = toCheck.getLast();
                    Coord[] neighbors = neighbors(current);
                    for (int i = 0; i < 4; i++) {
                        if (field.getCell(neighbors[i]) == Cell.Empty) {
                            checked =  null;
                            break;
                        }
                        if ((!checked.contains(neighbors[i])) && (field.getCell(neighbors[i]) == groupColor)) {
                            toCheck.addFirst(neighbors[i]);
                        }
                    }
                    if (checked == null) {
                        break;
                    }
                    if (!checked.contains(current)) {
                        checked.add(current);
                    }
                    if (!checked.contains(current)) {
                        Log.v("kek", "shit");
                    }
                    toCheck.remove(current);
                }
                if (checked != null) {
                    enclosed.addAll(checked);
                }
            }
        }
        if (enclosed.isEmpty()) {
            return null;
        }
        return enclosed;
    }

    private Coord[] neighbors(Coord c) {
        Coord[] neighbors = new Coord[4];
        neighbors[0] = new Coord(c.x + 1, c.y);
        neighbors[1] = new Coord(c.x, c.y + 1);
        neighbors[2] = new Coord(c.x - 1, c.y);
        neighbors[3] = new Coord(c.x, c.y - 1);
        return neighbors;
    }

    public ArrayList<Coord> blackStones () {
        ArrayList<Coord> blackStones = new ArrayList<>();
        for (int i = 0; i < field.size; i++) {
            for (int j = 0; j < field.size; j++) {
                if (field.getCell(i, j) == Cell.Black) {
                    blackStones.add(new Coord(i, j));
                }
            }
        }
        return blackStones;
    }

    public ArrayList<Coord> whiteStones () {
        ArrayList<Coord> whiteStones = new ArrayList<>();
        for (int i = 0; i < field.size; i++) {
            for (int j = 0; j < field.size; j++) {
                if (field.getCell(i, j) == Cell.White) {
                    whiteStones.add(new Coord(i, j));
                }
            }
        }
        return whiteStones;
    }
}
