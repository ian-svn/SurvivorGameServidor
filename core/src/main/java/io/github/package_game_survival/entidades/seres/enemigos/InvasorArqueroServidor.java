package io.github.package_game_survival.entidades.seres.enemigos;

public class InvasorArqueroServidor extends EnemigoServidor {

    public InvasorArqueroServidor(int id, float x, float y) {
        super(
            id,
            "Invasor Arquero",
            x, y,
            180,
            20,
            135f,
            32f, 32f,
            150f,
            15f,
            150f
        );
    }

    @Override
    public void update(float delta, Object mundo) {

    }
}
