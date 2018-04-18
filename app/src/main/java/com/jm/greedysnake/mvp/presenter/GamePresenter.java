package com.jm.greedysnake.mvp.presenter;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.jm.greedysnake.R;
import com.jm.greedysnake.mvp.base.BasePresenter;
import com.jm.greedysnake.mvp.model.GameModel;
import com.jm.greedysnake.mvp.view.GameView;
import com.jm.greedysnake.util.ApplicationUtil;

import java.util.HashMap;

public class GamePresenter extends BasePresenter<GameModel, GameView> {
    private GameModel gameModel;
    private GameView gameView;

    MediaPlayer themeMusicPlayer;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> hashMap;

    public GamePresenter() {
        gameModel = new GameModel();
        super.BasePresenter(gameModel);

        initSound();
    }

    private void initSound() {
        SoundPool.Builder builder = new SoundPool.Builder();
        // Set the amount of the file going to play
        builder.setMaxStreams(7);

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
        builder.setAudioAttributes(attrBuilder.build());

        soundPool = builder.build();
        hashMap = new HashMap<>();
        hashMap.put(2, soundPool.load(ApplicationUtil.context, R.raw._02_collect_coin_or_treasure, 1));
        hashMap.put(3, soundPool.load(ApplicationUtil.context, R.raw._03_car_racing_start, 1));
        hashMap.put(4, soundPool.load(ApplicationUtil.context, R.raw._04_collect_pick_up, 1));
        hashMap.put(5, soundPool.load(ApplicationUtil.context, R.raw._05_negative_lose_life, 1));
        hashMap.put(6, soundPool.load(ApplicationUtil.context, R.raw._06_menu_navigate, 1));
        hashMap.put(7, soundPool.load(ApplicationUtil.context, R.raw._07_blip_generic, 1));
    }


    public void playThemeMusic() {
        themeMusicPlayer = MediaPlayer.create(ApplicationUtil.getContext(), R.raw._01_snake_bgm);
        themeMusicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        themeMusicPlayer.setLooping(true);
        themeMusicPlayer.start();
    }

    public void pauseThemeMusic() {
        themeMusicPlayer.pause();
    }

    public void stopPlayingThemeMusic() {
        themeMusicPlayer.stop();
    }

    public void eatFood() {
        playSoundEffect(3);
    }

    public void eatSelf() {
        playSoundEffect(5);
        stopPlayingThemeMusic();
    }

    public void hitBoundary() {
        playSoundEffect(5);
        stopPlayingThemeMusic();
    }

    public void move() {
        playSoundEffect(7);
    }

    private void playSoundEffect(int mediaId) {
        AudioManager audioManager = (AudioManager) ApplicationUtil.context.getSystemService(Context.AUDIO_SERVICE);
        // Get max volume
        float streamVolumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeMax;

        // Play sound
        switch (mediaId) {
            case 2:
                soundPool.play(hashMap.get(1), volume, volume, 1, -1, 1.0f);
                break;
            case 3:
                soundPool.play(hashMap.get(2), volume, volume, 1, 0, 1.0f);
                break;
            case 4:
                soundPool.play(hashMap.get(3), volume, volume, 1, 0, 1.0f);
                break;
            case 5:
                soundPool.play(hashMap.get(4), volume, volume, 1, 0, 1.0f);
                break;
            case 6:
                soundPool.play(hashMap.get(5), volume, volume, 1, 0, 1.0f);
                break;
            case 7:
                soundPool.play(hashMap.get(6), volume, volume, 1, 0, 1.0f);
                break;
            default:
        }
    }
}
