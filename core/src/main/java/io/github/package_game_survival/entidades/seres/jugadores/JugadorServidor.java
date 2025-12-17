package io.github.package_game_survival.entidades.seres.jugadores;

import io.github.package_game_survival.entidades.mapas.EscenarioServidor;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;

public class JugadorServidor extends SerVivoServidor {

    private float objetivoX, objetivoY;
    private boolean moviendose = false;

    public JugadorServidor(int id, float x, float y) {
        super(id, "Jugador", x, y, 100, 120f, 10, 32, 32);
    }

    @Override
    public void update(float delta, Object mundoObj) {
        EscenarioServidor mundo = (EscenarioServidor) mundoObj;

        if (!moviendose) return;

        float dx = objetivoX - x;
        float dy = objetivoY - y;

        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist < 2f) {
            moviendose = false;
            return;
        }

        float nx = x + (dx / dist) * velocidad * delta;
        float ny = y + (dy / dist) * velocidad * delta;

        if (!mundo.colisionaConBloque(nx, ny, rectColision.width, rectColision.height)) {
            mover(nx, ny);
        } else {
            moviendose = false;
        }
    }

    public void setObjetivo(float x, float y) {
        this.objetivoX = x;
        this.objetivoY = y;
        this.moviendose = true;
    }
}
