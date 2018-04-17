package com.jm.greedysnake.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.model.MainModel;
import com.jm.greedysnake.mvp.presenter.MainPresenter;
import com.jm.greedysnake.mvp.view.MainView;
import com.jm.greedysnake.ui.base.BaseActivity;
import com.jm.snakepanelview.GameType;
import com.jm.snakepanelview.SnakePanelView;

public class MainActivity
        extends BaseActivity<MainModel, MainView, MainPresenter>
        implements MainView {
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
    public MainView createView() {
        return this;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
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
}
