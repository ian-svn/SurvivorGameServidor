package io.github.package_game_survival.entidades.seres.enemigos;

public class InvasorDeLaLunaServidor extends EnemigoServidor {

    public InvasorDeLaLunaServidor(int id, float x, float y) {
        super(
            id,
            "Invasor De La Luna",
            x, y,
            140,
            20,
            80f,
            32f, 32f,
            65f,
            10f,
            120f
        );
    }

    @Override
    public void update(float delta, Object mundo) {

    }
}
