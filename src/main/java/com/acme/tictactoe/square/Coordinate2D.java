package com.acme.tictactoe.square;

import com.acme.tictactoe.Coordinate;

public record Coordinate2D(int i, int j) implements Coordinate {

    @Override
    public String toString() {
        return "%d, %d".formatted(this.i, this.j);
    }
}
