package io.github.package_game_survival.entidades.seres.animales;

public class OvejaServidor extends AnimalServidor {

    public OvejaServidor(int id, float x, float y) {
        super(
            id,
            "OVEJA",
            x,
            y,
            42,     // vidaMax
            45f,    // velocidad
            0,      // daño
            28f,    // ancho colisión
            28f     // alto colisión
        );
    }

    @Override
    protected void updateIA(float delta) {
        // lógica de oveja
    }
}
