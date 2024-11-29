package com.badlogic;

import com.badlogic.gdx.physics.box2d.*;

public class StaticBody {
    private final BodyDef bodyDef;
    private Body body;

    public StaticBody() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }
}
