package com.badlogic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;
    public Music music;
    public boolean musicPlaying;
    public boolean pause;
    float timer;

    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(1280, 720);
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5F);
        musicPlaying = true;
        timer = 1;
        this.setScreen(new MainMenuScreen(this));
    }

    public void turnMusic() {
        if (musicPlaying)
            musicPlaying = false;
        else
            musicPlaying = true;
    }

    public float getTimer() {
        return timer;
    }

    public void addTimer(float t) {
        timer += t;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void render() {

        super.render();
    }

    public void dispose() {
        batch.dispose();
    }

}
