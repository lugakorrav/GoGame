package com.lugakorrav.gogame;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by Григорий on 11.06.2016.
 */
public class GoGame {

    public enum Cell {
        Empty, Black, White
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

    public boolean makeTurn(Coord coord) {
        Cell whoWaits = whoWaits();
        if (!canBeFilled(coord)) {
            return false;
        }
        field.setCell(coord, whoseTurn);

        ArrayList<Coord> enclosedGroup = new ArrayList<>();
        Coord[] startNeighbors = neighbors(coord);

        for (int i = 0; i < 4; i++) {
            ArrayList<Coord> enclosedPart = checkForEnclosed(startNeighbors[i], whoWaits);
            if (enclosedPart != null) {
                enclosedGroup.addAll(enclosedPart);
            }
        }

        int outsideCellsCount = 0;
        if (enclosedGroup.isEmpty()) {
            if (checkForEnclosed(coord, whoseTurn) != null) {
                field.setCell(coord, Cell.Empty);
                return false;
            }
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
        } else {
            whiteScore += score;
        }
        whoseTurn = whoWaits;
        return true;
    }

    private boolean canBeFilled(Coord coord) {
        if (field.getCell(coord) != Cell.Empty) {
            return false;
        }
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

        ArrayDeque<Coord> toCheck = new ArrayDeque<>();
        ArrayList<Coord> checked = new ArrayList<>();

        if (field.getCell(start) != groupColor) {
            return null;
        }
        toCheck.addFirst(start);

        while (!toCheck.isEmpty()) {

            Coord current = toCheck.getLast();
            Coord[] neighbors = neighbors(current);
            for (int i = 0; i < 4; i++) {
                if (field.getCell(neighbors[i]) == Cell.Empty) {
                    checked = null;
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
            toCheck.remove(current);
        }

        return checked;
    }

    private Coord[] neighbors(Coord c) {
        Coord[] neighbors = new Coord[4];
        neighbors[0] = new Coord(c.x + 1, c.y);
        neighbors[1] = new Coord(c.x, c.y + 1);
        neighbors[2] = new Coord(c.x - 1, c.y);
        neighbors[3] = new Coord(c.x, c.y - 1);
        return neighbors;
    }

    public ArrayList<Coord> blackStones() {
        return field.blackStones();
    }

    public ArrayList<Coord> whiteStones() {
        return field.whiteStones();
    }

    public Field getField() {
        return field;
    }

    public Cell whoseTurn() {
        return whoseTurn;
    }

    public int captured(Cell color) {
        int s = 0;

        int stoneCount = field.stoneCount(color);
        switch (field.stoneCount(invertColor(color))) {
            case 0:
                if (stoneCount > 0) {
                    return (field.getSize() * field.getSize() - stoneCount);
                }
            default:
                break;
        }

        int result = 0;
        ArrayList<Coord> checked = new ArrayList<>();

        for (int x = 0; x < field.getSize(); x++) {
            for (int y = 0; y < field.getSize(); y++) {
                ArrayList<Coord> captured = new ArrayList<>();
                ArrayDeque<Coord> toCheck = new ArrayDeque<>();

                Coord start = new Coord(x, y);
                boolean flag = false;

                if ((field.getCell(start) == Cell.Empty) && (!checked.contains(start))) {
                    toCheck.addFirst(start);
                    checked.add(start);
                    while (!toCheck.isEmpty()) {


                        Log.v("log", (new Integer(++s).toString()));


                        Coord current = toCheck.getLast();
                        Coord[] neighbors = neighbors(current);
                        for (int i = 0; i < 4; i++) {
                            Cell neighborColor = field.getCell(neighbors[i]);
                            if ((neighborColor == invertColor(color))) {
                                flag = true;
                                break;
                            }
                            if ((!captured.contains(neighbors[i])) &&
                                    (!checked.contains(neighbors[i])) &&
                                    (field.getCell(neighbors[i]) == Cell.Empty)) {
                                toCheck.addFirst(neighbors[i]);
                            }
                        }
                        if (!captured.contains(current)) {
                            captured.add(current);
                        }
                        toCheck.remove(current);
                    }
                    if (!flag) {
                        result += captured.size();
                    }
                    checked.addAll(captured);
                }
            }
        }
        return result;
    }

    private Cell invertColor(Cell color) {
        if (color == null) {
            return null;
        }
        switch (color) {
            case Black:
                return Cell.White;
            case White:
                return Cell.Black;
            default:
                return Cell.Empty;
        }
    }

    public void pass() {

    }
}
