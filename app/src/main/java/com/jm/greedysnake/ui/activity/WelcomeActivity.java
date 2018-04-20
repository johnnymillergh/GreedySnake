package com.jm.greedysnake.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.model.WelcomeModel;
import com.jm.greedysnake.mvp.presenter.WelcomePresenter;
import com.jm.greedysnake.mvp.view.WelcomeView;
import com.jm.greedysnake.ui.base.BaseActivity;
import com.jm.greedysnake.ui.config.GameConfig;
import com.jm.greedysnake.ui.fragment.DifficultyDialogFragment;

public class WelcomeActivity
        extends BaseActivity<WelcomeModel, WelcomeView, WelcomePresenter>
        implements WelcomeView,
        DifficultyDialogFragment.DifficultyDialogFragmentCallbackListener {
    private TextView welcome;

    private int difficulty = GameConfig.DIFFICULTY_EASY;
    private int mode = GameConfig.MODE_SINGLE;
    private int skinColor = GameConfig.DEFAULT_SKIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        welcome = findViewById(R.id.welcome);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(600);
        alphaAnimation.setStartOffset(600);
        welcome.setText(R.string.welcome);
        welcome.startAnimation(alphaAnimation);
    }

    @Override
    public WelcomeView createView() {
        return this;
    }

    @Override
    public WelcomePresenter createPresenter() {
        return new WelcomePresenter();
    }

    public void onClickDifficulty(View view) {
        DifficultyDialogFragment difficultyDialogFragment = new DifficultyDialogFragment();
        difficultyDialogFragment.show(getSupportFragmentManager());
    }

    public void onClickMode(View view) {

    }

    public void onClickSkin(View view) {

    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("mode", mode);
        intent.putExtra("skinColor", skinColor);
        startActivity(intent);
    }

    @Override
    public int onGetDifficulty() {
        return difficulty;
    }

    @Override
    public void onSetDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
