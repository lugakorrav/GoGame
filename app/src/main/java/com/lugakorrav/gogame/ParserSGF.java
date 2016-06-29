package com.lugakorrav.gogame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Григорий on 28.06.2016.
 */
public class ParserSGF {

    static String MOVES_START = ";B[";
    static String[] KEYS = {"]AB", "]AW", "]AN", "]AP", "]BR", "]BT", "]C", "]CP",
            "]DT", "]EV", "]FF", "]GM", "]GN", "]HA", "]KM", "]ON", "]OT", "]PB", "]PC",
            "]PL", "]PW", "]RE", "]RO", "]RU", "]SO", "]SZ", "]TM", "]US", "]W", "]WR", "]WT"};

    public static DataSGF getDataSGF(String fileName) {
        DataSGF dataSGF = new DataSGF();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileInputStream != null) {
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(isr);

            try {
                String str = reader.readLine();
                ArrayList<Coord> moves = getMoves(str);
                HashMap<String, String> data = getData(str);
                dataSGF.setMoves(moves);
                dataSGF.setData(data);

                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dataSGF;
    }

    private static HashMap<String, String> getData(String str) {
        HashMap<String, String> data = new HashMap<>();
        for (int i = 0; i < KEYS.length; i++) {
            String value = new String();
            int pointer = str.indexOf(KEYS[i]);
            if (pointer != -1) {
                pointer = str.indexOf('[', pointer) + 1;
                while (str.charAt(pointer) != ']') {
                    value += str.charAt(pointer);
                    pointer++;
                }
            } else {
                value = "";
            }
            data.put(KEYS[i], value);
        }
        return data;
    }

    private static ArrayList<Coord> getMoves(String str) {
        int movesStartFrom = str.indexOf(MOVES_START);

        int pointer = movesStartFrom + 1;

        ArrayList<Coord> moves = new ArrayList<>();

        while ((str.charAt(pointer) == 'B') || (str.charAt(pointer) == 'W')) {
            pointer += 2;
            int x = (str.charAt(pointer) - 'a');
            if (x > (int) 'i') {
                x--;
            }
            int y = (str.charAt(++pointer) - 'a');
            if (y > (int) 'i') {
                y--;
            }
            moves.add(new Coord(x, y));
            pointer += 3;
            if (pointer >= str.length()) {
                break;
            }
        }
        return moves;
    }
}
