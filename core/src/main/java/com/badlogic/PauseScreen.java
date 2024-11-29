package com.badlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.FileWriter;

public class PauseScreen implements Screen {
    private final Main game;
    private final int level;
    private final Screen previousScreen;
    private final Texture level1Texture, level2Texture, level3Texture, backgroundTexture, resumeTexture, mainMenuTexture, saveAndExitTexture;
    private final Button resume, mainMenu, saveAndExit;
    private final Vector2 touchPos;

    public PauseScreen(final Main game, int level, Screen screen) {
        this.game = game;
        this.level = level;
        this.previousScreen = screen;
        touchPos = new Vector2();
        level1Texture = new Texture(Gdx.files.internal("level1Background.png"));
        level2Texture = new Texture(Gdx.files.internal("level2Background.png"));
        level3Texture = new Texture(Gdx.files.internal("level3Background.png"));
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        resumeTexture = new Texture(Gdx.files.internal("pause.png"));
        saveAndExitTexture = new Texture(Gdx.files.internal("saveAndExit.png"));
        mainMenuTexture = new Texture(Gdx.files.internal("mainMenu.png"));

        resume = new Button(resumeTexture, 760, 500, 80, 80);
        mainMenu = new Button(mainMenuTexture, 760, 300, 80, 80);
        saveAndExit = new Button(saveAndExitTexture, 760, 400, 80, 80);

        game.viewport.setWorldSize(1600, 900);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
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

            System.out.println(game.getTimer());
            if (game.getTimer() > 0.1f) {
                if (resume.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(previousScreen);
                } else if (mainMenu.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                } else if (saveAndExit.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    save(previousScreen);
                    game.setScreen(new MapScreen(game));
                    dispose();
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

        if (level == 1) {
            game.batch.draw(level1Texture, 0, 0, worldWidth, worldHeight);
        } else if (level == 2) {
            game.batch.draw(level2Texture, 0, 0, worldWidth, worldHeight);
        } else if (level == 3) {
            game.batch.draw(level3Texture, 0, 0, worldWidth, worldHeight);
        }

        game.batch.draw(backgroundTexture, 424.5f, 174, 751, 552);
        resume.draw(game); saveAndExit.draw(game); mainMenu.draw(game);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    private void save(Screen screen) {
        if (screen instanceof Level1Screen level1Screen) {
            level1Screen.save();
        } else if (screen instanceof Level2Screen level2Screen) {
            level2Screen.save();
        } else if (screen instanceof Level3Screen level3Screen) {
            level3Screen.save();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
