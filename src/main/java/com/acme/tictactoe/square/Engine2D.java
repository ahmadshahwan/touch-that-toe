package com.acme.tictactoe.square;

import com.acme.tictactoe.Engine;
import java.io.PrintStream;

public class Engine2D extends Engine<Coordinate2D> {

    public Engine2D() {
        super(new Board2D());
    }

    @Override
    protected Coordinate2D coordinate(int i, int j, int ignored) {
        return new Coordinate2D(i, j);
    }

    @Override
    public void print(PrintStream out) {
        int size = this.board.getSize();
        out.println();
        for (int i = 0; i < size; i++) {
            out.print(" |");
            for (int j = 0; j < size; j++) {
                Coordinate2D current = new Coordinate2D(i, j);
                int label = i * size + j + 1;
                this.print(out, current, label);
            }
            out.println("|");
        }
    }
}
