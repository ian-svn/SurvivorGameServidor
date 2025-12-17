package io.github.package_game_survival.desastres;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.package_game_survival.entidades.seres.enemigos.EnemigoServidor;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;
import io.github.package_game_survival.interfaces.IMundoServidor;

public class TornadoServidor {

    private final int id;
    private final Rectangle hitbox;
    private final Vector2 direccion = new Vector2();

    private static final float VELOCIDAD = 200f;
    private static final float INTERVALO_DANO = 0.2f;
    private static final int DANIO_JUGADOR = 20;
    private static final int DANIO_ENEMIGO = 40;

    private float cooldownDano = 0f;

    public TornadoServidor(int id, float x, float y, float w, float h, Vector2 dir) {
        this.id = id;
        this.hitbox = new Rectangle(x, y, w, h);
        this.direccion.set(dir).nor();
    }

    public void update(float delta, IMundoServidor mundo) {
        hitbox.x += direccion.x * VELOCIDAD * delta;
        hitbox.y += direccion.y * VELOCIDAD * delta;

        if (cooldownDano > 0) cooldownDano -= delta;

        if (cooldownDano <= 0) {
            // Jugador
            JugadorServidor jugador = mundo.getJugador();
            if (jugador != null && hitbox.overlaps(jugador.getRectColision())) {
                jugador.alterarVida(-DANIO_JUGADOR);
                cooldownDano = INTERVALO_DANO;
            }

            // Enemigos
            for (EnemigoServidor e : mundo.getEnemigos()) {
                if (hitbox.overlaps(e.getRectColision())) {
                    e.alterarVida(-DANIO_ENEMIGO);
                    cooldownDano = INTERVALO_DANO;
                }
            }
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public String snapshot() {
        return "TORNADO:" + id + ":" + hitbox.x + ":" + hitbox.y;
    }
}
