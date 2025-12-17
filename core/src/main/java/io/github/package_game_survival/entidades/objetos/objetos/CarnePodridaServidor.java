package io.github.package_game_survival.entidades.objetos.objetos;

import io.github.package_game_survival.entidades.objetos.ItemConsumibleServidor;
import io.github.package_game_survival.entidades.objetos.ObjetoConsumibleServidor;
import io.github.package_game_survival.entidades.objetos.items.CarnePodridaItemServidor;

public class CarnePodridaServidor extends ObjetoConsumibleServidor {

    public CarnePodridaServidor(float x, float y) {
        super(x, y,
            -5,
            0, 3, 0,
            0f, 0f);
    }

    @Override
    protected ItemConsumibleServidor crearItem() {
        return new CarnePodridaItemServidor();
    }
}
