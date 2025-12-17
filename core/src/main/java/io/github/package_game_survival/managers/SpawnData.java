package io.github.package_game_survival.managers;

import io.github.package_game_survival.interfaces.TipoEnemigo;

public class SpawnData {

    private final int id;
    private final TipoEnemigo tipo;
    private final float x;
    private final float y;

    public SpawnData(int id, TipoEnemigo tipo, float x, float y) {
        this.id = id;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public TipoEnemigo getTipo() {
        return tipo;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
