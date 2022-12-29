package com.acme.tictactoe.square;

import com.acme.tictactoe.AbstractBoard;
import com.acme.tictactoe.Player;
import java.util.function.Function;
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
        return
                this.testOverRange(x -> x, x -> j, player) ||
                this.testOverRange(x -> i, x -> x, player) ||
                (i == this.diag(j) && this.testOverRange(this::diag, this::diag, player)) ||
                (i == this.anti(j) && this.testOverRange(this::diag, this::anti, player));
    }

    private boolean testOverRange(Function<Integer, Integer> fI, Function<Integer, Integer> fJ, Player player) {
        if (this.testOverRange(x -> this.cells[fI.apply(x)][fJ.apply(x)] == player)) {
            this.winning = IntStream.range(0, this.getSize())
                    .mapToObj(x -> new Coordinate2D(fI.apply(x), fJ.apply(x)))
                    .toArray(Coordinate2D[]::new);
            return true;
        }
        return false;
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
