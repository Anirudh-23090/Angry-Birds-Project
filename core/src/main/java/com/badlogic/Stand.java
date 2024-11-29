package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Stand {
    protected StaticBody staticBody;
    protected World world;
    protected Rectangle rectangle;

    public Stand(World world, float x, float y, float width, float height) {
        this.world = world;
        rectangle = new Rectangle(x, y, width, height);
        staticBody = new StaticBody();
        staticBody.getBodyDef().position.set(x + width / 2, y + height / 2);
        Body body = world.createBody(staticBody.getBodyDef());
        PolygonShape StandBox = new PolygonShape();

        StandBox.setAsBox(width / 2, height / 2);
        body.createFixture(StandBox, 0.0f);

        staticBody.setBody(body);
        StandBox.dispose();
    }
}
