package com.acme.tictactoe;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Board {

    public static final int DEFAULT_SIZE = 3;

    private final int size;

    private final Player[][] cells;

    private int placed = 0;

    public Board(int size) {
        this.size = size;
        this.cells = new Player[size][size];
    }

    public Board() {
        this(DEFAULT_SIZE);
    }

    public int getSize() {
        return size;
    }

    public boolean place(int i, int j, Player player) {
        if (this.cells[i][j] != null) {
            throw new CellNotEmptyException(i, j);
        }
        this.placed++;
        this.cells[i][j] = player;
        return
                this.testOverRange(x -> this.cells[x][j] == player) ||
                this.testOverRange(x -> this.cells[i][x] == player) ||
                (i == j && this.testOverRange(x -> this.cells[x][x] == player)) ||
                (i == size - 1 - j && testOverRange(x -> this.cells[x][size - 1 - x] == player));
    }

    private boolean testOverRange(IntPredicate predicate) {
        return IntStream.range(0, size).allMatch(predicate);
    }

    public Player at(int i, int j) {
        return this.cells[i][j];
    }

    public boolean isFull() {
        return this.placed == this.size * this.size;
    }
}
