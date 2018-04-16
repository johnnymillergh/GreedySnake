package com.jm.greedysnake.ui.activity;

import android.os.Bundle;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.model.MainModel;
import com.jm.greedysnake.mvp.presenter.MainPresenter;
import com.jm.greedysnake.mvp.view.MainView;
import com.jm.greedysnake.ui.base.BaseActivity;

public class MainActivity
        extends BaseActivity<MainModel, MainView, MainPresenter>
        implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public MainView createView() {
        return null;
    }

    @Override
    public MainPresenter createPresenter() {
        return null;
    }
}
