package com.jm.greedysnake.mvp.view;

import android.content.Intent;

import com.jm.greedysnake.mvp.base.BaseView;
import com.jm.snakepanelview.SnakePanelView;

public interface GameView extends BaseView {
    SnakePanelView onGetSnakePanelView();

    Intent onGetIntent();
}
