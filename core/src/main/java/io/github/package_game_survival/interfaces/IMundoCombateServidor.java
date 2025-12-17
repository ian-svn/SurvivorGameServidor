package io.github.package_game_survival.interfaces;

import com.badlogic.gdx.utils.Array;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.entidades.seres.enemigos.EnemigoServidor;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public interface IMundoCombateServidor {

    JugadorServidor getJugador();

    Array<EnemigoServidor> getEnemigos();

    Array<SerVivoServidor> getSeresVivos();
}
