package com.acme.tictactoe.cube;

import com.acme.tictactoe.Coordinate;

public record Coordinate3D(int i, int j, int k) implements Coordinate {

    @Override
    public String toString() {
        return "%d, %d".formatted(this.i, this.j);
    }
}
