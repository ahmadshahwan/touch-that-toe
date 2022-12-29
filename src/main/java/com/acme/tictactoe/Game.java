package com.acme.tictactoe;

import com.acme.tictactoe.cube.Board3D;
import com.acme.tictactoe.cube.Coordinate3D;
import com.acme.tictactoe.square.Board2D;
import com.acme.tictactoe.square.Coordinate2D;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {

    private Board<? extends Coordinate> board;
    private Coordinate next;

    private Player currentPlayer;

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Game().play();
    }

    public Game() {
    }

    public void play() {
        while (this.board == null) {
            this.chooseBoard();
        }
        boolean finished = false;
        print();
        while (!finished) {
            this.currentPlayer = this.currentPlayer == Player.X ? Player.O : Player.X;
            System.out.printf("Player %s turns.\n", this.currentPlayer);
            System.out.println("Please enter the label of the cell where to place your mark.");
            while (!readChoice()) {
                System.out.println("Bad input. Please make your choice.");
            }
            do {
                print();
                System.out.println("Confirm your choice by pressing Enter. You can enter another choice otherwise.");
            } while (!readConfirmation());
            finished = this.place();
            this.next = null;
            print();
            if (finished) {
                System.out.printf("Player %s wins.\n", this.currentPlayer);
            } else {
                finished = board.isFull();
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
            this.board = new Board2D();
            return;
        }
        if (c == '3') {
            this.board = new Board3D();
            return;
        }
        System.out.println("Bad input.");
    }

    private boolean place() {
        if (
                this.board instanceof Board2D board2D &&
                this.next instanceof Coordinate2D coordinate2D) {
            return board2D.place(coordinate2D, this.currentPlayer);
        }
        if (
                this.board instanceof Board3D board3D &&
                this.next instanceof Coordinate3D coordinate3D) {
            return board3D.place(coordinate3D, this.currentPlayer);
        }
        throw new IllegalStateException("Cannot place next move due to mismatching board move types.");
    }

    private boolean is3D() {
        return this.board instanceof Board3D;
    }

    public boolean readNext(boolean confirmation) {
        String line = this.scanner.nextLine().trim();
        if (line.isBlank()) {
            return confirmation;
        }
        int k = 0;
        char c = line.charAt(0);
        if (this.is3D()) {
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
        if (ij >= this.board.getSize() * this.board.getSize()) {
            return false;
        }
        int i = ij / this.board.getSize();
        int j = ij % this.board.getSize();
        try {
            this.updateNext(i, j, k);
        } catch (CellNotEmptyException e) {
            return false;
        }
        return !confirmation;
    }

    private void updateNext(int i, int j, int k) {
        if (this.board instanceof Board2D board2D) {
            Coordinate2D coordinate = new Coordinate2D(i, j);
            if (board2D.at(coordinate) != null) {
                throw new CellNotEmptyException(coordinate);
            }
            this.next = coordinate;
        } else if (this.board instanceof Board3D board3D) {
            Coordinate3D coordinate = new Coordinate3D(i, j, k);
            if (board3D.at(coordinate) != null) {
                throw new CellNotEmptyException(coordinate);
            }
            this.next = coordinate;
        } else {
            throw new IllegalStateException("Board type cannot be determined");
        }
    }

    public boolean readConfirmation() {
        return this.readNext(true);
    }

    public boolean readChoice() {
        return this.readNext(false);
    }


    public void print() {
        if (this.board instanceof Board2D board2D) {
            print(System.out, board2D);
        } else if (this.board instanceof Board3D board3D) {
            this.print(System.out, board3D);
        }
    }

    private void print(PrintStream out, Board3D board) {
        int size = board.getSize();
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
                    Coordinate3D current = new Coordinate3D(i, j, k);
                    Player cell = board.at(current);
                    int label = i * size + j + 1;
                    this.print(out, current, cell, label);
                }
                out.print("|");
                out.print(" ".repeat(8));
            }
            out.println();
        }
    }
    private void print(PrintStream out, Board2D board) {
        int size = board.getSize();
        out.println();
        for (int i = 0; i < size; i++) {
            out.print(" |");
            for (int j = 0; j < size; j++) {
                Coordinate2D current = new Coordinate2D(i, j);
                Player cell = board.at(current);
                int label = i * size + j + 1;
                this.print(out, current, cell, label);
            }
            out.println("|");
        }
    }

    private void print(PrintStream out, Coordinate current, Player player, int label) {
        if (this.next !=null && this.next.equals(current)) {
            out.printf(">%s<", this.currentPlayer.toString().toLowerCase());
        } else if (player == null) {
            out.printf(" %d ", label);
        } else {
            out.printf(" %s ", player);
        }
    }
}
