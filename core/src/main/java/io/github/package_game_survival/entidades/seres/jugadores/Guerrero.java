package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.SerVivo;
import io.github.package_game_survival.habilidades.AtaqueAranazo;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;

public class Guerrero extends Jugador {

    public Guerrero(float x, float y) {
        // Stats originales + Atlas de Jugador (Player)
        super("Guerrero", x, y, 1000, 120, 20,
            Assets.get(PathManager.PLAYER_ATLAS, TextureAtlas.class));

        // Habilidad original (Corta distancia, ancha)
        this.habilidadPrincipal = new AtaqueAranazo(0.5f, 0.1f, 25, 60f,
            40f, SerVivo.class, Color.BLUE);
    }
}
