package com.acme.tictactoe;

import java.util.stream.IntStream;

public abstract class AbstractBoard<T extends Coordinate> implements Board<T> {

    private final int size;
    private int placed = 0;

    protected final int numberOfDimensions;

    private final int capacity;

    protected AbstractBoard(int size, int numberOfDimensions) {
        this.size = size;
        this.numberOfDimensions = numberOfDimensions;
        int cap = 1;
        for (int i = 0; i < this.numberOfDimensions; i++) {
            cap *= this.size;
        }
        this.capacity = cap;
    }

    public final int getSize() {
        return this.size;
    }

    public boolean place(T position, Player player) {
        if (this.at(position) != null) {
            throw new CellNotEmptyException(position);
        }
        this.placed++;
        this.put(position, player);
        return this.checkWin(position);
    }

    protected abstract void put(T position, Player player);

    protected abstract boolean checkWin(T position);

    public final boolean isFull()  {
        return this.placed >= this.capacity;
    }
}
