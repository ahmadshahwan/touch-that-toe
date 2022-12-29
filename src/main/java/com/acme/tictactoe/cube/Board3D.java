package com.acme.tictactoe.cube;

import com.acme.tictactoe.AbstractBoard;
import com.acme.tictactoe.Board;
import com.acme.tictactoe.Player;
import com.acme.tictactoe.square.Coordinate2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Board3D extends AbstractBoard<Coordinate3D> {

    private final Player[][][] cells;

    public Board3D(int size) {
        super(size);
        this.cells = new Player[size][size][size];
    }

    public Board3D() {
        this(DEFAULT_SIZE);
    }

    @Override
    protected void put(Coordinate3D position, Player player) {
        this.cells[position.i()][position.j()][position.k()] = player;
    }

    @Override
    protected boolean checkWin(Coordinate3D position) {
        Player player = this.at(position);
        int i = position.i();
        int j = position.j();
        int k = position.k();
        return
                this.testOverRange(x -> this.cells[x][j][k] == player) ||
                this.testOverRange(x -> this.cells[i][x][k] == player) ||
                this.testOverRange(x -> this.cells[i][j][x] == player) ||
                this.testDiagonal(i, j, k, this::diag, this::diag, player) ||
                this.testDiagonal(i, j, k, this::diag, this::anti, player) ||
                this.testDiagonal(i, j, k, this::anti, this::diag, player) ||
                this.testDiagonal(i, j, k, this::anti, this::anti, player) ||
                (i == diag(j) && this.testDiagonal(2, k, this::diag, player)) ||
                (i == anti(j) && this.testDiagonal(2, k, this::anti, player)) ||
                (i == diag(k) && this.testDiagonal(1, j, this::diag, player)) ||
                (i == anti(k) && this.testDiagonal(1, j, this::anti, player)) ||
                (j == diag(k) && this.testDiagonal(0, i, this::diag, player)) ||
                (j == anti(k) && this.testDiagonal(0, i, this::anti, player));
    }

    private boolean testDiagonal(int fixedDimension, int fixedValue, Function<Integer, Integer> f, Player player) {
        List<Function<Integer, Integer>> list = new ArrayList<>(3);
        list.add(this::diag);
        list.add(f);
        list.add(fixedDimension, (ignored) -> fixedValue);
        return this.testDiagonal(list.get(0), list.get(1), list.get(2), player);
    }

    private boolean testDiagonal(Function<Integer, Integer> fI, Function<Integer, Integer> fJ, Function<Integer, Integer> fK, Player player) {
        return this.testOverRange(x -> this.cells[fI.apply(x)][fJ.apply(x)][fK.apply(x)] == player);
    }

    private boolean testDiagonal(int i, int j, int k, Function<Integer, Integer> fJ, Function<Integer, Integer> fK, Player player) {
        return (i == fJ.apply(j) && i == fK.apply(k) && this.testDiagonal(x -> x, fJ, fK, player));
    }

    private int diag(int x) {
        return x;
    }

    private int anti(int x) {
        return this.getSize() - 1 - x;
    }

    private boolean testOverRange(IntPredicate predicate) {
        return IntStream.range(0, this.getSize()).allMatch(predicate);
    }


    @Override
    public Player at(Coordinate3D position) {
        return this.at(position.i(), position.j(), position.k());
    }

    public Player at(int i, int j, int k) {
        return this.cells[i][j][k];
    }

    public Board<Coordinate2D> at(int i) {
        return new AbstractBoard<>(this.getSize()) {
            @Override
            protected void put(Coordinate2D position, Player player) {
                throw new IllegalStateException("This board is readonly");
            }

            @Override
            protected boolean checkWin(Coordinate2D position) {
                return false;
            }

            @Override
            public Player at(Coordinate2D position) {
                return Board3D.this.at(i, position.i(), position.j());
            }
        };
    }
}
