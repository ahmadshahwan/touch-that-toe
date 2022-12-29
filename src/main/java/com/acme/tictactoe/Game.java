package com.acme.tictactoe;

import com.acme.tictactoe.square.Engine2D;
import com.acme.tictactoe.cube.Engine3D;
import java.util.Scanner;

public class Game {

    private Engine<? extends Coordinate> engine;

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Game().play();
    }

    public Game() {
    }

    public void play() {
        while (this.engine == null) {
            this.chooseBoard();
        }
        boolean finished = false;
        this.print();
        while (!finished) {
            Player currentPlayer = this.engine.flipPlayer();
            System.out.printf("Player %s turns.\n", currentPlayer);
            System.out.println("Please enter the label of the cell where to place your mark.");
            while (!readChoice()) {
                System.out.println("Bad input. Please make your choice.");
            }
            do {
                this.print();
                System.out.println("Confirm your choice by pressing Enter. You can enter another choice otherwise.");
            } while (!readConfirmation());
            finished = this.engine.place();
            this.print();
            if (finished) {
                System.out.printf("Player %s wins.\n", currentPlayer);
            } else {
                finished = this.engine.isGameOver();
                if (finished) {
                    System.out.println("Board is full. Game over.");
                }
            }
        }
    }

    private void chooseBoard() {
        System.out.println("Please choose board type: 2 for 2D or 3 for 3D.");
        String line = this.scanner.nextLine().trim();
        char c = line.isEmpty() ? '\0' : line.charAt(0);
        if (c == '2') {
            this.engine = new Engine2D();
            return;
        }
        if (c == '3') {
            this.engine = new Engine3D();
            return;
        }
        System.out.println("Bad input.");
    }

    public boolean readNext(boolean confirmation) {
        String line = this.scanner.nextLine().trim();
        if (line.isBlank()) {
            return confirmation;
        }
        int k = 0;
        char c = line.charAt(0);
        if (this.engine.dimensions() == 3) {
            if (c >= 'a' && c <= 'c') {
                k = c - 'a';
            } else if (c >= 'A' && c <= 'C') {
                k = c - 'A';
            } else {
                return false;
            }
            c = ' ';
            for (int i = 1; i < line.length() && c == ' '; i++) {
                c = line.charAt(i);
            }
        }
        int ij;
        if (c >= '1' && c <= '9') {
            ij = c - '1';
        } else if (c >= 'a' && c <= 'z') {
            ij = c - 'a';
        } else if (c >= 'A' && c <= 'Z') {
            ij = c - 'A';
        } else {
            return false;
        }
        if (ij >= this.boardSize() * this.boardSize()) {
            return false;
        }
        int i = ij / this.boardSize();
        int j = ij % this.boardSize();
        try {
            this.engine.updateNext(i, j, k);
        } catch (CellNotEmptyException e) {
            return false;
        }
        return !confirmation;
    }

    public boolean readConfirmation() {
        return this.readNext(true);
    }

    public boolean readChoice() {
        return this.readNext(false);
    }


    public void print() {
        this.engine.print(System.out);
    }

    private int boardSize() {
        return this.engine.boardSize();
    }

}
