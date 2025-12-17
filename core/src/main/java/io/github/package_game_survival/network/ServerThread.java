package io.github.package_game_survival.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerThread extends Thread {

    private DatagramSocket socket;
    private int serverPort = 5555; // Puerto donde escucha el servidor
    private boolean end = false;
    private final int MAX_CLIENTS = 2;
    private ArrayList<Client> clients = new ArrayList<>();
    private GameController gameController;

    public ServerThread(GameController gameController) {
        this.gameController = gameController;
        try {
            // El servidor SÍ debe especificar el puerto en el constructor
            socket = new DatagramSocket(serverPort);
            System.out.println("SERVIDOR UDP: Esperando en puerto " + serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!end) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                // Se queda esperando mensaje
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength()).trim();
                procesarMensaje(msg, packet);
            } catch (IOException e) {
                if (!end) e.printStackTrace();
            }
        }
    }

    private void procesarMensaje(String msg, DatagramPacket packet) {
        // Formato esperado: CONNECT:CLASE (Ej: CONNECT:GUERRERO)
        if (msg.startsWith("CONNECT")) {
            String[] partes = msg.split(":");
            String clase = partes.length > 1 ? partes[1] : "GUERRERO";

            InetAddress ipJugador = packet.getAddress();
            int puertoJugador = packet.getPort();

            // Verificar si ya existe para no duplicarlo
            boolean existe = false;
            for(Client c : clients) {
                if(c.getIp().equals(ipJugador) && c.getPort() == puertoJugador) existe = true;
            }

            if (!existe && clients.size() < MAX_CLIENTS) {
                int id = clients.size() + 1;
                Client nuevo = new Client(id, ipJugador, puertoJugador, clase);
                clients.add(nuevo);

                System.out.println("NUEVO JUGADOR: ID " + id + " - " + clase);

                // Confirmamos al cliente que entró
                sendMessage("CONNECTED", ipJugador, puertoJugador);

                // Si se llenó la sala, avisamos
                if (clients.size() == MAX_CLIENTS) {
                    System.out.println("SALA LLENA. INICIANDO...");
                    // Enviamos START:ClaseJ1:ClaseJ2 a TODOS
                    String msgStart = "START:" + clients.get(0).getClaseElegida() + ":" + clients.get(1).getClaseElegida();
                    sendMessageToAll(msgStart);

                    if (gameController != null) gameController.startGame();
                }
            }
        }
        else if (msg.startsWith("MOVE")) {
            // Reenviar movimiento a todos los demás (Broadcast)
            // Lógica: MOVE:ID:X:Y
            sendMessageToAllExcept(msg, packet.getAddress(), packet.getPort());
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

    // Para no rebotar el movimiento al mismo que lo envió
    private void sendMessageToAllExcept(String msg, InetAddress ipExcluir, int portExcluir) {
        for(Client c : clients) {
            if (!c.getIp().equals(ipExcluir) || c.getPort() != portExcluir) {
                sendMessage(msg, c.getIp(), c.getPort());
            }
        }
    }

    public int getConnectedClients() {
        return clients.size();
    }

    public void terminate() {
        end = true;
        if(socket != null) socket.close();
    }
}
