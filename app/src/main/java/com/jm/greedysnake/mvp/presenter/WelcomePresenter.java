package com.jm.greedysnake.mvp.presenter;

import com.jm.greedysnake.mvp.base.BasePresenter;
import com.jm.greedysnake.mvp.model.WelcomeModel;
import com.jm.greedysnake.mvp.view.WelcomeView;

public class WelcomePresenter extends BasePresenter<WelcomeModel, WelcomeView> {
    private WelcomeModel welcomeModel;
    private WelcomeView welcomeView;

    public WelcomePresenter() {
        welcomeModel = new WelcomeModel();
        super.BasePresenter(welcomeModel);
    }
}
