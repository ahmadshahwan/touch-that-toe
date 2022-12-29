package com.acme.tictactoe;

public interface Board<T extends Coordinate> {

    int DEFAULT_SIZE = 3;

    int getSize();

    boolean place(T position, Player player);

    Player at(T position);

    boolean isFull();

    int dimensions();
}
