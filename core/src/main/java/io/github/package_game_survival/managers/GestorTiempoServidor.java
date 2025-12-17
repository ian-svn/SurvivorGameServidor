package io.github.package_game_survival.managers;

public class GestorTiempoServidor {

    private int dia = 1;
    private int hora = 6;
    private int minuto = 0;

    private float acumulador = 0f;

    private static final float SEGUNDOS_POR_MINUTO = 1f;

    public void update(float delta) {
        acumulador += delta;

        if (acumulador >= SEGUNDOS_POR_MINUTO) {
            acumulador -= SEGUNDOS_POR_MINUTO;
            minuto++;

            if (minuto >= 60) {
                minuto = 0;
                hora++;

                if (hora >= 24) {
                    hora = 0;
                    dia++;
                }
            }
        }
    }

    // ======================
    // GETTERS
    // ======================
    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    /**
     * Noche = número de noches transcurridas
     * Día 1 noche = noche 1
     */
    public int getNoche() {
        return dia;
    }

    public boolean esDeNoche() {
        return hora >= 20 || hora < 6;
    }

    public void avanzarHastaLaNoche() {
        if (hora < 20) {
            hora = 20;
            minuto = 0;
        }
    }
}
