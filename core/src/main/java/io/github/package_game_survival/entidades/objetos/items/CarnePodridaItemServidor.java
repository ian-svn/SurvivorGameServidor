package io.github.package_game_survival.entidades.objetos.items;

import io.github.package_game_survival.entidades.objetos.ItemConsumibleServidor;

public class CarnePodridaItemServidor extends ItemConsumibleServidor {

    public CarnePodridaItemServidor() {
        super(
            "Carne Podrida",
            -5,   // vida (daño)
            0,
            3,    // velocidad
            0,
            0f,
            0f
        );
    }
}
