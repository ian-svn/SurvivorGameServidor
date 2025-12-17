package io.github.package_game_survival.entidades.seres.jugadores;

public class JugadorFactoryServidor {

    /**
     * Crea un jugador servidor con valores por defecto.
     * El ID lo asigna el servidor.
     */
    public static JugadorServidor crearJugador(int id, float x, float y) {
        return new JugadorServidor(id, x, y);
    }

    /**
     * Overload simple (spawn default)
     */
    public static JugadorServidor crearJugador(int id) {
        return new JugadorServidor(id, 400, 300);
    }
}
