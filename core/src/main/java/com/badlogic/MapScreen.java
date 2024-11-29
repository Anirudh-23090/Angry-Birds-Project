package com.badlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;

import java.io.FileReader;

public class MapScreen implements Screen {
    private final Main game;
    private final Texture backgroundTexture, level1Texture, level2Texture, level3Texture, backTexture, loadTexture;
    private final Button level1Button, level2Button, level3Button, backButton, level1LoadButton, level2LoadButton, level3LoadButton;
    private final Vector2 touchPos;

    public MapScreen(final Main game) {
        this.game = game;
        touchPos = new Vector2();
        backgroundTexture = new Texture("background2.png");
        level1Texture = new Texture("level1Button.png");
        level2Texture = new Texture("level2Button.png");
        level3Texture = new Texture("level3Button.png");
        backTexture = new Texture("backButton.png");
        loadTexture = new Texture("load.png");

        level1Button = new Button(level1Texture, 259, 286, 43, 50);
        level2Button = new Button(level2Texture, 355, 286, 42, 50);
        level3Button = new Button(level3Texture, 451, 286, 42, 50);
        level1LoadButton = new Button(loadTexture, 259, 208, 42, 50);
        level2LoadButton = new Button(loadTexture, 355, 208, 42, 50);
        level3LoadButton = new Button(loadTexture, 451, 208, 42, 50);
        backButton = new Button(backTexture, 7, 7, 63, 63);

        game.viewport.setWorldSize(750, 573);
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

            System.out.println(game.getTimer());
            if (game.getTimer() > 0.1f) {
                if (level1Button.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new Level1Screen(game));
                    dispose();
                } else if (level2Button.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new Level2Screen(game));
                    dispose();
                } else if (level3Button.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new Level3Screen(game));
                    dispose();
                } else if (backButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                } else if (level1LoadButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    load(1);
                    dispose();
                } else if (level2LoadButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                     load(2);
                    dispose();
                } else if (level3LoadButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    load(3);
                    dispose();
                }
            }
        }
    }

    private void load(int level) {
        if (level == 1) {
            Serialisation serialize = get(Constants.filepath1);
            game.setScreen(new Level1Screen(game, serialize));
            dispose();
        } else if (level == 2) {
            Serialisation serialize = get(Constants.filepath2);
            game.setScreen(new Level2Screen(game, serialize));
            dispose();
        } else if (level == 3) {
            Serialisation serialize = get(Constants.filepath3);
            game.setScreen(new Level3Screen(game, serialize));
            dispose();
        }
    }

    private Serialisation get(String filepath) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        try (FileReader reader = new FileReader(filepath)) {
            Serialisation state = json.fromJson(Serialisation.class, reader);
            return state;
        } catch (Exception e) {
            System.out.println("Error loading file");
        }
        return null;
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
        level1Button.draw(game); level2Button.draw(game); level3Button.draw(game); backButton.draw(game); level1LoadButton.draw(game); level2LoadButton.draw(game); level3LoadButton.draw(game);

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
