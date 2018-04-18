package com.jm.snakepanelview;

public interface SnakePanelViewListener {
    void onEatFood();

    void onEatSelf();

    void onHitBoundary();

    void onMove();
}
