package com.jm.greedysnake.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;

import com.jm.greedysnake.R;
import com.jm.greedysnake.ui.config.GameConfig;

public class DifficultyDialogFragment extends DialogFragment {
    private RadioButton easy;
    private RadioButton normal;
    private RadioButton hard;

    private DifficultyDialogFragmentCallbackListener mListener;

    public void show(FragmentManager manager) {
        show(manager, "DifficultyDialogFragment");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_difficulty_dialog, null);
        int difficulty = mListener.onGetDifficulty();
        initView(view);
        updateView(difficulty);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (easy.isChecked()) {
                    mListener.onSetDifficulty(GameConfig.DIFFICULTY_EASY);
                } else if (normal.isChecked()) {
                    mListener.onSetDifficulty(GameConfig.DIFFICULTY_NORMAL);
                } else if (hard.isChecked()) {
                    mListener.onSetDifficulty(GameConfig.DIFFICULTY_HARD);
                }
            }
        }).setNegativeButton(R.string.back, null);
        return builder.create();
    }

    private void updateView(int difficulty) {
        switch (difficulty) {
            case GameConfig.DIFFICULTY_EASY:
                easy.setChecked(true);
                break;
            case GameConfig.DIFFICULTY_NORMAL:
                normal.setChecked(true);
                break;
            case GameConfig.DIFFICULTY_HARD:
                hard.setChecked(true);
                break;
        }
    }

    private void initView(View rootView) {
        easy = rootView.findViewById(R.id.easy);
        normal = rootView.findViewById(R.id.normal);
        hard = rootView.findViewById(R.id.hard);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DifficultyDialogFragmentCallbackListener) {
            mListener = (DifficultyDialogFragmentCallbackListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement DifficultyDialogFragmentCallbackListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DifficultyDialogFragmentCallbackListener {
        int onGetDifficulty();

        void onSetDifficulty(int difficulty);
    }
}
