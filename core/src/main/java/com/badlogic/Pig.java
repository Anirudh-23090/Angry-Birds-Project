package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Pig {
    protected DynamicBody dynamicBody;
    protected World world;
    protected Sprite sprite;
    protected int position;
    protected int hits;
    protected Vector2 speed;
    protected boolean destroy;
    public Pig(World world, Texture t, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits) {
        this.world = world;
        this.destroy = false;
        sprite = new Sprite(t);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        sprite.setRotation(rotation);
        dynamicBody = new DynamicBody();
        this.hits = hits;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return dynamicBody.getBody();
    }

    public void damage() {
        hits--;
    }

    public int getHealth() {
        return hits;
    }

    public boolean isDestroyed() {
        return destroy;
    }

    public void destroy() {
        destroy = true;
    }
}
