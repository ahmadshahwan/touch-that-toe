package com.acme.tictactoe;

import com.acme.tictactoe.i18n.Text;
import com.acme.tictactoe.square.Engine2D;
import com.acme.tictactoe.cube.Engine3D;
import java.util.Scanner;

public class Game {

    private Engine<? extends Coordinate> engine;

    private final Scanner scanner = new Scanner(System.in);

    private final Text text;

    public static void main(String[] args) {
        new Game().play();
    }

    public Game() {
        this(Text.en());
    }

    public Game(Text text) {
        this.text = text;
    }

    public void play() {
        while (this.engine == null) {
            this.chooseBoard();
        }
        boolean finished = false;
        this.print();
        while (!finished) {
            Player currentPlayer = this.engine.flipPlayer();
            System.out.printf(this.text.playerTurn, currentPlayer);
            if (this.engine.dimensions() == 3) {
                System.out.println(this.text.enterLabel3D);
            } else {
                System.out.println(this.text.enterLabel2D);
            }
            while (!readChoice()) {
                System.out.println(this.text.badInput);
            }
            do {
                this.print();
                System.out.println(this.text.confirmChoice);
            } while (!readConfirmation());
            finished = this.engine.place();
            this.print();
            if (finished) {
                System.out.printf(this.text.playerWins, currentPlayer);
            } else {
                finished = this.engine.isGameOver();
                if (finished) {
                    System.out.println(this.text.boardIsFull);
                }
            }
        }
    }

    private void chooseBoard() {
        System.out.println(this.text.boardChoice);
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
        System.out.println(this.text.badInput);
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
