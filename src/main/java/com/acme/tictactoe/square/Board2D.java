package com.acme.tictactoe.square;

import com.acme.tictactoe.AbstractBoard;
import com.acme.tictactoe.Player;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Board2D extends AbstractBoard<Coordinate2D> {

    private final Player[][] cells;

    public Board2D(int size) {
        super(size, 2);
        this.cells = new Player[size][size];
    }

    public Board2D() {
        this(DEFAULT_SIZE);
    }

    @Override
    protected void put(Coordinate2D position, Player player) {
        this.cells[position.i()][position.j()] = player;
    }

    @Override
    protected boolean checkWin(Coordinate2D position) {
        Player player = this.at(position);
        int i = position.i();
        int j = position.j();
        int size = this.getSize();
        return
                this.testOverRange(x -> this.cells[x][j] == player) ||
                this.testOverRange(x -> this.cells[i][x] == player) ||
                (i == j && this.testOverRange(x -> this.cells[x][x] == player)) ||
                (i == size - 1 - j && testOverRange(x -> this.cells[x][size - 1 - x] == player));
    }

    private boolean testOverRange(IntPredicate predicate) {
        return IntStream.range(0, this.getSize()).allMatch(predicate);
    }


    @Override
    public Player at(Coordinate2D position) {
        return this.at(position.i(), position.j());
    }
    public Player at(int i, int j) {
        return this.cells[i][j];
    }
}
