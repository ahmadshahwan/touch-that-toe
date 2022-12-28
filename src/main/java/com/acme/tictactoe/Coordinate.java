package com.acme.tictactoe;

public class Coordinate {
    private final int i;
    private final int j;

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "%d, %d".formatted(this.i, this.j);
    }
}
