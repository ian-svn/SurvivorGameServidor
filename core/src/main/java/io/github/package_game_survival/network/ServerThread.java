package io.github.package_game_survival.network;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {

    private DatagramSocket socket;
    private int serverPort = 5555;
    private boolean end = false;
    private final int MAX_CLIENTS = 2; // Espera exactamente 2 jugadores

    private ArrayList<Client> clients = new ArrayList<>();

    // Referencia al controlador para avisar a la pantalla
    private GameController gameController;

    // CONSTRUCTOR CORREGIDO: Ahora acepta el GameController
    public ServerThread(GameController gameController) {
        this.gameController = gameController;
        try {
            socket = new DatagramSocket(serverPort);
            System.out.println("SERVIDOR AUTORITARIO: Iniciado en puerto " + serverPort);
        } catch (SocketException e) {
            System.err.println("Error iniciando servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println("Esperando a 2 jugadores...");
        while (!end) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength()).trim();
                procesarMensaje(msg, packet);
            } catch (IOException e) {
                if (!end) e.printStackTrace();
            }
        }
    }

    private void procesarMensaje(String msg, DatagramPacket packet) {
        String[] parts = msg.split(":");

        if (parts.length > 0 && parts[0].equalsIgnoreCase("CONNECT")) {
            String clase = (parts.length > 1) ? parts[1] : "GUERRERO";
            handleConnection(packet, clase);
        }
    }

    private void handleConnection(DatagramPacket packet, String clase) {
        // Verificar si ya existe este cliente (IP:Port)
        String id = packet.getAddress().toString() + ":" + packet.getPort();
        for(Client c : clients) {
            if(c.getId().equals(id)) return;
        }

        if (clients.size() < MAX_CLIENTS) {
            int numJugador = clients.size() + 1;
            Client nuevo = new Client(numJugador, packet.getAddress(), packet.getPort(), clase);
            clients.add(nuevo);

            System.out.println("Jugador " + numJugador + " conectado (" + clase + ")");

            // Confirmar conexión al cliente
            sendMessage("CONNECTED", nuevo.getIp(), nuevo.getPort());

            // Si ya hay 2, arrancamos
            if (clients.size() == MAX_CLIENTS) {
                System.out.println("¡SALA LLENA! Enviando START...");
                String msgStart = "START:" + clients.get(0).getClaseElegida() + ":" + clients.get(1).getClaseElegida();
                sendMessageToAll(msgStart);

                // AVISAR A LA PANTALLA DEL SERVIDOR (UI)
                if (gameController != null) {
                    gameController.startGame();
                }
            }
        } else {
            sendMessage("FULL", packet.getAddress(), packet.getPort());
        }
    }

    private void sendMessage(String msg, InetAddress ip, int port) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket p = new DatagramPacket(data, data.length, ip, port);
            socket.send(p);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void sendMessageToAll(String msg) {
        for(Client c : clients) sendMessage(msg, c.getIp(), c.getPort());
    }

    public void terminate() {
        end = true;
        if(socket != null) socket.close();
    }

    // MÉTODO QUE FALTABA Y DABA ERROR
    public int getConnectedClients() {
        return clients.size();
    }
}
