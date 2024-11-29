package com.badlogic;

import com.badlogic.gdx.physics.box2d.*;

public class DynamicBody {
    private final BodyDef bodyDef;
    private Body body;
    private final FixtureDef fixtureDef;

    public DynamicBody() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        fixtureDef = new FixtureDef();
    }

    public Body getBody() {
        return body;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
