package io.github.package_game_survival.entidades.seres.jugadores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.package_game_survival.entidades.seres.SerVivo;
import io.github.package_game_survival.habilidades.AtaqueAranazo;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;

public class Cazador extends Jugador {

    public Cazador(float x, float y) {
        // Stats: Menos vida, Más velocidad, Menos daño + Atlas de Arquero
        // ¡IMPORTANTE! Asegúrate de que el archivo 'atlases/archer.atlas' exista.
        // Si no existe aún, usa PLAYER_ATLAS temporalmente.
        super("Arquero", x, y, 600, 160, 12,
            Assets.get(PathManager.CAZADOR_ATLAS, TextureAtlas.class));

        // Habilidad: Larga distancia, fina, verde
        this.habilidadPrincipal = new AtaqueAranazo(0.4f, 0.1f, 15, 250f,
            10f, SerVivo.class, Color.GREEN);
    }
}
