package io.github.package_game_survival.entidades.objetos.objetos;

import io.github.package_game_survival.entidades.objetos.ItemConsumibleServidor;
import io.github.package_game_survival.entidades.objetos.ObjetoConsumibleServidor;
import io.github.package_game_survival.entidades.objetos.items.PocionDeAmatistaItemServidor;

public class PocionDeAmatistaServidor extends ObjetoConsumibleServidor {

    public PocionDeAmatistaServidor(float x, float y) {
        super(x, y,
            25,
            0, 0, 5,
            0f, 0f);
    }

    @Override
    protected ItemConsumibleServidor crearItem() {
        return new PocionDeAmatistaItemServidor();
    }
}
