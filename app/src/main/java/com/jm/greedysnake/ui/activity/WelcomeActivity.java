package com.jm.greedysnake.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.model.WelcomeModel;
import com.jm.greedysnake.mvp.presenter.WelcomePresenter;
import com.jm.greedysnake.mvp.view.WelcomeView;
import com.jm.greedysnake.ui.base.BaseActivity;

public class WelcomeActivity
        extends BaseActivity<WelcomeModel, WelcomeView, WelcomePresenter>
        implements WelcomeView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public WelcomeView createView() {
        return this;
    }

    @Override
    public WelcomePresenter createPresenter() {
        return new WelcomePresenter();
    }
}
