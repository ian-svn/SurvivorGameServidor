package io.github.package_game_survival.interfaces;

import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public interface IMundoServidorJugador extends IMundoServidor {
    JugadorServidor getJugador();
}
