package io.github.package_game_survival.interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.entidades.seres.animales.AnimalServidor;
import io.github.package_game_survival.entidades.seres.enemigos.EnemigoServidor;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;
import io.github.package_game_survival.entidades.objetos.ObjetoServidor;

public interface IMundoServidor {

    JugadorServidor getJugador();

    JugadorServidor buscarJugadorMasCercano(SerVivoServidor origen);

    Array<SerVivoServidor> getSeresVivos();
    Array<EnemigoServidor> getEnemigos();
    Array<AnimalServidor> getAnimales();
    Array<ObjetoServidor> getObjetos();

    boolean colisiona(Rectangle rect);

    float getAncho();
    float getAlto();

    Array<Rectangle> getRectangulosNoTransitables();
}
