package com.jm.greedysnake.mvp.presenter;

import com.jm.greedysnake.mvp.base.BasePresenter;
import com.jm.greedysnake.mvp.model.GameModel;
import com.jm.greedysnake.mvp.view.GameView;

public class GamePresenter extends BasePresenter<GameModel, GameView> {
    private GameModel gameModel;
    private GameView gameView;

    public GamePresenter() {
        gameModel = new GameModel();
        super.BasePresenter(gameModel);
    }
}
