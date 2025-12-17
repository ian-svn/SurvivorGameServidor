package io.github.package_game_survival.desastres;

import com.badlogic.gdx.math.Rectangle;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public class CharcoServidor {

    private final int id;
    private final Rectangle hitbox;
    private float cooldown = 0f;

    private static final float INTERVALO_DANO = 0.5f;
    private static final int DANIO = 15;

    public CharcoServidor(int id, float x, float y, float w, float h) {
        this.id = id;
        this.hitbox = new Rectangle(x, y, w, h);
    }

    public void update(float delta, JugadorServidor jugador) {
        if (cooldown > 0) cooldown -= delta;

        if (jugador == null) return;

        if (hitbox.overlaps(jugador.getRectColision()) && cooldown <= 0) {
            jugador.alterarVida(-DANIO);
            cooldown = INTERVALO_DANO;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public String snapshot() {
        return "CHARCO:" + id + ":" +
            hitbox.x + ":" + hitbox.y + ":" +
            hitbox.width + ":" + hitbox.height;
    }
}
