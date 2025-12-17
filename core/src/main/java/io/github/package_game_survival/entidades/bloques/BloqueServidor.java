package io.github.package_game_survival.entidades.bloques;

import com.badlogic.gdx.math.Rectangle;

public abstract class BloqueServidor {

    protected final Rectangle rect;
    protected final boolean transitable;

    protected BloqueServidor(float x, float y, float w, float h, boolean transitable) {
        this.transitable = transitable;
        this.rect = new Rectangle(x, y, w, h);
    }

    public Rectangle getRectColision() {
        return rect;
    }

    public boolean isTransitable() {
        return transitable;
    }
}
