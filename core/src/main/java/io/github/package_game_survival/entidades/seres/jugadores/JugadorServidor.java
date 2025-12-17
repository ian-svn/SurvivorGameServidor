package io.github.package_game_survival.entidades.seres.jugadores;

import io.github.package_game_survival.entidades.mapas.MundoServidor;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;

public class JugadorServidor extends SerVivoServidor {

    private int inputX;
    private int inputY;

    public JugadorServidor(
        float x,
        float y,
        int vidaMax,
        int danio,
        float velocidad,
        float rangoAtaque,
        float areaAtaque
    ) {
        super(
            generarId(),
            "JUGADOR",
            x,
            y,
            vidaMax,
            velocidad,
            danio,
            32f,
            32f
        );

        this.rangoAtaque = rangoAtaque;
        this.areaAtaque = areaAtaque;
    }

    public JugadorServidor(int id, float x, float y) {
        this(
            x, y,          // posición
            120,           // vidaMax
            10,            // daño base
            80f,           // velocidad
            60f,           // rango ataque
            40f            // área ataque
        );
        this.id = id;
    }


    private static int generarId() {
        return (int) (System.nanoTime() & 0x7FFFFFFF);
    }

    public void setInput(int dx, int dy) {
        inputX = dx;
        inputY = dy;
    }

    @Override
    public void update(float delta, Object mundoObj) {
        MundoServidor mundo = (MundoServidor) mundoObj;

        float nx = x + inputX * velocidad * delta;
        float ny = y + inputY * velocidad * delta;

        if (!mundo.colisiona(nx, ny, rectColision.width, rectColision.height)) {
            mover(nx, ny);
        }
    }

    protected boolean sintiendoCalor = false;

    public boolean isSintiendoCalor() {
        return sintiendoCalor;
    }

    public void setSintiendoCalor(boolean sintiendoCalor) {
        this.sintiendoCalor = sintiendoCalor;
    }
}
