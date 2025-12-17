package io.github.package_game_survival.entidades.objetos;

import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public abstract class ItemServidor {

    protected final String nombre;

    protected ItemServidor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void usar(JugadorServidor jugador);
}
