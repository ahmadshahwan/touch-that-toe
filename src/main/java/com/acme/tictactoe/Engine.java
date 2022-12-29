package com.acme.tictactoe;

import java.io.PrintStream;
import java.util.Arrays;

public abstract class Engine<T extends Coordinate> {

    protected Board<T> board;

    protected T next;

    private Player currentPlayer;

    protected Engine(Board<T> board) {
        this.board = board;
    }

    public Player flipPlayer() {
        return this.currentPlayer = this.currentPlayer == Player.X ? Player.O : Player.X;
    }

    public boolean place() {
        boolean result = this.board.place(this.next, this.currentPlayer);
        this.next = null;
        return result;
    }

    protected abstract T coordinate(int i, int j, int k);

    public void updateNext(int i, int j, int k) {
        T coordinate = this.coordinate(i, j, k);
        if (this.board.at(coordinate) != null) {
            throw new CellNotEmptyException(coordinate);
        }
        this.next = coordinate;
    }

    public boolean isGameOver() {
        return this.board.isFull();
    }

    public int dimensions() {
        return this.board.dimensions();
    }

    public int boardSize() {
        return this.board.getSize();
    }

    private boolean isInWinningCombination(T current) {
        return this.board.winningCombination() != null &&
                Arrays.asList(this.board.winningCombination()).contains(current);
    }

    protected void print(PrintStream out, T current, int label) {
        Player player = this.board.at(current);
        if (this.next !=null && this.next.equals(current)) {
            out.printf(">%s<", this.currentPlayer.toString().toLowerCase());
        } else if (player == null) {
            out.printf(" %d ", label);
        } else if (isInWinningCombination(current)) {
            out.printf("[%s]", player);
        } else {
            out.printf(" %s ", player);
        }
    }

    public abstract void print(PrintStream out);
}
