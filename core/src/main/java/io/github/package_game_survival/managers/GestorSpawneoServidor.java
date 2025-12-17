package io.github.package_game_survival.managers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.package_game_survival.interfaces.TipoEnemigo;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GestorSpawneoServidor {

    private static final AtomicInteger ID_GEN = new AtomicInteger();
    private final Random random = new Random();

    private static final int MAX_INTENTOS = 20;

    private SpawnData ultimoSpawn;

    public void update(
        float delta,
        int noche,
        float anchoMapa,
        float altoMapa,
        Array<Rectangle> noTransitables
    ) {
        ultimoSpawn = intentarSpawn(noche, anchoMapa, altoMapa, noTransitables);
    }

    public SpawnData consumirUltimoSpawn() {
        SpawnData s = ultimoSpawn;
        ultimoSpawn = null;
        return s;
    }

    private SpawnData intentarSpawn(
        int noche,
        float anchoMapa,
        float altoMapa,
        Array<Rectangle> noTransitables
    ) {
        if (noche > 4) return null;

        float chance = 0.12f + (noche * 0.06f);
        if (random.nextFloat() > chance) return null;

        TipoEnemigo tipo = elegirEnemigoAleatorio(noche);

        float x = 0, y = 0;
        Rectangle rectSpawn = new Rectangle();
        boolean valido = false;

        for (int i = 0; i < MAX_INTENTOS && !valido; i++) {
            x = random.nextFloat() * anchoMapa;
            y = random.nextFloat() * altoMapa;

            rectSpawn.set(x, y, 32, 32);
            valido = esZonaLibre(rectSpawn, noTransitables);
        }

        if (!valido) return null;

        return new SpawnData(
            ID_GEN.incrementAndGet(),
            tipo,
            x,
            y
        );
    }

    private TipoEnemigo elegirEnemigoAleatorio(int noche) {
        int roll = random.nextInt(100);

        switch (noche) {
            case 1:
                return TipoEnemigo.INVASOR_DE_LA_LUNA;

            case 2:
                return roll < 70
                    ? TipoEnemigo.INVASOR_DE_LA_LUNA
                    : TipoEnemigo.INVASOR_ARQUERO;

            case 3:
                if (roll < 40) return TipoEnemigo.INVASOR_DE_LA_LUNA;
                if (roll < 80) return TipoEnemigo.INVASOR_ARQUERO;
                return TipoEnemigo.INVASOR_MAGO;

            default:
                return TipoEnemigo.INVASOR_DE_LA_LUNA;
        }
    }

    private boolean esZonaLibre(Rectangle r, Array<Rectangle> bloqueos) {
        for (Rectangle b : bloqueos) {
            if (r.overlaps(b)) return false;
        }
        return true;
    }
}
