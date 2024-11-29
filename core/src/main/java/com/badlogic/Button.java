package com.badlogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button {
    private final Texture texture;
    private final Rectangle rect;

    public Button(Texture texture, float x, float y, float width, float height) {
        this.rect = new Rectangle(x, y, width, height);
        this.texture = texture;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean contains(float x, float y) {
        return rect.contains(x, y);
    }

    public void draw(Main g) {
        g.batch.draw(this.texture, rect.x, rect.y, rect.width, rect.height);
    }
}
