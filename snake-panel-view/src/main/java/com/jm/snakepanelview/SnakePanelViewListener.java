package com.jm.snakepanelview;

import java.util.List;

public interface SnakePanelViewListener {
    void onStartGame();

    void onPauseGame();

    void onEatFood(int snakeLength);

    void onEatSelf();

    void onHitBoundary();

    void onMove();

    void onGenerateFood(List<GridPosition> foodPositions);
}
