package com.acme.tictactoe.i18n;

public class FrText extends Text {

    public FrText() {
        this.playerTurn = "C'est au tour du joueur %s.\n";
        this.enterLabel2D = "Entrer le label du prochain placement.";
        this.enterLabel3D = "Entrer le label du niveau (une lettre) suivi par le label de la cellule (un chiffre) de votre prochain placement.";
        this.badInput = "Mauvaise entrée. Refaire le choix.";
        this.confirmChoice = "Confirmer le choix en appuyant sur Enter. Sinon, faire un autre choix.";
        this.playerWins = "Joueur %s gagne.\n";
        this.boardIsFull = "Tableau est tout rempli.";
        this.boardChoice = "Choisir le type du tableau : 2 pour 2D ou 3 pour 3D.";
    }
}
