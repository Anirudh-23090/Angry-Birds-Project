package com.badlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;

public class MainMenuScreen implements Screen {
    private final Main game;
    private final Texture backgroundTexture, playTexture, musicTexture, musicOffTexture;
    private final Button playButton, musicButton;
    private final Vector2 touchPos;

    public MainMenuScreen(final Main game) {
        this.game = game;
        backgroundTexture = new Texture("background1.jpg");
        playTexture = new Texture("playButton.png");
        musicTexture = new Texture("music.png");
        musicOffTexture = new Texture("musicOff.png");
        this.touchPos = new Vector2();

        playButton = new Button(playTexture, 463.5f, 218.5f * 1.333f, 343, 120 * 1.333f);
        musicButton = new Button(musicTexture, 5, 635 * 1.333f, 100, 80 * 1.333f);

        game.viewport.setWorldSize(1280, 960);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX(), y = Gdx.input.getY();
            touchPos.set(x, y);
            game.viewport.unproject(touchPos);

            float delta = Gdx.graphics.getDeltaTime();
            game.addTimer(delta);
            if (game.getTimer() > 0.1f) {
                if (playButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new MapScreen(game));
                    dispose();
                } else if (musicButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.turnMusic();
                }
            }
        }
    }

    private void logic() {
        if (game.musicPlaying) {
            game.music.play();
        } else {
            game.music.pause();
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        playButton.draw(game);
        musicButton.draw(game);
        if (!game.musicPlaying) {
            game.batch.draw(musicOffTexture, musicButton.getRect().x, musicButton.getRect().y, musicButton.getRect().width, musicButton.getRect().height);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        game.setPause(true);
    }

    @Override
    public void resume() {
        game.setPause(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
