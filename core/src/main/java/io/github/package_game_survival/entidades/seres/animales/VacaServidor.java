package io.github.package_game_survival.entidades.seres.animales;

public class VacaServidor extends AnimalServidor {

    public VacaServidor(int id, float x, float y) {
        super(
            id,
            "VACA",
            x,
            y,
            48,     // vidaMax
            32f,    // velocidad
            0,      // daño
            32f,    // ancho colisión
            32f     // alto colisión
        );
    }

    @Override
    protected void updateIA(float delta) {
        // caminar random, huir, etc
    }
}
