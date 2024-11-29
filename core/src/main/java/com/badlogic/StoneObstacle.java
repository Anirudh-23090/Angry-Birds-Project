package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class StoneObstacle extends Obstacle {
    public StoneObstacle(World world, Texture t, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits) {
        super(world, t, width, height, x, y, rotation, speed, omega, hits);
        setStoneObstacle(width, height, x, y, rotation, speed, omega);
    }

    private void setStoneObstacle(float width, float height, float x, float y, float rotation, Vector2 speed, float omega) {
        BodyDef bodyDef = dynamicBody.getBodyDef();
        bodyDef.position.set(
            (x + width / 2),
            (y + height / 2)
        );
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        FixtureDef fixtureDef = dynamicBody.getFixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.1f;
        body.createFixture(fixtureDef);
        body.setLinearVelocity(speed);
        body.setAngularVelocity(omega);
        body.setLinearDamping(0);
        body.setAngularDamping(0);
        body.setAwake(true);
        body.setUserData(this);
        dynamicBody.setBody(body);
        shape.dispose();
    }
}
