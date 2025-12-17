package io.github.package_game_survival.entidades;

import com.badlogic.gdx.math.Rectangle;

public abstract class Entidad {

    protected float x, y;
    protected float width, height;

    protected final Rectangle rectColision = new Rectangle();

    protected Entidad(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        actualizarRect();
    }

    protected void actualizarRect() {
        rectColision.set(x, y, width, height);
    }

    public Rectangle getRectColision() {
        actualizarRect();
        return rectColision;
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
