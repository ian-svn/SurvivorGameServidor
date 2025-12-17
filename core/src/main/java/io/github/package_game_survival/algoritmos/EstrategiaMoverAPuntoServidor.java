package io.github.package_game_survival.algoritmos;

import com.badlogic.gdx.math.Vector2;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.interfaces.IEstrategiaMovimientoServidor;
import io.github.package_game_survival.interfaces.IMundoServidor;
import io.github.package_game_survival.entidades.mapas.MundoServidor;

public class EstrategiaMoverAPuntoServidor implements IEstrategiaMovimientoServidor {

    private final Vector2 destino = new Vector2();
    private boolean terminado = false;

    public EstrategiaMoverAPuntoServidor(float x, float y) {
        destino.set(x, y);
    }

    @Override
    public void actualizar(
        SerVivoServidor ser,
        float delta,
        IMundoServidor mundo
    ) {
        if (terminado) return;

        MundoServidor m = (MundoServidor) mundo;

        float dx = destino.x - ser.getX();
        float dy = destino.y - ser.getY();

        if (dx * dx + dy * dy < 4f) { // 2px de margen
            terminado = true;
            return;
        }

        float len = (float) Math.sqrt(dx * dx + dy * dy);
        dx /= len;
        dy /= len;

        float dist = ser.getVelocidad() * delta;

        float nx = ser.getX() + dx * dist;
        float ny = ser.getY() + dy * dist;

        if (!m.colisiona(ser.getRectColision().setPosition(nx, ny))) {
            ser.mover(nx, ny);
        }
    }

    @Override
    public boolean haTerminado() {
        return terminado;
    }
}
