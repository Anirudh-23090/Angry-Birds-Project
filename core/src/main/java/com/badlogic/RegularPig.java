package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class RegularPig extends Pig {
    public RegularPig(World world, Texture t, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits) {
        super(world, t, width, height, x, y, rotation, speed, omega, hits);
        setRegularPig(width, height, x, y, rotation, speed, omega);
    }

    private void setRegularPig(float width, float height, float x, float y, float rotation, Vector2 speed, float omega) {
        BodyDef bodyDef = dynamicBody.getBodyDef();
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(width / 2);
        FixtureDef fixtureDef = dynamicBody.getFixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        body.setLinearVelocity(speed);
        body.setAngularVelocity(omega);
        body.setLinearDamping(0);
        body.setAngularDamping(0);
        body.setAwake(true);
        body.setUserData(this);
        dynamicBody.setBody(body);
        circle.dispose();
    }
}
