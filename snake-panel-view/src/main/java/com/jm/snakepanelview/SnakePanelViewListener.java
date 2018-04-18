package com.jm.snakepanelview;

public interface SnakePanelViewListener {
    void onStartGame();

    void onPauseGame();

    void onEatFood();

    void onEatSelf();

    void onHitBoundary();

    void onMove();
}
