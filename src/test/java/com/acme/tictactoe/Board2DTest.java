package com.acme.tictactoe;

import com.acme.tictactoe.square.Board2D;
import com.acme.tictactoe.square.Coordinate2D;

public class Board2DTest {

    public void testPlaceDoesNotAllowReplace() {
        Board2D board = new Board2D();
        board.place(new Coordinate2D(0, 0), Player.X);
        try {
            board.place(new Coordinate2D(0, 0), Player.X);
            assert false : "place() should not allow replace";
        } catch (CellNotEmptyException ignored) {}
        assert true : "place() should throw CellNotEmptyException";
    }

    public void testPlaceThenAt() {
        Board2D board = new Board2D();
        Coordinate2D o = new Coordinate2D(0, 0);
        board.place(o, Player.X);
        Player p = board.at(o);
        assert p == Player.X : "at() should return what was place()'d";
    }

    public void testWinDiagonal() {
        Board2D board = new Board2D();
        boolean test;
        test = board.place(new Coordinate2D(0, 0), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(1, 1), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(2, 2), Player.X);
        assert test : "Diagonal placements should win";
    }

    public void testWinAntiDiagonal() {
        Board2D board = new Board2D();
        boolean test;
        test = board.place(new Coordinate2D(0, 2), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(1, 1), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(2, 0), Player.X);
        assert test : "Anti-diagonal placements should win";
    }

    public void testWinRow() {
        Board2D board = new Board2D();
        boolean test;
        test = board.place(new Coordinate2D(0, 0), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(0, 1), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(0, 2), Player.X);
        assert test : "Row placements should win";
    }

    public void testWinColumn() {
        Board2D board = new Board2D();
        boolean test;
        test = board.place(new Coordinate2D(0, 0), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(1, 0), Player.X);
        assert !test;
        test = board.place(new Coordinate2D(2, 0), Player.X);
        assert test : "Column placements should win";
    }

    public void testIsFull() {
        Board2D board = new Board2D();
        Player p = Player.X;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                board.place(new Coordinate2D(i, j), p);
                p = p == Player.X ? Player.O : Player.X;
                if (i == j && i == board.getSize() - 1) {
                    assert board.isFull() : "Board should be reported full";
                } else {
                    assert !board.isFull() : "Board should not be reported full";
                }
            }
        }


    }
}
