package io.github.package_game_survival.entidades.mapas;

import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;
import io.github.package_game_survival.managers.GestorDesastresServidor;
import io.github.package_game_survival.managers.GestorSpawneoServidor;
import io.github.package_game_survival.managers.GestorTiempoServidor;
import io.github.package_game_survival.managers.SpawnData;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EscenarioServidor {

    private final Map<Integer, JugadorServidor> jugadores = new HashMap<>();

    private final GestorTiempoServidor gestorTiempo;
    private final GestorDesastresServidor gestorDesastres;
    private final GestorSpawneoServidor gestorSpawneo;

    private final MundoServidor mundo;

    public EscenarioServidor() {
        this.gestorTiempo = new GestorTiempoServidor();
        this.gestorDesastres = new GestorDesastresServidor();
        this.gestorSpawneo = new GestorSpawneoServidor();
        this.mundo = new MundoServidor(2000, 2000);
    }

    public void update(float delta) {

        // ======================
        // TIEMPO
        // ======================
        gestorTiempo.update(delta);

        // ======================
        // JUGADORES + DESASTRES
        // ======================
        for (JugadorServidor j : jugadores.values()) {
            j.update(delta, mundo);
            gestorDesastres.update(delta, j);
        }

        // ======================
        // SPAWNEO (CORREGIDO)
        // ======================
        gestorSpawneo.update(
            delta,
            gestorTiempo.getNoche(),
            mundo.getAncho(),
            mundo.getAlto(),
            mundo.getRectangulosNoTransitables()
        );

        SpawnData spawn = gestorSpawneo.consumirUltimoSpawn();
        if (spawn != null) {
            // acá después vas a crear el enemigo real
            System.out.println("Spawn enemigo: " + spawn.getTipo());
        }
    }

    public void agregarJugador(int id) {
        JugadorServidor j = new JugadorServidor(id, 400, 300);
        jugadores.put(id, j);
        mundo.setJugador(j);
    }

    public void aplicarInput(int id, int dx, int dy) {
        JugadorServidor j = jugadores.get(id);
        if (j != null) {
            j.setInput(dx, dy);
        }
    }

    public String generarSnapshot() {
        StringBuilder sb = new StringBuilder();

        sb.append("WORLD\n");
        sb.append("TIME:")
            .append(gestorTiempo.getDia()).append(":")
            .append(gestorTiempo.getHora()).append(":")
            .append(gestorTiempo.getMinuto()).append("\n");

        sb.append("DISASTER:")
            .append(gestorDesastres.getDesastreActivo()).append("\n");

        for (JugadorServidor j : jugadores.values()) {
            sb.append("PLAYER:")
                .append(j.getId()).append(":")
                .append(String.format(Locale.US, "%.2f", j.getX())).append(":")
                .append(String.format(Locale.US, "%.2f", j.getY()))
                .append("\n");
        }

        sb.append("END");
        return sb.toString();
    }

    public Map<Integer, JugadorServidor> getJugadores() {
        return jugadores;
    }

    public void dormirHastaLaNoche() {
        gestorTiempo.avanzarHastaLaNoche();
    }
}
