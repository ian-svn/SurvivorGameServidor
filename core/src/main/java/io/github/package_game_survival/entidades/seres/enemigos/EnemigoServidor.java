package io.github.package_game_survival.entidades.seres.enemigos;

import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.interfaces.IAtaqueServidor;

public abstract class EnemigoServidor extends SerVivoServidor {

    protected float rangoAtaque;
    protected float areaAtaque;
    protected float rangoDeteccion;

    protected IAtaqueServidor habilidadPrincipal;

    public EnemigoServidor(
        int id,
        String nombre,
        float x,
        float y,
        int vidaMax,
        int danio,
        float velocidad,
        float anchoColision,
        float altoColision,
        float rangoAtaque,
        float areaAtaque,
        float rangoDeteccion
    ) {
        super(
            id,
            nombre,
            x, y,
            vidaMax,
            velocidad,
            danio,
            anchoColision,
            altoColision
        );

        this.rangoAtaque = rangoAtaque;
        this.areaAtaque = areaAtaque;
        this.rangoDeteccion = rangoDeteccion;
    }
}
