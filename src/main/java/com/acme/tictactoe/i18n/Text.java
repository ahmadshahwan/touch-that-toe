package com.acme.tictactoe.i18n;

public abstract class Text {

    public String playerTurn = "Player %s's turns.\n";
    public String enterLabel2D = "Please enter the label of the cell where to place your mark.";
    public String enterLabel3D = "Please enter the label of the level (a letter), followed by that of the cell (a digit) where to place your mark.";
    public String badInput = "Bad input. Please make your choice.";
    public String confirmChoice = "Confirm your choice by pressing Enter. You can enter another choice otherwise.";
    public String playerWins = "Player %s wins.\n";
    public String boardIsFull = "Board is full. Game over.";
    public String boardChoice = "Please choose board type: 2 for 2D or 3 for 3D.";


    public static EnText en() {
        return new EnText();
    }

    public static FrText fr() {
        return new FrText();
    }
}
