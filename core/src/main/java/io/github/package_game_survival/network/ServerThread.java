package io.github.package_game_survival.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ServerThread modificado para:
 * - Mantener estado de jugadores (posición, velocidad)
 * - Procesar INPUT del cliente y actualizar posición en un tick fijo (autoridad)
 * - Enviar STATE:<id>:<x>:<y> a todos los clientes en cada tick (broadcast)
 *
 * NOTA: Este código usa UDP (DatagramSocket) como tu proyecto original.
 */
public class ServerThread extends Thread {

    private DatagramSocket socket;
    private int serverPort = 5555; // Puerto donde escucha el servidor
    private boolean end = false;
    private final int MAX_CLIENTS = 2;

    // Lista de clientes (información de conexión y clase elegida)
    private final ArrayList<Client> clients = new ArrayList<>();

    // Estado dinámico por cliente (clave = ip:port)
    private final ConcurrentHashMap<String, PlayerState> playerStates = new ConcurrentHashMap<>();

    // Control de tick (autoridad)
    private final ScheduledExecutorService tickExecutor = Executors.newSingleThreadScheduledExecutor();
    private final int TICK_RATE = 20; // ticks por segundo
    private final float DELTA = 1f / TICK_RATE;

    // Mundo (limites simples) - adaptá si tenés dimensiones en Escenario/MyGame
    private final float WORLD_WIDTH = 2000f;
    private final float WORLD_HEIGHT = 2000f;

    public ServerThread() {
        try {
            // El servidor especifica el puerto en el constructor
            socket = new DatagramSocket(serverPort);
            System.out.println("SERVIDOR UDP: Esperando en puerto " + serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Iniciamos el loop de tick (movimiento y broadcast)
        tickExecutor.scheduleAtFixedRate(this::tick, 0, 1000 / TICK_RATE, TimeUnit.MILLISECONDS);

        while (!end) {
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                // Bloqueante: espera mensaje UDP
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength()).trim();
                procesarMensaje(msg, packet);
            } catch (IOException e) {
                if (!end) e.printStackTrace();
            }
        }

        // cerrar executor si termina
        tickExecutor.shutdownNow();
        if (socket != null && !socket.isClosed()) socket.close();
    }

    /**
     * Tick del servidor: actualiza posiciones de acuerdo a velocidades y difunde estados.
     */
    private void tick() {
        try {
            if (playerStates.isEmpty()) return;

            // Actualizar posiciones
            for (PlayerState ps : playerStates.values()) {
                // Se asume vx,vy en {-1,0,1} o valores normalizados
                ps.x += ps.vx * ps.speed * DELTA;
                ps.y += ps.vy * ps.speed * DELTA;

                // Clampeo a límites del mundo
                if (ps.x < 0) ps.x = 0;
                if (ps.y < 0) ps.y = 0;
                if (ps.x > WORLD_WIDTH) ps.x = WORLD_WIDTH;
                if (ps.y > WORLD_HEIGHT) ps.y = WORLD_HEIGHT;
            }

            // Broadcast del estado de cada jugador
            for (PlayerState ps : playerStates.values()) {
                String stateMsg = String.format("STATE:%d:%f:%f", ps.id, ps.x, ps.y);
                sendMessageToAll(stateMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void procesarMensaje(String msg, DatagramPacket packet) {
        // Formato esperado:
        // CONNECT:CLASE
        // INPUT:DX:DY
        try {
            if (msg.startsWith("CONNECT")) {
                String[] partes = msg.split(":");
                String clase = partes.length > 1 ? partes[1] : "GUERRERO";

                InetAddress ipJugador = packet.getAddress();
                int puertoJugador = packet.getPort();

                // Chequear duplicado
                boolean existe = false;
                for (Client c : clients) {
                    if (c.getIp().equals(ipJugador) && c.getPort() == puertoJugador) {
                        existe = true;
                        break;
                    }
                }

                if (!existe && clients.size() < MAX_CLIENTS) {
                    int id = clients.size() + 1;
                    Client nuevo = new Client(id, ipJugador, puertoJugador, clase);
                    clients.add(nuevo);

                    // Crear estado inicial del jugador (spawn simple: disperso por id)
                    float spawnX = 400 + (id - 1) * 80;
                    float spawnY = 300;
                    PlayerState ps = new PlayerState(id, spawnX, spawnY, clase, ipJugador, puertoJugador);
                    playerStates.put(getKey(ipJugador, puertoJugador), ps);

                    System.out.println("NUEVO JUGADOR: ID " + id + " - " + clase + " (" + getKey(ipJugador, puertoJugador) + ")");

                    // Enviamos al cliente su ID asignado
                    sendMessage("CONNECTED:" + id, ipJugador, puertoJugador);

                    // Si se llenó la sala, avisamos con las clases en orden (J1,J2,...)
                    if (clients.size() == MAX_CLIENTS) {
                        System.out.println("SALA LLENA. INICIANDO...");
                        StringBuilder start = new StringBuilder("START");
                        for (Client c : clients) {
                            start.append(":").append(c.getClaseElegida());
                        }
                        sendMessageToAll(start.toString());
                    }
                }
            }
            else if (msg.startsWith("INPUT")) {
                // INPUT:DX:DY  (DX, DY son enteros -1,0,1)
                String[] p = msg.split(":");
                if (p.length >= 3) {
                    int dx = Integer.parseInt(p[1]);
                    int dy = Integer.parseInt(p[2]);

                    InetAddress ip = packet.getAddress();
                    int port = packet.getPort();
                    String key = getKey(ip, port);
                    PlayerState ps = playerStates.get(key);
                    if (ps != null) {
                        // Asignamos intención de movimiento (se aplica en tick)
                        ps.vx = dx;
                        ps.vy = dy;
                        // (opcional) normalizar para movimientos diagonales:
                        if (dx != 0 && dy != 0) {
                            // normalizamos a 0.707 para mantener velocidad uniforme (opcional)
                            ps.vx = dx * 0.70710677f;
                            ps.vy = dy * 0.70710677f;
                        }
                    }
                }
            }
            // otros comandos pueden procesarse aquí
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String msg, InetAddress ip, int port) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket p = new DatagramPacket(data, data.length, ip, port);
            socket.send(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAll(String msg) {
        for (Client c : clients) {
            sendMessage(msg, c.getIp(), c.getPort());
        }
    }

    public int getConnectedClients() {
        return clients.size();
    }

    public void terminate() {
        end = true;
        tickExecutor.shutdownNow();
        if (socket != null) socket.close();
    }

    private String getKey(InetAddress ip, int port) {
        return ip.toString() + ":" + port;
    }

    /**
     * Estado simple por jugador mantenido por el servidor.
     */
    private static class PlayerState {
        int id;
        float x, y;
        float vx = 0f, vy = 0f;
        float speed = 200f; // px/s
        String clase;
        InetAddress ip;
        int port;

        PlayerState(int id, float x, float y, String clase, InetAddress ip, int port) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.clase = clase;
            this.ip = ip;
            this.port = port;
        }
    }
}
