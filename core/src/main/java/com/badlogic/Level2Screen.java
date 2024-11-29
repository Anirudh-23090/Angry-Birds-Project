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

public class Level2Screen implements Screen {
    private final Main game;
    private final Texture level2Texture;
    private final Texture pauseTexture;
    private final Texture retryTexture;
    private final Texture pig1Texture;
    private final Texture pig2Texture;
    private final Texture redBirdTexture;
    private final Texture yellowBirdTexture;
    private final Texture bigRedBirdTexture;
    private final Texture vertical1Texture;
    private final Texture vertical2Texture;
    private final Texture vertical3Texture;
    private final Texture vertical4Texture;
    private final Texture vertical5Texture;
    private final Texture vertical6Texture;
    private final Texture horizontal1Texture;
    private final Texture horizontal2Texture;
    private final Texture standTexture;
    private final Texture slingShotTexture;
    private final World world;
    private final List<Bird> birds;
    private final List<Pig> pigs;
    private final List<Obstacle> obstacles;
    private final Stand stand;
    private final SlingShot slingShot;
    private final Ground ground;
    private final Button pauseButton, retryButton;
    private final Vector2 touchPos, start;
    private final Box2DDebugRenderer debugRenderer;
    private boolean paused;
    private Bird launchBird;
    private final SpriteBatch spriteBatch;
    private float accumulator;
    private final ListenerClass ListenerClass;

    public Level2Screen(final Main game) {
        this.game = game; this.paused = false; this.accumulator = 0;

        spriteBatch = new SpriteBatch();

        touchPos = new Vector2();

        level2Texture = new Texture(Gdx.files.internal("level2Background.png"));
        vertical1Texture = new Texture(Gdx.files.internal("vertical1Wood.png"));
        vertical2Texture = new Texture(Gdx.files.internal("vertical2Wood.png"));
        vertical3Texture = new Texture(Gdx.files.internal("vertical1Stone.png"));
        vertical4Texture = new Texture(Gdx.files.internal("vertical2Stone.png"));
        vertical5Texture = new Texture(Gdx.files.internal("vertical3Stone.png"));
        vertical6Texture = new Texture(Gdx.files.internal("vertical4Stone.png"));
        horizontal1Texture = new Texture(Gdx.files.internal("horizontal1Wood.png"));
        horizontal2Texture = new Texture(Gdx.files.internal("horizontal1Stone.png"));
        slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
        standTexture = new Texture(Gdx.files.internal("stand2.png"));
        redBirdTexture = new Texture(Gdx.files.internal("redBird.png"));
        yellowBirdTexture = new Texture(Gdx.files.internal("yellowBird.png"));
        bigRedBirdTexture = new Texture(Gdx.files.internal("bigRedBird.png"));
        pig1Texture = new Texture(Gdx.files.internal("regularPig.png"));
        pig2Texture = new Texture(Gdx.files.internal("shieldPig.png"));
        pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        retryTexture = new Texture(Gdx.files.internal("retry.png"));

        start = new Vector2(423, 275);

        world = new World(new Vector2(0, -240), true);
        ground = new Ground(world, 0, 0, 1600, 125);
        stand = new Stand(world, 1215, 125, 140, 190);
        slingShot = new SlingShot(game, start, 85, 178, 375, 125);

        ListenerClass = new ListenerClass();
        world.setContactListener(ListenerClass);

        debugRenderer = new Box2DDebugRenderer();

        Vector2 vector2 = new Vector2(0, 0);

        birds = new ArrayList<>();
        RedBird redBird = new RedBird(world, redBirdTexture, 0, 45, 45, 423, 275, 0, vector2, 0, 1);
        YellowBird yellowBird = new YellowBird(world, yellowBirdTexture, 1, 38, 38, 288, 125, 0, vector2, 0, 1);
        BigRedBird bigRedBird = new BigRedBird(world, bigRedBirdTexture, 2, 60, 60, 223, 125, 0, vector2, 0, 1);
        birds.add(redBird); birds.add(yellowBird); birds.add(bigRedBird);

        obstacles = new ArrayList<>();
        WoodObstacle vertical1, vertical2, vertical3, vertical4, vertical5, vertical6, vertical7, horizontal1, horizontal2, horizontal3, horizontal4, vertical8, vertical9;
        StoneObstacle vertical10, vertical11, vertical12, vertical13, vertical14, vertical15, vertical16, vertical17, vertical18;
        vertical1 = new WoodObstacle(world, vertical1Texture, 13, 58, 1213 - 160 + 10, 125 + 50, 0, vector2,  0, 10);
        vertical2 = new WoodObstacle(world, vertical1Texture, 13, 58, 1213 - 80 + 10, 125 + 50,  0, vector2, 0, 10);
        vertical3 = new WoodObstacle(world, vertical1Texture, 13, 58, 1213 - 160 + 10, 125 + 50 + 58 + 15,  0, vector2, 0, 10);
        vertical4 = new WoodObstacle(world, vertical1Texture, 13, 58, 1213 - 80 + 10, 125 + 50 + 58 + 15,  0, vector2, 0, 10);
        vertical5 = new WoodObstacle(world, vertical1Texture, 13, 58, 1242 + 10, 315 + 60,  0, vector2, 0, 10);
        vertical6 = new WoodObstacle(world, vertical1Texture, 13, 58, 1242 + 10, 315 + 60 + 58,  0, vector2, 0, 10);
        vertical7 = new WoodObstacle(world, vertical1Texture, 13, 58, 1242 + 10, 315 + 60 + 58 + 58 + 15 + 30 + 15 + 15,  0, vector2, 0, 10);
        vertical8 = new WoodObstacle(world, vertical2Texture, 30, 50, 1215 - 160, 125,  0, vector2, 0, 10);
        vertical9 = new WoodObstacle(world, vertical2Texture, 30, 50, 1215 - 80, 125,  0, vector2, 0, 10);

        vertical10 = new StoneObstacle(world, vertical3Texture, 13, 58, 1242 + 70, 315 + 60 + 58 + 58 + 15 + 30 + 15 + 15, 0, vector2, 0, 12);
        vertical11 = new StoneObstacle(world, vertical3Texture, 13, 58, 1242 + 70, 315 + 60 + 58, 0, vector2, 0, 12);
        vertical12 = new StoneObstacle(world, vertical3Texture, 13, 58, 1242 + 70, 315 + 60, 0, vector2, 0, 12);
        vertical13 = new StoneObstacle(world, vertical4Texture, 30, 15, 1215 + 10 + 120 - 30, 315 + 60 + 58 + 58 + 15 + 30, 0, vector2, 0, 12);
        vertical14 = new StoneObstacle(world, vertical4Texture, 30, 15, 1215 + 10, 315 + 60 + 58 + 58 + 15 + 30, 0, vector2, 0, 12);
        vertical15 = new StoneObstacle(world, vertical5Texture, 30, 30, 1215 + 10, 315 + 60 + 58 + 58 + 15, 0, vector2, 0, 12);
        vertical16 = new StoneObstacle(world, vertical5Texture, 30, 30, 1215 + 10 + 120 - 30, 315 + 60 + 58 + 58 + 15, 0, vector2, 0, 12);
        vertical17 = new StoneObstacle(world, vertical6Texture, 30, 60, 1245, 315, 0, vector2, 0, 12);
        vertical18 = new StoneObstacle(world, vertical6Texture, 30, 60, 1245 + 60, 315, 0, vector2, 0, 12);

        horizontal1 = new WoodObstacle(world, horizontal1Texture, 120, 15, 1218 - 160 - 10, 125 + 50 + 58, 0, vector2, 0, 10);
        horizontal2 = new WoodObstacle(world, horizontal1Texture, 120, 15, 1218 - 160 - 10, 125 + 50 + 58 + 15 + 58,  0, vector2, 0, 10);
        horizontal3 = new WoodObstacle(world, horizontal2Texture, 120, 15, 1215 + 10, 315 + 60 + 58 + 58,  0, vector2, 0, 10);
        horizontal4 = new WoodObstacle(world, horizontal2Texture, 120, 15, 1215 + 10, 315 + 60 + 58 + 58 + 15 + 30 + 15,  0, vector2, 0, 10);

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
        obstacles.add(vertical12);
        obstacles.add(vertical13);
        obstacles.add(vertical14);
        obstacles.add(vertical15);
        obstacles.add(vertical16);
        obstacles.add(vertical17);
        obstacles.add(vertical18);
        obstacles.add(horizontal1);
        obstacles.add(horizontal2);
        obstacles.add(horizontal3);
        obstacles.add(horizontal4);

        pigs = new ArrayList<>();
        ShieldPig pig1;
        pig1 = new ShieldPig(world, pig2Texture, 40, 40, 1213 - 160 + 25 + 30, 125 + 50 + 58 + 15, 0, vector2, 0, 4);
        RegularPig pig2, pig3;
        pig2 = new RegularPig(world, pig1Texture, 28, 28, 1215 + 25 + 28 + 30, 315 + 60 + 58 + 58 + 15, 0, vector2, 0, 3);
        pig3 = new RegularPig(world, pig1Texture,  28, 28, 1215 + 25 + 30, 315 + 60 + 58 + 58 + 15, 0, vector2, 0, 3);
        pigs.add(pig1);
        pigs.add(pig2);
        pigs.add(pig3);

        pauseButton = new Button(pauseTexture, 125, 813, 80, 80);
        retryButton = new Button(retryTexture, 10, 813, 80, 80);

        launchBird = birds.getFirst();

        game.viewport.setWorldSize(1600, 900);
    }

    public Level2Screen(final Main game, Serialisation serialise) {
        this.game = game; this.paused = false; this.accumulator = 0;

        spriteBatch = new SpriteBatch();

        touchPos = new Vector2();

        level2Texture = new Texture(Gdx.files.internal("level2Background.png"));
        vertical1Texture = new Texture(Gdx.files.internal("vertical1Wood.png"));
        vertical2Texture = new Texture(Gdx.files.internal("vertical2Wood.png"));
        vertical3Texture = new Texture(Gdx.files.internal("vertical1Stone.png"));
        vertical4Texture = new Texture(Gdx.files.internal("vertical2Stone.png"));
        vertical5Texture = new Texture(Gdx.files.internal("vertical3Stone.png"));
        vertical6Texture = new Texture(Gdx.files.internal("vertical4Stone.png"));
        horizontal1Texture = new Texture(Gdx.files.internal("horizontal1Wood.png"));
        horizontal2Texture = new Texture(Gdx.files.internal("horizontal1Stone.png"));
        slingShotTexture = new Texture(Gdx.files.internal("slingShot.png"));
        standTexture = new Texture(Gdx.files.internal("stand2.png"));
        redBirdTexture = new Texture(Gdx.files.internal("redBird.png"));
        yellowBirdTexture = new Texture(Gdx.files.internal("yellowBird.png"));
        bigRedBirdTexture = new Texture(Gdx.files.internal("bigRedBird.png"));
        pig1Texture = new Texture(Gdx.files.internal("regularPig.png"));
        pig2Texture = new Texture(Gdx.files.internal("shieldPig.png"));
        pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        retryTexture = new Texture(Gdx.files.internal("retry.png"));

        start = new Vector2(423, 275);

        world = new World(new Vector2(0, -240), true);
        ground = new Ground(world, 0, 0, 1600, 125);
        stand = new Stand(world, 1215, 125, 140, 190);
        slingShot = new SlingShot(game, start, 85, 178, 375, 125);

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
                WoodObstacle woodObstacle = new WoodObstacle(world, vertical2Texture, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                obstacles.add(woodObstacle);
            } else if (data.getType2() == 3) {
                StoneObstacle stoneObstacle = new StoneObstacle(world, vertical4Texture, data.getWidth(), data.getHeight(), data.getX(), data.getY(), data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                obstacles.add(stoneObstacle);
            }
        }

        pigs = new ArrayList<>();
        for (Data data : serialise.getPigs()) {
            if (data.getType2() == 1) {
                RegularPig regularPig = new RegularPig(world, pig1Texture, data.getWidth(), data.getHeight(), data.getX() + data.getWidth() / 2 , data.getY() + data.getHeight() / 2, data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                pigs.add(regularPig);
            } else if (data.getType2() == 2) {
                ShieldPig shieldPig = new ShieldPig(world, pig2Texture, data.getWidth(), data.getHeight(), data.getX() + data.getWidth() / 2 , data.getY() + data.getHeight() / 2, data.getRotation(), new Vector2(data.getVx(), data.getVy()), data.getOmega(), data.getHits());
                pigs.add(shieldPig);
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
                    game.setScreen(new Level2Screen(game));
                    dispose();
                } else if (pauseButton.contains(touchPos.x, touchPos.y)) {
                    game.setTimer(0);
                    game.setScreen(new PauseScreen(game, 2, this));
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
            game.setScreen(new WinScreen(game, 2));
            dispose();
        } else if (birdsFinished) {
            game.setScreen(new LoseScreen(game, 2));
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
                    if ((bird.isReleased() && body.getLinearVelocity().len() <= 10) || bird.getSprite().getX() < -300 || bird.getSprite().getX() > 1900) {
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

        game.batch.draw(level2Texture, 0, 0, worldWidth, worldHeight);
        game.batch.draw(standTexture, 1215, 125, 140, 190);
        game.batch.draw(slingShotTexture, 375, 125, 85, 178);
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
        try (FileWriter writer = new FileWriter(Constants.filepath2)) {
            String jsonString = json.toJson(state, Serialisation.class);
            writer.write(jsonString);
        } catch (Exception e) {
            System.out.println("Error saving file     ");
        }
    }
}
