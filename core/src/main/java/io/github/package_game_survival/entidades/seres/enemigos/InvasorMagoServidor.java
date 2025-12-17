package io.github.package_game_survival.entidades.seres.enemigos;

public class InvasorMagoServidor extends EnemigoServidor {

    public InvasorMagoServidor(int id, float x, float y) {
        super(
            id,
            "Invasor Mago",
            x, y,
            100,
            15,
            120f,
            32f, 32f,
            180f,
            60f,
            180f
        );
    }

    @Override
    public void update(float delta, Object mundo) {

    }
}
