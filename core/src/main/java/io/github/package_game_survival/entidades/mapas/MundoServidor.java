package io.github.package_game_survival.entidades.mapas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.package_game_survival.entidades.objetos.ObjetoServidor;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.entidades.seres.animales.AnimalServidor;
import io.github.package_game_survival.entidades.seres.enemigos.EnemigoServidor;
import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;
import io.github.package_game_survival.interfaces.IMundoCombateServidor;
import io.github.package_game_survival.interfaces.IMundoServidor;

public class MundoServidor implements IMundoServidor, IMundoCombateServidor {

    // ======================
    // MAPA
    // ======================
    private final float ancho;
    private final float alto;

    private final Array<Rectangle> rectangulosNoTransitables = new Array<>();

    // ======================
    // ENTIDADES
    // ======================
    private JugadorServidor jugador;

    private final Array<EnemigoServidor> enemigos = new Array<>();
    private final Array<AnimalServidor> animales = new Array<>();
    private final Array<ObjetoServidor> objetos = new Array<>();

    // ======================
    // CONSTRUCTOR
    // ======================
    public MundoServidor(float ancho, float alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    // ======================
    // JUGADOR
    // ======================
    @Override
    public JugadorServidor getJugador() {
        return jugador;
    }

    public void setJugador(JugadorServidor jugador) {
        this.jugador = jugador;
    }

    @Override
    public JugadorServidor buscarJugadorMasCercano(SerVivoServidor origen) {
        if (jugador == null || origen == null) return null;
        return jugador;
    }

    // ======================
    // SERES VIVOS
    // ======================
    @Override
    public Array<SerVivoServidor> getSeresVivos() {
        Array<SerVivoServidor> seres = new Array<>();

        if (jugador != null) seres.add(jugador);
        seres.addAll(enemigos);
        seres.addAll(animales);

        return seres;
    }

    // ======================
    // COLISIONES (🔥 CLAVE)
    // ======================
    @Override
    public boolean colisiona(Rectangle rect) {
        for (Rectangle r : rectangulosNoTransitables) {
            if (rect.overlaps(r)) return true;
        }
        return false;
    }


    // 🔥 OVERLOAD QUE TE FALTABA
    public boolean colisiona(float x, float y, float w, float h) {
        return colisiona(new Rectangle(x, y, w, h));
    }

    // ======================
    // GETTERS
    // ======================
    @Override
    public Array<EnemigoServidor> getEnemigos() {
        return enemigos;
    }

    @Override
    public Array<AnimalServidor> getAnimales() {
        return animales;
    }

    @Override
    public Array<ObjetoServidor> getObjetos() {
        return objetos;
    }

    @Override
    public Array<Rectangle> getRectangulosNoTransitables() {
        return rectangulosNoTransitables;
    }

    @Override
    public float getAncho() {
        return ancho;
    }

    @Override
    public float getAlto() {
        return alto;
    }

    // ======================
    // MAPA
    // ======================
    public void agregarBloque(Rectangle r) {
        rectangulosNoTransitables.add(r);
    }
}
