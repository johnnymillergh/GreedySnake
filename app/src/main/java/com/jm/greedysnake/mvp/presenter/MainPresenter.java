package com.jm.greedysnake.mvp.presenter;

import com.jm.greedysnake.mvp.base.BasePresenter;
import com.jm.greedysnake.mvp.model.MainModel;
import com.jm.greedysnake.mvp.view.MainView;

public class MainPresenter extends BasePresenter<MainModel, MainView> {
    private MainModel mainModel;
    private MainView mainView;

    public MainPresenter() {
        mainModel = new MainModel();
        super.BasePresenter(mainModel);
    }
}
