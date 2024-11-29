package com.badlogic;

import com.badlogic.Button;
import com.badlogic.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

public class LoseScreen implements Screen {
    private final Main game;
    private final Texture level1Texture, level2Texture, level3Texture, loseTexture, failedTexture, retryTexture, mainMenuTexture;
    private final Button retryButton, mainMenuButton;
    private final SpriteBatch spriteBatch;
    private final Vector2 touchPos;
    private final int level;

    public LoseScreen(final Main game, int level) {
        this.game = game;
        this.level = level;
        spriteBatch = new SpriteBatch();
        level1Texture = new Texture(Gdx.files.internal("level1Background.png"));
        level2Texture = new Texture(Gdx.files.internal("level2Background.png"));
        level3Texture = new Texture(Gdx.files.internal("level3Background.png"));
        loseTexture = new Texture(Gdx.files.internal("lose.png"));
        failedTexture = new Texture(Gdx.files.internal("failed.png"));
        retryTexture = new Texture(Gdx.files.internal("retry.png"));
        mainMenuTexture = new Texture(Gdx.files.internal("mainMenu.png"));
        retryButton = new Button(retryTexture, 645, 210, 100, 80);
        mainMenuButton = new Button(mainMenuTexture, 885, 210, 100, 80);
        touchPos = new Vector2();

        game.viewport.setWorldSize(1600, 900);
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
                if (retryButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    if (level == 1) {
                        game.setScreen(new Level1Screen(game));
                        dispose();
                    } else if (level == 2) {
                        game.setScreen(new Level2Screen(game));
                        dispose();
                    } else if (level == 3) {
                        game.setScreen(new Level3Screen(game));
                        dispose();
                    }
                } else if (mainMenuButton.contains(touchPos.x, touchPos.y)) {
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

        if (level == 1) {
            game.batch.draw(level1Texture, 0, 0, 1600, 900);
        } else if (level == 2) {
            game.batch.draw(level2Texture, 0, 0, 1600, 900);
        } else if (level == 3) {
            game.batch.draw(level3Texture, 0, 0, 1600, 900);
        }

        game.batch.draw(loseTexture, 424.5f, 174, 751, 552);
        game.batch.draw(failedTexture, 549.5f, 545, 501, 101);

        retryButton.draw(game); mainMenuButton.draw(game);

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
