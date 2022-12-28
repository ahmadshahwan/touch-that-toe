package com.acme.tictactoe;

public class CellNotEmptyException extends IllegalStateException {

    public CellNotEmptyException(int i, int j) {
        super("Cell at (%d, %d) is occupied".formatted(i, j));
    }
}
