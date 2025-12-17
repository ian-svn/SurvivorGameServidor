package io.github.package_game_survival.managers;

public class GestorTiempoServidor {

    // =========================
    // TIEMPO DEL DIA
    // =========================
    private float tiempoDelDia = 8f; // empieza de dia
    private static final float VELOCIDAD_TIEMPO = 0.1f;
    private static final float NOCHE = 18f;

    // =========================
    // UPDATE
    // =========================
    public void update(float delta) {
        tiempoDelDia += delta * VELOCIDAD_TIEMPO;
        if (tiempoDelDia >= 24f) {
            tiempoDelDia = 0f;
        }
    }

    // =========================
    // ACCIONES
    // =========================
    public void dormirHastaLaNoche() {
        tiempoDelDia = NOCHE;
        System.out.println("GestorTiempoServidor: dormir hasta la noche");
    }

    // =========================
    // GETTERS
    // =========================
    public float getTiempoDelDia() {
        return tiempoDelDia;
    }

    public boolean esDeNoche() {
        return tiempoDelDia >= NOCHE || tiempoDelDia < 6f;
    }
}
