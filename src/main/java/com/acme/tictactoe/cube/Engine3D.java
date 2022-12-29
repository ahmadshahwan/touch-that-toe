package com.acme.tictactoe.cube;

import com.acme.tictactoe.Engine;
import java.io.PrintStream;

public class Engine3D extends Engine<Coordinate3D> {

    public Engine3D() {
        super(new Board3D());
    }

    @Override
    protected Coordinate3D coordinate(int i, int j, int k) {
        return new Coordinate3D(i, j, k);
    }

    @Override
    public void print(PrintStream out) {
        int size = this.board.getSize();
        out.println();
        for (int k = 0; k < size; k++) {
            out.print(" ".repeat(5));
            out.printf("(%c)", 'a' + k);
            if (k != size - 1) {
                out.print(" ".repeat(12));
            } else {
                out.println();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                out.print(" |");
                for (int j = 0; j < size; j++) {
                    Coordinate3D current = this.coordinate(i, j, k);
                    int label = i * size + j + 1;
                    this.print(out, current, label);
                }
                out.print("|");
                out.print(" ".repeat(8));
            }
            out.println();
        }
    }
}
