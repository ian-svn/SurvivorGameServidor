package io.github.package_game_survival.entidades.objetos;

import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoServidor {

    protected final Rectangle hitbox;
    protected boolean marcadoParaBorrar = false;

    protected ObjetoServidor(float x, float y, float w, float h) {
        this.hitbox = new Rectangle(x, y, w, h);
    }

    public Rectangle getRectColision() {
        return hitbox;
    }

    public boolean isMarcadoParaBorrar() {
        return marcadoParaBorrar;
    }

    public void marcarParaBorrar() {
        this.marcadoParaBorrar = true;
    }

    public void update(float delta) {
        // por defecto nada
    }

    /** Convierte el objeto del mundo en un ítem de inventario */
    public abstract ItemServidor toItem();
}
