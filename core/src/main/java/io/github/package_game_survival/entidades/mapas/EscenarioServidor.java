package io.github.package_game_survival.entidades.mapas;

import com.badlogic.gdx.math.Rectangle;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;
import io.github.package_game_survival.managers.GestorTiempoServidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EscenarioServidor {

    private final Map<Integer, JugadorServidor> jugadores = new HashMap<>();
    private final List<Rectangle> bloques = new ArrayList<>();

    private final GestorTiempoServidor gestorTiempo = new GestorTiempoServidor();

    // ======================
    // JUGADORES
    // ======================
    public void agregarJugador(JugadorServidor jugador) {
        jugadores.put(jugador.getId(), jugador);
    }

    public JugadorServidor getJugador(int id) {
        return jugadores.get(id);
    }

    public int getCantidadJugadores() {
        return jugadores.size();
    }

    // ======================
    // BLOQUES
    // ======================
    public void agregarBloque(Rectangle r) {
        bloques.add(r);
    }

    public boolean colisionaConBloque(float x, float y, float w, float h) {
        Rectangle aux = new Rectangle(x, y, w, h);
        for (Rectangle b : bloques) {
            if (b.overlaps(aux)) return true;
        }
        return false;
    }

    public List<Rectangle> getBloques() {
        return bloques;
    }

    // ======================
    // TIEMPO
    // ======================
    public void dormirHastaLaNoche() {
        gestorTiempo.dormirHastaLaNoche();
    }

    // ======================
    // UPDATE
    // ======================
    public void update(float delta) {
        gestorTiempo.update(delta);
        for (JugadorServidor j : jugadores.values()) {
            j.update(delta, this);
        }
    }
}
