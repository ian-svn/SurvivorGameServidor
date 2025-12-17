package io.github.package_game_survival.network;

import java.net.InetAddress;

public class Client {
    private int num; // ID del jugador (1 o 2)
    private InetAddress ip;
    private int port;
    private String claseElegida; // "GUERRERO" o "CAZADOR"

    public Client(int num, InetAddress ip, int port, String claseElegida) {
        this.num = num;
        this.ip = ip;
        this.port = port;
        this.claseElegida = claseElegida;
    }

    public InetAddress getIp() { return ip; }
    public int getPort() { return port; }
    public String getClaseElegida() { return claseElegida; }

    // Identificador único (IP:Puerto)
    public String getId() { return ip.toString() + ":" + port; }
}
