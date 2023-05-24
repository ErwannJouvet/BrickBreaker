package com.example.myapplication.Interface;

public interface IGameListener {

    public void onPlayGame(boolean play);

    public void onSelectLevel(int level);

    public void onSelectionLevel(boolean selectionLevel);

    // over: 0 en cours, 1 terminé
    // result: 0 perdu, 1 gagné
    // score : entier
    public void onGameOver(boolean over, boolean result, int score);

    public void onHome();
}
