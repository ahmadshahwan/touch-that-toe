package com.acme.tictactoe;

public class CellNotEmptyException extends IllegalStateException {

    public CellNotEmptyException(Coordinate position) {
        super("Cell at %s is occupied".formatted(position));
    }
}
