package io.github.package_game_survival.entidades.objetos.objetos;

import io.github.package_game_survival.entidades.objetos.ItemConsumibleServidor;
import io.github.package_game_survival.entidades.objetos.ObjetoConsumibleServidor;
import io.github.package_game_survival.entidades.objetos.items.CarneItemServidor;

public class CarneServidor extends ObjetoConsumibleServidor {

    public CarneServidor(float x, float y) {
        super(x, y,
            10,
            4, 0, 0,
            0f, 0f);
    }

    @Override
    protected ItemConsumibleServidor crearItem() {
        return new CarneItemServidor();
    }
}
