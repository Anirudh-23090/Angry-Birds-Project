package com.badlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class WinScreen implements Screen {
    private final int level;
    private final Main game;
    private final Texture level1Texture, level2Texture, level3Texture, winTexture, clearTexture, retryTexture, mainMenuTexture, nextLevelTexture, happyBirdTexture, starsTexture;;
    private final Button retryButton, mainMenuButton, nextButton;
    private final Vector2 touchPos;
    private final SpriteBatch spriteBatch;

    public WinScreen(final Main game, int level) {
        this.game = game;
        this.level = level;
        level1Texture = new Texture(Gdx.files.internal("level1Background.png"));
        level2Texture = new Texture(Gdx.files.internal("level2Background.png"));
        level3Texture = new Texture(Gdx.files.internal("level3Background.png"));
        winTexture = new Texture(Gdx.files.internal("win.png"));
        clearTexture = new Texture(Gdx.files.internal("clear.png"));
        retryTexture = new Texture(Gdx.files.internal("retry.png"));
        mainMenuTexture = new Texture(Gdx.files.internal("mainMenu.png"));
        nextLevelTexture = new Texture(Gdx.files.internal("next.png"));
        happyBirdTexture = new Texture(Gdx.files.internal("happyBird.png"));
        starsTexture = new Texture(Gdx.files.internal("stars.png"));

        touchPos = new Vector2();
        spriteBatch = new SpriteBatch();

        retryButton = new Button(retryTexture, 580, 210, 100, 80);
        mainMenuButton = new Button(mainMenuTexture, 750, 210, 100, 80);
        nextButton = new Button(nextLevelTexture, 920, 212, 93, 75);
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
                } else if (nextButton.contains(touchPos.x, touchPos.y)) {
                    if (this.level == 1) {
                        game.setScreen(new Level2Screen(game));
                        dispose();
                    } else if (this.level == 2) {
                        game.setScreen(new Level3Screen(game));
                        dispose();
                    } else {
                        game.setScreen(new FinishScreen(game));
                        dispose();
                    }
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

        game.batch.draw(winTexture, 424.5f, 174, 751, 552);
        game.batch.draw(clearTexture, 549.5f, 595, 501, 101);
        game.batch.draw(happyBirdTexture, 1100.5f, 290, 150, 150);
        game.batch.draw(starsTexture, 535, 410, 530, 180);

        retryButton.draw(game); mainMenuButton.draw(game); nextButton.draw(game);

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
