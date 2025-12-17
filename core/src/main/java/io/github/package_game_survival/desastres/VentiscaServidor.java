package io.github.package_game_survival.desastres;


import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public class VentiscaServidor {

    private final JugadorServidor jugador;

    private float acumuladorDano = 0f;
    private float tiempoSinCalor = 0f;

    private static final float TIEMPO_INICIO_DANO = 2f;
    private static final int DANIO_POR_TICK = 10;

    public VentiscaServidor(JugadorServidor jugador) {
        this.jugador = jugador;
    }

    public void update(float delta) {
        if (jugador == null) return;

        if (!jugador.isSintiendoCalor()) {
            tiempoSinCalor += delta;

            if (tiempoSinCalor >= TIEMPO_INICIO_DANO) {
                acumuladorDano += delta * 5f;

                if (acumuladorDano >= 1f) {
                    jugador.alterarVida(-DANIO_POR_TICK);
                    acumuladorDano -= 1f;
                }
            }
        } else {
            tiempoSinCalor = 0;
            acumuladorDano = 0;
        }
    }
}
