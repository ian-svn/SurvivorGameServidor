package io.github.package_game_survival.managers;

import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public class GestorDesastresServidor {

    private String desastreActivo = "NONE";

    public void update(float delta, JugadorServidor jugador) {
        // Ejemplo: ventisca activa
        if ("VENTISCA".equals(desastreActivo) && jugador != null) {
            // lógica de daño si querés
        }
    }

    public String getDesastreActivo() {
        return desastreActivo;
    }

    public void activar(String nombre) {
        desastreActivo = nombre;
    }
}
