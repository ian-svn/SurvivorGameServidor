package io.github.package_game_survival.interfaces;

import io.github.package_game_survival.entidades.seres.SerVivoServidor;

public interface IEstrategiaMovimientoServidor {

    void actualizar(
        SerVivoServidor ser,
        float delta,
        IMundoServidor mundo
    );

    boolean haTerminado();
}
