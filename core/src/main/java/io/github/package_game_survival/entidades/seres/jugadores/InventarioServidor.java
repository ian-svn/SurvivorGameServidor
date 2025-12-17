package io.github.package_game_survival.entidades.seres.jugadores;

import io.github.package_game_survival.entidades.objetos.ItemServidor;

public class InventarioServidor {

    private final ItemServidor[] slots = new ItemServidor[9];

    public boolean agregar(ItemServidor item) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = item;
                return true;
            }
        }
        return false;
    }

    public void usar(int slot, JugadorServidor jugador) {
        if (slot < 0 || slot >= slots.length) return;
        if (slots[slot] == null) return;

        slots[slot].usar(jugador);
        slots[slot] = null;
    }
}
