package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Bird {
    protected DynamicBody dynamicBody;
    protected World world;
    protected Sprite sprite;
    protected Vector2 speed;
    protected boolean released, destroy;
    protected int position, hits;
    public Bird(World world, Texture t, int pos, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits) {
        this.world = world;
        this.released = false;
        this.position = pos;
        sprite = new Sprite(t);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        sprite.setRotation(rotation);
        dynamicBody = new DynamicBody();
        destroy = false;
        this.hits = hits;
    }
    public Bird(World world, Texture t, int pos, float width, float height, float x, float y, float rotation, Vector2 speed, float omega, int hits, boolean release) {
        this.world = world;
        this.released = false;
        this.position = pos;
        sprite = new Sprite(t);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        sprite.setRotation(rotation);
        dynamicBody = new DynamicBody();
        destroy = false;
        this.hits = hits;
        this.setReleased(release);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return dynamicBody.getBody();
    }

    public int getPosition() {
        return position;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public boolean isDestroyed() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean isReleased() {
        return released;
    }
}
