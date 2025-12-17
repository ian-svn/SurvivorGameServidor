package io.github.package_game_survival.entidades.objetos.items;

import io.github.package_game_survival.entidades.objetos.ItemConsumibleServidor;

public class LanaItemServidor extends ItemConsumibleServidor {

    public LanaItemServidor() {
        super(
            "Lana",
            0,      // vida
            0,      // daño
            0,      // velocidad
            0,      // vida máxima
            15f,    // rango de ataque
            8f      // área / grosor del arañazo
        );
    }
}
