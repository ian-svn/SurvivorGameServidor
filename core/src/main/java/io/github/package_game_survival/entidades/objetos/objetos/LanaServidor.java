package io.github.package_game_survival.entidades.objetos.objetos;

import io.github.package_game_survival.entidades.objetos.ItemServidor;
import io.github.package_game_survival.entidades.objetos.ObjetoServidor;
import io.github.package_game_survival.entidades.objetos.items.LanaItemServidor;

public class LanaServidor extends ObjetoServidor {

    public LanaServidor(float x, float y) {
        super(x, y, 32, 32);
    }

    @Override
    public ItemServidor toItem() {
        return new LanaItemServidor();
    }
}
