package com.jm.greedysnake.mvp.base;

/**
 * Created by Johnny on 1/18/2018.
 */

public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    private M model;
    private V view;

    public void BasePresenter(M model) {
        this.model = model;
    }

    public V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
        model.cancel();
    }
}
