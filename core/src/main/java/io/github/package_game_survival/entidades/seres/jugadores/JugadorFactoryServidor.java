package io.github.package_game_survival.entidades.seres.jugadores;

public class JugadorFactoryServidor {

    public static JugadorServidor crear(
        ClaseJugador clase,
        float x,
        float y
    ) {
        switch (clase) {

            case GUERRERO:
                return new JugadorServidor(
                    x, y,
                    120,   // vida
                    25,    // daño
                    80f,   // velocidad
                    60f,   // rango
                    40f    // área
                );

            case CAZADOR:
                return new JugadorServidor(
                    x, y,
                    100,
                    15,
                    100f,
                    100f,
                    10f
                );

            default:
                throw new IllegalArgumentException("Clase no soportada");
        }
    }
}
