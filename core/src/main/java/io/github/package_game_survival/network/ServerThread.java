package io.github.package_game_survival.network;

import io.github.package_game_survival.entidades.mapas.EscenarioServidor;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerThread extends Thread {

    private static final int PORT = 5555;

    private final EscenarioServidor escenario;
    private final Map<Socket, PrintWriter> salidas = new HashMap<>();
    private final Map<Socket, JugadorServidor> jugadores = new HashMap<>();

    private int nextId = 1;

    public ServerThread(EscenarioServidor escenario) {
        this.escenario = escenario;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {

            new Thread(this::loopServidor).start();

            while (true) {
                Socket socket = server.accept();

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                JugadorServidor jugador = new JugadorServidor(nextId++, 400, 300);
                escenario.agregarJugador(jugador);

                jugadores.put(socket, jugador);
                salidas.put(socket, out);

                new Thread(() -> escucharCliente(socket, in)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // LOOP AUTORITATIVO
    // =========================
    private void loopServidor() {
        long last = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            float delta = (now - last) / 1_000_000_000f;
            last = now;

            escenario.update(delta);
            broadcastPosiciones();

            try {
                Thread.sleep(16);
            } catch (InterruptedException ignored) {}
        }
    }

    // =========================
    // ESCUCHAR CLIENTE
    // =========================
    private void escucharCliente(Socket socket, BufferedReader in) {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {

                if (msg.startsWith("MOVE_TO:")) {
                    String[] p = msg.split(":");
                    float x = Float.parseFloat(p[1]);
                    float y = Float.parseFloat(p[2]);

                    jugadores.get(socket).setObjetivo(x, y);
                }
            }
        } catch (Exception ignored) {}
    }

    // =========================
    // BROADCAST
    // =========================
    private void broadcastPosiciones() {
        for (Map.Entry<Socket, JugadorServidor> e : jugadores.entrySet()) {
            JugadorServidor j = e.getValue();

            String msg = "POS:" + j.getId() + ":" + j.getX() + ":" + j.getY();

            for (PrintWriter out : salidas.values()) {
                out.println(msg);
            }
        }
    }

    public int getConnectedClients() {
        return jugadores.size();
    }
}
