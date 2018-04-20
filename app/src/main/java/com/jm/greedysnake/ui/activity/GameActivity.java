package com.jm.greedysnake.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.model.GameModel;
import com.jm.greedysnake.mvp.presenter.GamePresenter;
import com.jm.greedysnake.mvp.view.GameView;
import com.jm.greedysnake.ui.base.BaseActivity;
import com.jm.snakepanelview.GameType;
import com.jm.snakepanelview.GridPosition;
import com.jm.snakepanelview.SnakePanelView;
import com.jm.snakepanelview.SnakePanelViewListener;

import java.util.List;

public class GameActivity
        extends BaseActivity<GameModel, GameView, GamePresenter>
        implements GameView,
        SnakePanelViewListener {
    private Toolbar toolbar;
    private SnakePanelView snakePanelView;
    private TextView result;
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

        getPresenter().updateSnakePanelView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        snakePanelView = findViewById(R.id.snakePanelView);
        snakePanelView.setSnakePanelViewListener(this);
        result = findViewById(R.id.result);

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
    public void onStartGame() {
        getPresenter().playThemeMusic();
    }

    @Override
    public void onPauseGame() {
        getPresenter().pauseThemeMusic();
    }

    @Override
    public void onEatFood(final int snakeLength) {
        getPresenter().eatFood();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                result.setText(String.valueOf(snakeLength));
//            }
//        });
    }

    @Override
    public void onEatSelf() {
        getPresenter().eatSelf();
    }

    @Override
    public void onHitBoundary() {
        getPresenter().hitBoundary();
    }

    @Override
    public void onMove() {
//        getPresenter().move();
    }

    @Override
    public void onGenerateFood(final List<GridPosition> foodPositions) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                result.setText(foodPositions.toString());
            }
        });
    }

    @Override
    public SnakePanelView onGetSnakePanelView() {
        return snakePanelView;
    }

    @Override
    public Intent onGetIntent() {
        return getIntent();
    }
}
