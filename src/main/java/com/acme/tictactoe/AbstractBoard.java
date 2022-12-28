package com.acme.tictactoe;

public abstract class AbstractBoard<T extends Coordinate> implements Board<T> {

    protected final int size;
    protected int placed = 0;

    protected AbstractBoard(int size) {
        this.size = size;
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
        return this.placed == this.size * this.size;
    }
}
