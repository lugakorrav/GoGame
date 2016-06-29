package com.lugakorrav.gogame;

/**
 * Created by Григорий on 12.06.2016.
 */
public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;

        if(o == null)
            return false;

        if(!(getClass() == o.getClass()))
            return false;
        if ((x == ((Coord)o).x) && (y == ((Coord)o).y)) {
            return true;
        } else {
            return false;
        }
    }
}
