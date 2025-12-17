package io.github.package_game_survival.entidades.objetos.items;

import io.github.package_game_survival.entidades.objetos.ItemConsumibleServidor;

public class CarneItemServidor extends ItemConsumibleServidor {

    public CarneItemServidor() {
        super(
            "Carne",
            10,   // vida
            4,    // daño
            0,    // velocidad
            0,    // vida máxima
            0f,   // rango
            0f    // área
        );
    }
}
