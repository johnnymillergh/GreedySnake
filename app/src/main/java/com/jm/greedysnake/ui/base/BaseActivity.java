package com.jm.greedysnake.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jm.greedysnake.mvp.base.BaseModel;
import com.jm.greedysnake.mvp.base.BasePresenter;
import com.jm.greedysnake.mvp.base.BaseView;

/**
 * Created by Johnny on 1/19/2018.
 */

public abstract class BaseActivity<M extends BaseModel, V extends BaseView, P extends BasePresenter<M, V>> extends
        AppCompatActivity {
    private P presenter;
    private V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (view == null) {
            view = createView();
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null && view != null) {
            presenter.attachView(view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null && view != null) {
            presenter.detachView();
        }
    }

    public abstract V createView();

    public abstract P createPresenter();

    public P getPresenter() {
        return presenter;
    }

    public V getView() {
        return view;
    }
}
