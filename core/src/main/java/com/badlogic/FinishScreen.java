package com.badlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class FinishScreen implements Screen {
    private final Main game;
    private final Texture wellDoneTexture, backgroundTexture, mainMenuTexture, finishTexture;
    private final Button mainMenuButton;
    private final Vector2 touchPos;
    private final SpriteBatch spriteBatch;

    public FinishScreen(final Main game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("winBackground.png"));
        finishTexture = new Texture(Gdx.files.internal("finish.png"));
        wellDoneTexture = new Texture(Gdx.files.internal("wellDone.png"));
        mainMenuTexture = new Texture(Gdx.files.internal("mainMenu.png"));

        touchPos = new Vector2();
        spriteBatch = new SpriteBatch();

        mainMenuButton = new Button(mainMenuTexture, 750 , 380, 100, 80);
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
                if (mainMenuButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
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
        spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        spriteBatch.begin();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, 1600, 900);
        game.batch.draw(finishTexture, 204 , 500, 1192, 121);
        game.batch.draw(wellDoneTexture, 532, 700 , 736, 121);
        mainMenuButton.draw(game);

        game.batch.end();
        spriteBatch.end();
    }

    @Override
    public void resize(int i, int i1) {
        game.viewport.update(i, i1, true);
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
