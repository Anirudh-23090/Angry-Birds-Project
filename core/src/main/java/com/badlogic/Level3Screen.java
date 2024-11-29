package com.badlogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class Level3Screen implements Screen {
    private final Main game;
    private final Texture backgroundTexture, pauseTexture, retryTexture, pigTexture, redBirdTexture, yellowBirdTexture, bigRedBirdTexture, vertical1Texture, vertical2Texture, vertical3Texture, horizontal1Texture, horizontal2Texture, slingShotTexture, shieldPigTexture, kingPigTexture;
    private final World world;
    private final List<Bird> birds;
    private final List<Pig> pigs;
    private final List<Obstacle> obstacles;
    private final SlingShot slingShot;
    private final Ground ground;
    private final Button pauseButton, retryButton;
    private final Vector2 touchPos, start;
    private final Box2DDebugRenderer debugRenderer;
    private final SpriteBatch spriteBatch;
    private final ListenerClass ListenerClass;
    private boolean paused;
    private Bird launchBird;
    private float accumulator;

    public Level3Screen(final Main game) {
        this.game = game; this.paused = false; this.accumulator = 0;

        spriteBatch = new SpriteBatch();

        touchPos = new Vector2();

        backgroundTexture = new Texture(Gdx.files.internal("level3Background.png"));
        vertical1Texture = new Texture(Gdx.files.internal("vertical4.jpeg"));
        vertical2Texture = new Texture(Gdx.files.internal("vertical1Stone.jpeg"));
        vertical3Texture = new Texture(Gdx.files.internal("vertical1Glass.jpeg"));
        horizontal1Texture = new Texture(Gdx.files.internal("horizontal3.jpeg"));
        horizontal2Texture = new Texture(Gdx.files.internal("horizontal1Glass.jpeg"));
        pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        retryTexture = new Texture(Gdx.files.internal("retry.png"));
        redBirdTexture = new Texture(Gdx.files.internal("redBird.png"));
        yellowBirdTexture = new Texture(Gdx.files.internal("yellowBird.png"));
        bigRedBirdTexture = new Texture(Gdx.files.internal("bigRedBird.png"));
        shieldPigTexture = new Texture(Gdx.files.internal("shieldPig.png"));
        kingPigTexture = new Texture(Gdx.files.internal("kingPig.png"));
        slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
        pigTexture = new Texture(Gdx.files.internal("regularPig.png"));

        start = new Vector2(423, 278);

        world = new World(new Vector2(0, -240), true);
        ground = new Ground(world, 0, 0, 1600, 123);
        slingShot = new SlingShot(game, start, 85, 178, 375, 123);

        ListenerClass = new ListenerClass();
        world.setContactListener(ListenerClass);

        debugRenderer = new Box2DDebugRenderer();

        Vector2 vector2 = new Vector2(0, 0);

        birds = new ArrayList<>();
        RedBird redBird = new RedBird(world, redBirdTexture, 0, 45, 45, start.x, start.y, 0, vector2, 0, 1);
        YellowBird yellowBird = new YellowBird(world, yellowBirdTexture, 1, 38, 38, 288, 128, 0, vector2, 0, 1);
        BigRedBird bigRedBird = new BigRedBird(world, bigRedBirdTexture, 2, 60, 60, 223, 128, 0, vector2, 0, 1);
        birds.add(redBird); birds.add(yellowBird); birds.add(bigRedBird);

        obstacles = new ArrayList<>();
        WoodObstacle vertical1, vertical2, vertical3, vertical4, vertical5, vertical6, vertical7, horizontal1, horizontal2, horizontal3, horizontal4, horizontal5, horizontal6;
        GlassObstacle vertical10, vertical11, horizontal7, horizontal8, horizontal9;
        StoneObstacle vertical8, vertical9;
        vertical1 = new WoodObstacle(world, vertical1Texture, 31, 39, 916 , 128 - 5, 0, vector2,  0, 10);
        vertical2 = new WoodObstacle(world, vertical1Texture, 16, 100, 1193 + 90, 128 - 5,  0, vector2, 0, 10);
        vertical3 = new WoodObstacle(world, vertical1Texture, 16, 100, 1269 + 90, 128 - 5,  0, vector2, 0, 10);
        vertical4 = new WoodObstacle(world, vertical1Texture, 16, 100, 1193 + 90 + 46 - 8, 248,  0, vector2, 0, 10);
        vertical5 = new WoodObstacle(world, vertical1Texture, 16, 75, 1065 , 128 - 5,  0, vector2, 0, 10);
        vertical6 = new WoodObstacle(world, vertical1Texture, 16, 75, 1115 , 128 - 5,  0, vector2, 0, 10);
        vertical7 = new WoodObstacle(world, vertical1Texture, 16, 75, 1165 , 128 - 5, 0, vector2, 0, 10);
        vertical8 = new StoneObstacle(world, vertical2Texture, 16, 90, 1065, 211,  0, vector2, 0, 12);
        vertical9 = new StoneObstacle(world, vertical2Texture, 16, 90, 1165 , 211,  0, vector2, 0, 12);
        vertical10 = new GlassObstacle(world, vertical3Texture, 16, 90, 1055, 328, 0, vector2, 0, 7);
        vertical11 = new GlassObstacle(world, vertical3Texture, 16, 90, 1175, 328, 0, vector2, 0, 7);

        horizontal1 = new WoodObstacle(world, horizontal1Texture, 76, 20, 893 , 167,  0, vector2, 0, 10);
        horizontal2 = new WoodObstacle(world, horizontal1Texture, 92, 20, 1193 + 90, 228,  0, vector2, 0, 10);
        horizontal3 = new WoodObstacle(world, horizontal1Texture, 92, 20, 1193 + 94, 348,  0, vector2, 0, 10);
        horizontal4 = new WoodObstacle(world, horizontal1Texture, 60, 15, 1065, 203,  0, vector2, 0, 10);
        horizontal5 = new WoodObstacle(world, horizontal1Texture, 60, 15, 1063 + 54, 203,  0, vector2, 0, 10);
        horizontal6 = new WoodObstacle(world, horizontal1Texture, 136, 20, 1055, 308,  0, vector2, 0, 10);
        horizontal7 = new GlassObstacle(world, horizontal2Texture, 136, 20, 1055, 418, 0, vector2, 0, 7);
        horizontal8 = new GlassObstacle(world, horizontal2Texture, 30, 20, 1055 + 17, 328, 0, vector2, 0, 7);
        horizontal9 = new GlassObstacle(world, horizontal2Texture, 30, 20, 1175 - 30 - 1, 328, 0, vector2, 0, 7);

        obstacles.add(vertical1);
        obstacles.add(vertical2);
        obstacles.add(vertical3);
        obstacles.add(vertical4);
        obstacles.add(vertical5);
        obstacles.add(vertical6);
        obstacles.add(vertical7);
        obstacles.add(vertical8);
        obstacles.add(vertical9);
        obstacles.add(vertical10);
        obstacles.add(vertical11);
        obstacles.add(horizontal1);
        obstacles.add(horizontal2);
        obstacles.add(horizontal3);
        obstacles.add(horizontal4);
        obstacles.add(horizontal5);
        obstacles.add(horizontal6);
        obstacles.add(horizontal7);
        obstacles.add(horizontal8);
        obstacles.add(horizontal9);

        pigs = new ArrayList<>();

        ShieldPig pig1, pig2;
        pig1 = new ShieldPig(world, shieldPigTexture, 50, 50, 931, 187, 0, vector2, 0, 4);
        pig2 = new ShieldPig(world, shieldPigTexture, 50, 50, 1190 + 100 + 60 - 20, 368, 0, vector2, 0, 4);
        KingPig pig3;
        pig3 = new KingPig(world, kingPigTexture, 70, 70, 1070 + 50 + 5, 251, 0, vector2, 0, 10);
        RegularPig pig4;
        pig4 = new RegularPig(world, pigTexture, 40, 40, 1065 + 58, 328, 0, vector2, 0, 3);
        pigs.add(pig1);
        pigs.add(pig2);
        pigs.add(pig3);
        pigs.add(pig4);

        pauseButton = new Button(pauseTexture, 125, 813, 80, 80);
        retryButton = new Button(retryTexture, 10, 813, 80, 80);

        launchBird = birds.getFirst();

        game.viewport.setWorldSize(1600, 900);
    }

    public Level3Screen(final Main game, Serialisation serialise) {
        this.game = game; this.paused = false; this.accumulator = 0;

        spriteBatch = new SpriteBatch();

        touchPos = new Vector2();

        backgroundTexture = new Texture(Gdx.files.internal("level3Background.png"));
        vertical1Texture = new Texture(Gdx.files.internal("vertical4.jpeg"));
        vertical2Texture = new Texture(Gdx.files.internal("vertical1Stone.jpeg"));
        vertical3Texture = new Texture(Gdx.files.internal("vertical1Glass.jpeg"));
        horizontal1Texture = new Texture(Gdx.files.internal("horizontal3.jpeg"));
        horizontal2Texture = new Texture(Gdx.files.internal("horizontal1Glass.jpeg"));
        pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        retryTexture = new Texture(Gdx.files.internal("retry.png"));
        redBirdTexture = new Texture(Gdx.files.internal("redBird.png"));
        yellowBirdTexture = new Texture(Gdx.files.internal("yellowBird.png"));
        bigRedBirdTexture = new Texture(Gdx.files.internal("bigRedBird.png"));
        shieldPigTexture = new Texture(Gdx.files.internal("shieldPig.png"));
        kingPigTexture = new Texture(Gdx.files.internal("kingPig.png"));
        slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
        pigTexture = new Texture(Gdx.files.internal("regularPig.png"));

        start = new Vector2(423, 278);

        world = new World(new Vector2(0, -240), true);
        ground = new Ground(world, 0, 0, 1600, 123);
        slingShot = new SlingShot(game, start, 85, 178, 375, 123);

        ListenerClass = new ListenerClass();
        world.setContactListener(ListenerClass);

        debugRenderer = new Box2DDebugRenderer();

        Vector2 vector2 = new Vector2(0, 0);

        birds = new ArrayList<>(); int s = 0;
        for (Data data : serialise.getBirds()) {
            if (data.getType2() == 1) {
                RedBird redBird = new RedBird(world, redBirdTexture, s, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits(), data.isReleased());
                birds.add(redBird);
            } else if (data.getType2() == 2) {
                YellowBird yellowBird = new YellowBird(world, yellowBirdTexture, s, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits(), data.isReleased());
                birds.add(yellowBird);
            } else if (data.getType2() == 3) {
                BigRedBird bigRedBird = new BigRedBird(world, bigRedBirdTexture, s, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits(), data.isReleased());
                birds.add(bigRedBird);
            }
            s++;
        }

        obstacles = new ArrayList<>();
        for (Data data : serialise.getObstacle()) {
            if (data.getType2() == 1) {
                WoodObstacle woodObstacle = new WoodObstacle(world, vertical1Texture, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                obstacles.add(woodObstacle);
            } else if (data.getType2() == 2) {
                GlassObstacle glassObstacle = new GlassObstacle(world, vertical3Texture, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                obstacles.add(glassObstacle);
            } else if (data.getType2() == 3) {
                StoneObstacle stoneObstacle = new StoneObstacle(world, vertical2Texture, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                obstacles.add(stoneObstacle);
            }
        }

        pigs = new ArrayList<>();
        for (Data data : serialise.getPigs()) {
            if (data.getType2() == 1) {
                RegularPig regularPig = new RegularPig(world, pigTexture, data.getWidth(), data.getHeight(), data.getX() + data.getWidth() / 2 , data.getY() + data.getHeight() / 2, data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                pigs.add(regularPig);
            } else if (data.getType2() == 2) {
                ShieldPig shieldPig = new ShieldPig(world, shieldPigTexture, data.getWidth(), data.getHeight(), data.getX() + data.getWidth() / 2 , data.getY() + data.getHeight() / 2, data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                pigs.add(shieldPig);
            } else if (data.getType2() == 3) {
                KingPig kingPig = new KingPig(world, shieldPigTexture, data.getWidth(), data.getHeight(), data.getX() + data.getWidth() / 2 , data.getY() + data.getHeight() / 2, data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                pigs.add(kingPig);
            }
        }

        pauseButton = new Button(pauseTexture, 125, 813, 80, 80);
        retryButton = new Button(retryTexture, 10, 813, 80, 80);

        launchBird = null;
        for (Bird bird : birds) {
            if (bird != null && !bird.isReleased()) {
                launchBird = bird;
                break;
            }
        }

        if (launchBird != null) {
            launchBird.getBody().setTransform(start, 0);
        }

        game.viewport.setWorldSize(1600, 900);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        if (paused) {
            return;
        }
        input();
        logic(v);
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
                    game.setScreen(new Level3Screen(game));
                    dispose();
                } else if (pauseButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new PauseScreen(game, 3, this));
                    dispose();
                } else if (slingShot.contains(touchPos)) {
                    game.setTimer(0);
                    slingShot.setGrabbed(true);
                }
            }
        }
        if (slingShot.isGrabbed()) {
            if (Gdx.input.isTouched()) {
                slingShot.stretch(touchPos);
                if (launchBird != null) {
                    Vector2 holdPoint = slingShot.getHoldPoint();
                    launchBird.getBody().setTransform(holdPoint, launchBird.getBody().getAngle());
                }
            } else {
                if (launchBird != null) {
                    Vector2 launchVector = slingShot.calculateVelocity();
                    if (launchBird instanceof BigRedBird) {
                        launchVector.scl(8f);
                    } else if (launchBird instanceof YellowBird) {
                        launchVector.scl(1.1f);
                    } else if (launchBird instanceof RedBird) {
                        launchVector.scl(1.3f);
                    }
                    launchBird.getBody().applyForce(launchVector, launchBird.getBody().getWorldCenter(), true);
                }
                slingShot.release();
                if (launchBird != null) {
                    launchBird.setReleased(true);
                    int pos = launchBird.getPosition();
                    if (pos + 1 == birds.size()) {
                        launchBird = null;
                    } else {
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                launchBird = birds.get(pos + 1);
                                launchBird.getBody().setTransform(start.x, start.y, 0);
                            }
                        }, 0.3f);
                    }
                }
            }
        }
    }

    private void logic(float delta) {
        if (game.musicPlaying) {
            game.music.play();
        } else {
            game.music.pause();
        }

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }

        boolean pigsDead = true;
        for (Pig pig : pigs) {
            if (!pig.isDestroyed()) {
                pigsDead = false;
            }
        }
        boolean birdsFinished = true;
        for (Bird bird : birds) {
            if (!bird.isDestroyed()) {
                birdsFinished = false;
            }
        }

        if (pigsDead) {
            game.setScreen(new WinScreen(game, 3));
            dispose();
        } else if (birdsFinished) {
            game.setScreen(new LoseScreen(game, 3));
            dispose();
        }

        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            if (body.getUserData() != null) {
                if (body.getUserData() instanceof Bird bird) {
                    Sprite sprite = bird.getSprite();
                    sprite.setOriginCenter();
                    sprite.setPosition(
                        body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2
                    );
                    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                    System.out.println(body.getLinearVelocity().len());
                    System.out.println(bird.getSprite().getX());
                    if ((bird.isReleased() && body.getLinearVelocity().len() <= 0.001) || bird.getSprite().getX() < -300 || bird.getSprite().getX() > 1900) {
                        bird.setDestroy(true);
                        world.destroyBody(body);
                    }
                } else if (body.getUserData() instanceof Pig pig) {
                    Sprite sprite = pig.getSprite();
                    sprite.setOriginCenter();
                    sprite.setPosition(
                        body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2
                    );
                    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                    if (pig.getSprite().getX() < -300 || pig.getSprite().getX() > 1900) {
                        pig.destroy();
                    }
                } else if (body.getUserData() instanceof Obstacle obstacle) {
                    Sprite sprite = obstacle.getSprite();
                    sprite.setOriginCenter();
                    sprite.setPosition(
                        body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2
                    );
                    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                }
            }
        }
        ListenerClass.processList();
    }

    private long getScore() {
        return ListenerClass.getScore();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        spriteBatch.begin();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        game.batch.draw(slingShotTexture, 375, 123, 85, 178);
        slingShot.draw();
        retryButton.draw(game); pauseButton.draw(game);

        for (Bird bird : birds) {
            if (bird != null && !bird.isDestroyed()) {
                Sprite sprite = bird.getSprite();
                sprite.draw(game.batch);
            }
        }

        for (Pig pig : pigs) {
            if (pig != null && !pig.isDestroyed()) {
                Sprite sprite = pig.getSprite();
                sprite.draw(game.batch);
            }
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle != null && !obstacle.isDestroyed()) {
                Sprite sprite = obstacle.getSprite();
                sprite.draw(game.batch);
            }
        }

        game.batch.end();
        spriteBatch.end();

        debugRenderer.render(world, game.viewport.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        game.setPause(true);
        this.paused = true;
    }

    @Override
    public void resume() {
        game.setPause(false);
        this.paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void save() {
        List<Data> b = new ArrayList<>(), o = new ArrayList<>(), p = new ArrayList<>();
        for (Bird bird : birds) {
            b.add(new Data(bird));
        }
        for (Obstacle obstacle : obstacles) {
            o.add(new Data(obstacle));
        }
        for (Pig pig : pigs) {
            p.add(new Data(pig));
        }
        Serialisation state = new Serialisation(b, p, o);
        try (FileWriter writer = new FileWriter(Constants.filepath3)) {
            String jsonString = json.toJson(state, Serialisation.class);
            writer.write(jsonString);
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }
}
