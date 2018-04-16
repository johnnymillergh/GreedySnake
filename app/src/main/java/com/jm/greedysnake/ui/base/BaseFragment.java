package com.jm.greedysnake.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jm.greedysnake.mvp.base.BaseModel;
import com.jm.greedysnake.mvp.base.BasePresenter;
import com.jm.greedysnake.mvp.base.BaseView;

/**
 * Created by Johnny on 1/31/2018.
 */

public abstract class BaseFragment<M extends BaseModel, V extends BaseView, P extends BasePresenter<M, V>> extends
        Fragment {
    private P presenter;
    private V view;
//    // Status of visibility of fragment
//    protected boolean isVisible;
//    // Status of preparation of fragment
//    public boolean isPrepared = false;
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInVisible();
//        }
//    }
//
//    protected abstract void onInVisible();
//
//    protected void onVisible(){
//        loadData();
//    }
//
//    protected abstract void loadData();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (view == null) {
            view = createView();
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null && view != null) {
            presenter.attachView(view);
        }
        notifyFinishAttachingView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null && view != null) {
            presenter.detachView();
        }
    }

    public abstract V createView();

    public abstract P createPresenter();

    public abstract void notifyFinishAttachingView();

    public P getPresenter() {
        return presenter;
    }

    public V getViewOfMVP() {
        return view;
    }
}
