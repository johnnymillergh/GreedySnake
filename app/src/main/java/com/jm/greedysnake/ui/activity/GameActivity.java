package com.jm.greedysnake.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.model.GameModel;
import com.jm.greedysnake.mvp.presenter.GamePresenter;
import com.jm.greedysnake.mvp.view.GameView;
import com.jm.greedysnake.ui.base.BaseActivity;
import com.jm.snakepanelview.GameType;
import com.jm.snakepanelview.SnakePanelView;
import com.jm.snakepanelview.SnakePanelViewListener;

public class GameActivity
        extends BaseActivity<GameModel, GameView, GamePresenter>
        implements GameView,
        SnakePanelViewListener{
    private Toolbar toolbar;
    private SnakePanelView snakePanelView;
    private Button startOrPause;
    private Button up;
    private Button right;
    private Button down;
    private Button left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        snakePanelView = findViewById(R.id.snakePanelView);
        startOrPause = findViewById(R.id.startOrPause);
        up = findViewById(R.id.up);
        right = findViewById(R.id.right);
        down = findViewById(R.id.down);
        left = findViewById(R.id.left);
    }

    @Override
    public GameView createView() {
        return this;
    }

    @Override
    public GamePresenter createPresenter() {
        return new GamePresenter();
    }

    public void onClickStartOrPause(View view) {
        snakePanelView.reStartGame();
    }

    public void onClickUp(View view) {
        snakePanelView.setSnakeDirection(GameType.TOP);
    }

    public void onClickRight(View view) {
        snakePanelView.setSnakeDirection(GameType.RIGHT);
    }

    public void onClickDown(View view) {
        snakePanelView.setSnakeDirection(GameType.BOTTOM);
    }

    public void onClickLeft(View view) {
        snakePanelView.setSnakeDirection(GameType.LEFT);
    }

    @Override
    public void onEatFood() {

    }

    @Override
    public void onEatSelf() {

    }

    @Override
    public void onHitBoundary() {

    }

    @Override
    public void onMove() {

    }
}
