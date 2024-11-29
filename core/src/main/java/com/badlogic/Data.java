package com.badlogic;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Data implements Json.Serializable {
    private float width;
    private float height;
    private float x;
    private float y;
    private float rotation;
    private float vx;
    private float vy;
    private float speed;
    private float omega;
    private int hits;
    private int type1;
    private int type2;
    private boolean isReleased;
    public Data(Object object) {
        if (object instanceof Bird) {
            type1 = 1;
            Bird bird = (Bird) object;
            x = bird.getSprite().getX();
            y = bird.getSprite().getY();
            if (bird instanceof RedBird) {
                type2 = 1;
            } else if (bird instanceof YellowBird) {
                type2 = 2;
            } else {
                type2 = 3;
            }
            isReleased = bird.isReleased();
            omega = bird.getBody().getAngularVelocity();
            vx = bird.getBody().getLinearVelocity().x;
            vy = bird.getBody().getLinearVelocity().y;
            rotation = bird.getSprite().getRotation();
            hits = 10;
            width = bird.getSprite().getWidth();
            height = bird.getSprite().getHeight();
        } else if (object instanceof Pig) {
            Pig pig = (Pig) object;
            type1 = 2;
            x = pig.getSprite().getX();
            y = pig.getSprite().getY();
            if (pig instanceof RegularPig) {
                type2 = 1;
            } else if (pig instanceof ShieldPig) {
                type2 = 2;
            } else {
                type2 = 3;
            }
            omega = pig.getBody().getAngularVelocity();
            vx = pig.getBody().getLinearVelocity().x;
            vy = pig.getBody().getLinearVelocity().y;
            rotation = pig.getSprite().getRotation();
            hits = pig.getHealth();
            width = pig.getSprite().getWidth();
            height = pig.getSprite().getHeight();
        } else if (object instanceof Obstacle) {
            type1 = 3;
            Obstacle obstacle = (Obstacle) object;
            x = obstacle.getSprite().getX();
            y = obstacle.getSprite().getY();
            if (obstacle instanceof WoodObstacle) {
                type2 = 1;
            } else if (obstacle instanceof GlassObstacle) {
                type2 = 2;
            } else {
                type2 = 3;
            }
            omega = obstacle.getBody().getAngularVelocity();
            vx = obstacle.getBody().getLinearVelocity().x;
            vy = obstacle.getBody().getLinearVelocity().y;
            rotation = obstacle.getSprite().getRotation();
            hits = obstacle.getHealth();
            width = obstacle.getSprite().getWidth();
            height = obstacle.getSprite().getHeight();
        }
    }

    public Data() {

    }

    public float getHeight() {
        return height;
    }

    public float getOmega() {
        return omega;
    }

    public float getRotation() {
        return rotation;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public float getWidth() {
        return width;
    }

    public int getHits() {
        return hits;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public int getType1() {
        return type1;
    }

    public int getType2() {
        return type2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public void write(Json json) {
        json.writeValue("x", x);
        json.writeValue("y", y);
        json.writeValue("width", width);
        json.writeValue("height", height);
        json.writeValue("rotation", rotation);
        json.writeValue("omega", omega);
        json.writeValue("vx", vx);
        json.writeValue("vy", vy);
        json.writeValue("speed", speed);
        json.writeValue("hits", hits);
        json.writeValue("type1", type1);
        json.writeValue("type2", type2);
        json.writeValue("isReleased", isReleased);
    }

    @Override
    public void read(Json json, JsonValue jsonValue) {
        x = json.readValue("x", Float.class, jsonValue);
        y = json.readValue("y", Float.class, jsonValue);
        width = json.readValue("width", Float.class, jsonValue);
        height = json.readValue("height", Float.class, jsonValue);
        rotation = json.readValue("rotation", Float.class, jsonValue);
        omega = json.readValue("omega", Float.class, jsonValue);
        vx = json.readValue("vx", Float.class, jsonValue);
        vy = json.readValue("vy", Float.class, jsonValue);
        speed = json.readValue("speed", Float.class, jsonValue);
        hits = json.readValue("hits", Integer.class, jsonValue);
        type1 = json.readValue("type1", Integer.class, jsonValue);
        type2 = json.readValue("type2", Integer.class, jsonValue);
        isReleased = json.readValue("isReleased", Boolean.class, jsonValue);
    }
}
