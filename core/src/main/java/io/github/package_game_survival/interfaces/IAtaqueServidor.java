package io.github.package_game_survival.interfaces;

import com.badlogic.gdx.math.Vector2;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;

public interface IAtaqueServidor {

    void intentarAtacar(
        SerVivoServidor atacante,
        Vector2 destino,
        IMundoCombateServidor mundo
    );

    void update(float delta);

    boolean estaCasteando();
    boolean isEnCooldown();
}
