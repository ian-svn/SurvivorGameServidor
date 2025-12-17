package io.github.package_game_survival.entidades.objetos;

import io.github.package_game_survival.entidades.mapas.EscenarioServidor;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public class CamaServidor extends ObjetoServidor {

    public CamaServidor(float x, float y) {
        super(x, y, 32, 64);
    }

    /**
     * Dormir: avanza el tiempo hasta la noche
     */
    public void usar(JugadorServidor jugador, EscenarioServidor escenario) {
        escenario.dormirHastaLaNoche();
    }

    /**
     * La cama NO es un item
     */
    @Override
    public ItemServidor toItem() {
        return null;
    }
}
