package io.github.package_game_survival.algoritmos;

import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.interfaces.IEstrategiaMovimientoServidor;
import io.github.package_game_survival.interfaces.IMundoServidor;

import java.util.Random;

public class EstrategiaMoverAleatorioServidor implements IEstrategiaMovimientoServidor {

    private float dirX = 1;
    private float dirY = 0;
    private float tiempo = 0;
    private float tiempoCambio = 2f;

    private final Random random = new Random();

    @Override
    public void actualizar(
        SerVivoServidor ser,
        float delta,
        IMundoServidor mundo
    ) {
        tiempo += delta;

        if (tiempo >= tiempoCambio) {
            tiempo = 0;
            tiempoCambio = 1.5f + random.nextFloat() * 2f;

            dirX = random.nextInt(3) - 1;
            dirY = random.nextInt(3) - 1;
        }

        if (dirX == 0 && dirY == 0) return;

        float dist = ser.getVelocidad() * delta;
        float nx = ser.getX() + dirX * dist;
        float ny = ser.getY() + dirY * dist;

        if (!mundo.colisiona(ser.getRectColision().setPosition(nx, ny))) {
            ser.mover(nx, ny);
        }
    }

    @Override
    public boolean haTerminado() {
        return false;
    }
}
