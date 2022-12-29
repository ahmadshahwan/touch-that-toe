package com.acme.tictactoe.cube;

import com.acme.tictactoe.CellNotEmptyException;
import com.acme.tictactoe.Player;

public class Board3DTest {

    Board3D board;

    public void setUp() {
        this.board = new Board3D();
    }

    private Coordinate3D coordinate(int i, int j, int k) {
        return new Coordinate3D(i, j, k);
    }

    public void testPlaceDoesNotAllowReplace() {
        this.setUp();
        var o = coordinate(0, 0, 0);
        board.place(o, Player.X);
        try {
            board.place(o, Player.X);
            assert false : "place() should not allow replace";
        } catch (CellNotEmptyException ignored) {}
        assert true : "place() should throw CellNotEmptyException";
    }

    public void testPlaceThenAt() {
        this.setUp();
        var o = coordinate(0, 0, 0);
        board.place(o, Player.X);
        Player p = board.at(o);
        assert p == Player.X : "at() should return what was place()'d";
    }

    public void testWinDiagonal() {
        this.setUp();
        boolean test;
        test = board.place(coordinate(0, 0, 1), Player.X);
        assert !test;
        test = board.place(coordinate(1, 1, 1), Player.X);
        assert !test;
        test = board.place(coordinate(2, 2, 1), Player.X);
        assert test : "Diagonal placements should win";
    }

    public void testWinCrossDiagonal() {
        this.setUp();
        boolean test;
        test = board.place(coordinate(0, 0, 0), Player.X);
        assert !test;
        test = board.place(coordinate(1, 1, 1), Player.X);
        assert !test;
        test = board.place(coordinate(2, 2, 2), Player.X);
        assert test : "Diagonal placements should win";
    }

    public void testWinAntiDiagonal() {
        this.setUp();
        boolean test;
        test = board.place(coordinate(0, 2, 2), Player.X);
        assert !test;
        test = board.place(coordinate(1, 1, 2), Player.X);
        assert !test;
        test = board.place(coordinate(2, 0, 2), Player.X);
        assert test : "Anti-diagonal placements should win";
    }

    public void testWinAntiCrossDiagonal() {
        this.setUp();
        boolean test;
        test = board.place(coordinate(2, 2, 0), Player.X);
        assert !test;
        test = board.place(coordinate(1, 1, 1), Player.X);
        assert !test;
        test = board.place(coordinate(0, 0, 2), Player.X);
        assert test : "Diagonal placements should win";
    }


    public void testWinRow() {
        this.setUp();
        boolean test;
        test = board.place(coordinate(0, 0, 0), Player.X);
        assert !test;
        test = board.place(coordinate(0, 1, 0), Player.X);
        assert !test;
        test = board.place(coordinate(0, 2, 0), Player.X);
        assert test : "Row placements should win";
    }

    public void testWinColumn() {
        this.setUp();
        boolean test;
        test = board.place(coordinate(1, 0, 0), Player.X);
        assert !test;
        test = board.place(coordinate(1, 1, 0), Player.X);
        assert !test;
        test = board.place(coordinate(1, 2, 0), Player.X);
        assert test : "Column placements should win";
    }

    public void testIsFull() {
        this.setUp();
        Player p = Player.X;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                for (int k = 0; k < board.getSize(); k++) {
                    board.place(coordinate(i, j, k), p);
                    p = p == Player.X ? Player.O : Player.X;
                    if (i == j && j == k && i == board.getSize() - 1) {
                        assert board.isFull() : "Board should be reported full";
                    } else {
                        assert !board.isFull() : "Board should not be reported full";
                    }
                }
            }
        }
    }

}
