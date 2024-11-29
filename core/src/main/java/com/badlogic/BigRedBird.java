package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BigRedBird extends Bird {
    public BigRedBird(World world, Texture t, int pos, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits) {
        super(world, t, pos, width, height, x, y, rotation, speed, omega, hits);
        setBigRedBird(width, height, x, y, rotation, speed, omega, false);
    }

    public BigRedBird(World world, Texture t, int pos, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits, boolean release) {
        super(world, t, pos, width, height, x, y, rotation, speed, omega, hits, release);
        setBigRedBird(width, height, x, y, rotation, speed, omega, release);
    }

    private void setBigRedBird(float width, float height, float x, float y, float rotation, Vector2 speed, float omega, boolean release) {
        BodyDef bodyDef = dynamicBody.getBodyDef();
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(width / 2);
        FixtureDef fixtureDef = dynamicBody.getFixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        body.setLinearVelocity(speed);
        body.setAngularVelocity(omega);
        body.setLinearDamping(0);
        body.setAngularDamping(0);
        body.setAwake(release);
        body.setUserData(this);
        dynamicBody.setBody(body);
        circle.dispose();
    }
}
