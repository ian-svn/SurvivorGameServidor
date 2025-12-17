package io.github.package_game_survival.entidades.seres.animales;

public class JabaliServidor extends AnimalServidor {

    public JabaliServidor(int id, float x, float y) {
        super(
            id,
            "JABALI",
            x,
            y,
            54,     // vidaMax
            42f,    // velocidad
            15,     // daño
            35f,    // ancho colisión
            35f     // alto colisión
        );
    }

    @Override
    protected void updateIA(float delta) {
        // IA del jabalí (agresivo, embestir, etc)
    }
}
