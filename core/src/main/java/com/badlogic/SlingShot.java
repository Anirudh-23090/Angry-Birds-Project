package com.badlogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class SlingShot {
    private final Main game;
    private final Vector2 start;
    private Vector2 holdPoint;
    private final Vector2 left;
    private final Vector2 right;
    boolean grab;
    private static final float BAND_WIDTH = 5f;
    private static final Color BAND_COLOR = new Color(0.4f, 0.2f, 0f, 2f);

    public SlingShot(Main game, Vector2 start, float width, float height, float x, float y) {
        this.game = game;
        this.start = new Vector2(start);
        holdPoint = new Vector2(start);
        this.left = new Vector2(x + width * 0.3f, y + height * 0.8f);
        this.right = new Vector2(x + width * 0.7f, y + height * 0.8f);
        this.grab = false;
    }

    public void stretch(Vector2 touchPos) {
        if (!grab) {
            return;
        }
        Vector2 direction = new Vector2(touchPos).sub(start);
        float distance = direction.len();

        if (distance > Constants.MAX_STRETCH) {
            direction.setLength(Constants.MAX_STRETCH);
        }
        holdPoint.set(start.x + direction.x, start.y + direction.y);
    }

    public void setGrabbed(boolean bool) {
        grab = true;
    }

    public boolean isGrabbed() {
        return grab;
    }

    public Vector2 getHoldPoint() {
        return holdPoint;
    }

    public void release() {
        grab = false;
        holdPoint = new Vector2(start);
    }

    public Vector2 calculateVelocity() {
        Vector2 velocity = new Vector2(start).sub(holdPoint);

        float stretch = velocity.len();
        if (stretch > Constants.MAX_STRETCH) {
            velocity.nor().scl(Constants.MAX_STRETCH);
        }
        float stretchFactor = 1800f;
        float launchSpeed = (stretch / Constants.MAX_STRETCH) * stretchFactor;

        System.out.println(velocity.scl(launchSpeed));
        return velocity.scl(launchSpeed);
    }

    public boolean contains(Vector2 vector2) {
        return vector2.dst(start) < 100f;
    }

    public void draw() {
        SpriteBatch batch = game.batch;
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(BAND_COLOR);

        shapeRenderer.rectLine(left, holdPoint, BAND_WIDTH);
        shapeRenderer.rectLine(right, holdPoint, BAND_WIDTH);

        shapeRenderer.end();
        batch.begin();
    }
}
