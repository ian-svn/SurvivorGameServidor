package io.github.package_game_survival.network;

import com.badlogic.gdx.Gdx;
import io.github.package_game_survival.network.Client;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {

    private DatagramSocket socket;
    private int serverPort = 5555;
    private boolean end = false;
    private final int MAX_CLIENTS = 2;
    private int connectedClients = 0;
    private ArrayList<Client> clients = new ArrayList<Client>();
    public GameController gameController;

    public ServerThread(GameController gameController) {
        this.gameController = gameController;
        try {
            socket = new DatagramSocket(serverPort);
            System.out.println(" Servidor iniciado en puerto " + serverPort);
        } catch (SocketException e) {
            System.err.println(" Error creando socket del servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println(" ServerThread ejecutándose - Esperando clientes...");
        do {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(packet);
                processMessage(packet);
                System.out.println(new String(packet.getData()).trim());
            } catch (IOException e) {
                if (!end) {
                    System.err.println(" Error recibiendo paquete: " + e.getMessage());
                }
            }
        } while(!end);
    }

    private void processMessage(DatagramPacket packet) {
        String message = (new String(packet.getData())).trim();
        String[] parts = message.split(":");
        int index = findClientIndex(packet);
        System.out.println("Mensaje recibido " + message);

        if (parts[0].equals("Connect"))
        {//Si el principio del mensaje es CONNECT
            int personajeIdx = 0;
            if (parts.length > 1) {
                try {
                    personajeIdx = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Índice de personaje inválido");
                }
            }

            if (index != -1) {
                System.out.println("Client already connected");
                this.sendMessage("AlreadyConnected", packet.getAddress(), packet.getPort());
                return;
            }

            if (connectedClients < MAX_CLIENTS) {
                connectedClients++;
                Client newClient = new Client(connectedClients, packet.getAddress(), packet.getPort());
                clients.add(newClient);
//               personajesElegidos.add(personajeIdx);
//
                System.out.println("Cliente conectado #" + connectedClients + " con personaje " + personajeIdx);
                sendMessage("Connected:" + connectedClients, packet.getAddress(), packet.getPort());

                if (connectedClients == MAX_CLIENTS) {
                    StringBuilder data = new StringBuilder();
//                    for (int i = 0; i < personajesElegidos.size(); i++) {
//                        data.append(personajesElegidos.get(i));
//                        if (i < personajesElegidos.size() - 1)
//                            data.append(",");
//                    }

                    String mensajeStart = "Start:" + data;
                    for (Client client : clients) {
                        sendMessage(mensajeStart, client.getIp(), client.getPort());
                    }
                }
            } else {
                sendMessage("Full", packet.getAddress(), packet.getPort());
            }

        }
        else if (parts[0].equals("Disconnect")) {
            System.out.println("Cliente desconectado: " + packet.getAddress() + ":" + packet.getPort());
            clients.remove(index);
            connectedClients--;
//            gameController.actualizarJugadoresConectados();
            return;
        }

        else if (index == -1)
        {
            System.out.println("Client not connected");
            this.sendMessage("NotConnected", packet.getAddress(), packet.getPort());
            return;
        }
    }

    private int findClientIndex(DatagramPacket packet) {
        String id = packet.getAddress().toString() + ":" + packet.getPort();

        for (int i = 0; i < clients.size(); i++) {
            if (id.equals(clients.get(i).getId())) {
                return i;
            }
        }

        return -1;
    }

    public void sendMessage(String message, InetAddress clientIp, int clientPort) {
        byte[] byteMessage = message.getBytes();
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, clientIp, clientPort);
        try {
            socket.send(packet);
            System.out.println(" Mensaje enviado a " + clientIp + ":" + clientPort + " -> " + message);
        } catch (IOException e) {
            System.err.println(" Error enviando mensaje: " + e.getMessage());
        }
    }

    public void sendMessageToAll(String message) {
        System.out.println("Enviando a todos los clientes (" + clients.size() + "): " + message);
        for (Client client : clients) {
            sendMessage(message, client.getIp(), client.getPort());
        }
    }

    public void terminate(){
        System.out.println("Terminando ServerThread...");
        this.end = true;
        socket.close();
        this.interrupt();
    }

    public void disconnectClients() {
        System.out.println(" Desconectando todos los clientes...");
        for (Client client : clients) {
            sendMessage("Disconnect", client.getIp(), client.getPort());
        }
        this.clients.clear();
        this.connectedClients = 0;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public int getConnectedClients() {
        return connectedClients;
    }
}
