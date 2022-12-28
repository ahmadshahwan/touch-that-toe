package com.acme.tictactoe;

import java.io.PrintStream;
import java.util.Scanner;

public class Game {

    private Board board;
    private Integer next;

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Game().play();
    }

    public Game() {
        this.board = new Board();
    }

    public void play() {
        boolean finished = false;
        Player player = null;
        int size = this.board.getSize();
        print();
        while (!finished) {
            player = player == Player.X ? Player.O : Player.X;
            System.out.printf("Player %s turns.\n", player);
            System.out.println("Please enter the label of the cell where to place your mark.");
            while (!readChoice()) {
                System.out.println("Bad input. Please make your choice.");
            }
            do {
                print();
                System.out.println("Confirm your choice by pressing Enter. You can enter another choice otherwise.");
            } while (!readConfirmation());
            int i = this.next / size;
            int j = this.next % size;
            finished = this.board.place(i, j, player);
            this.next = null;
            print();
            if (finished) {
                System.out.printf("Player %s wins.\n", player);
            } else {
                finished = board.isFull();
                if (finished) {
                    System.out.println("Board is full. Game over.");
                }
            }
        }
    }


    public boolean readNext(boolean confirmation) {
        String line = this.scanner.nextLine().trim();
        if (line.isBlank()) {
            return confirmation;
        }
        char c = line.charAt(0);
        if (c >= '1' && c <= '9') {
            this.next = c - '1';
        } else if (c >= 'a' && c <= 'z') {
            this.next = c - 'a';
        } else if (c >= 'A' && c <= 'Z') {
            this.next = c - 'A';
        } else {
            return false;
        }
        if (this.next >= this.board.getSize() * this.board.getSize()) {
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
        this.print(System.out);
    }

    public void print(PrintStream out) {
        int size = this.board.getSize();
        out.println();
        for (int i = 0; i < size; i++) {
            out.print(" |");
            for (int j = 0; j < size; j++) {
                if (this.next != null) {
                    int iNext = this.next / size;
                    int jNext = this.next % size;
                    if (i == iNext && j == jNext) {
                        out.print(" # ");
                        continue;
                    }
                }
                Player cell = this.board.at(i, j);
                if (cell == null) {
                    int label = i * size + j;
                    out.print(size > 3 ? " %c ".formatted('a' + label) : " %d ".formatted(label + 1));
                } else {
                    out.printf(" %s ", cell);
                }
            }
            out.println("|");
        }
    }
}
