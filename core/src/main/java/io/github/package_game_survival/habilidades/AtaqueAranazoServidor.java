package io.github.package_game_survival.habilidades;

import com.badlogic.gdx.math.Vector2;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;

public class AtaqueAranazoServidor extends AtaqueServidorBase {

    private final float ancho;

    public AtaqueAranazoServidor(
        float cooldown,
        float casteo,
        int danio,
        float rango,
        float ancho
    ) {
        super(cooldown, casteo, danio, rango);
        this.ancho = ancho;
    }

    @Override
    protected void onInicioCasteo() {
        // Servidor: sin animaciones
    }

    @Override
    protected void ejecutarGolpe() {

        Vector2 centroAtacante = atacante.getCentro();

        for (SerVivoServidor e : mundo.getSeresVivos()) {

            if (e == atacante) continue;

            Vector2 centroObjetivo = e.getCentro();

            float dx = centroObjetivo.x - centroAtacante.x;
            float dy = centroObjetivo.y - centroAtacante.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            if (dist <= rango) {
                e.alterarVida(-(danioBase + atacante.getDanio()));
            }
        }
    }

    public float getAncho() {
        return ancho;
    }
}
