package io.github.package_game_survival.entidades.seres.animales;

import io.github.package_game_survival.entidades.seres.SerVivoServidor;

public abstract class AnimalServidor extends SerVivoServidor {

    public AnimalServidor(
        int id,
        String nombre,
        float x,
        float y,
        int vidaMax,
        float velocidad,
        int danio,
        float anchoColision,
        float altoColision
    ) {
        super(
            id,
            nombre,
            x,
            y,
            vidaMax,
            velocidad,
            danio,
            anchoColision,
            altoColision
        );
    }

    // ======================
    // UPDATE GENERAL
    // ======================
    @Override
    public void update(float delta, Object mundo) {
        updateIA(delta);
    }

    // ======================
    // IA PROPIA DEL ANIMAL
    // ======================
    protected abstract void updateIA(float delta);
}
