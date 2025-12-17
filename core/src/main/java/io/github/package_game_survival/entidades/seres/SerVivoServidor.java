package io.github.package_game_survival.entidades.seres;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class SerVivoServidor {

    // ======================
    // IDENTIDAD
    // ======================
    protected int id;
    protected final String nombre;

    // ======================
    // POSICIÓN
    // ======================
    protected float x;
    protected float y;

    // ======================
    // VIDA
    // ======================
    protected int vida;
    protected int vidaMax;

    // ======================
    // COMBATE
    // ======================
    protected int danio;
    protected float velocidad;

    protected float rangoAtaque;
    protected float areaAtaque;

    // ======================
    // COLISIÓN
    // ======================
    protected final Rectangle rectColision;

    // ======================
    // CONSTRUCTOR CORRECTO
    // ======================
    public SerVivoServidor(
        int id,
        String nombre,
        float x,
        float y,
        int vidaMax,
        float velocidad,
        int danio,
        float anchoColision,
        float altoColision
    ) {
        this.id = id;
        this.nombre = nombre;

        this.x = x;
        this.y = y;

        this.vidaMax = vidaMax;
        this.vida = vidaMax;

        this.velocidad = velocidad;
        this.danio = danio;

        this.rectColision = new Rectangle(x, y, anchoColision, altoColision);
    }

    // ======================
    // UPDATE
    // ======================
    public abstract void update(float delta, Object mundo);

    // ======================
    // MOVIMIENTO
    // ======================
    public void mover(float nx, float ny) {
        x = nx;
        y = ny;
        rectColision.setPosition(nx, ny);
    }

    // ======================
    // VIDA
    // ======================
    public void alterarVida(int delta) {
        vida += delta;
        if (vida < 0) vida = 0;
        if (vida > vidaMax) vida = vidaMax;
    }

    public void aumentarVidaMaxima(int delta) {
        vidaMax += delta;
        if (vidaMax < 1) vidaMax = 1;
        vida = Math.min(vida, vidaMax);
    }

    // ======================
    // STATS
    // ======================
    public void alterarDanio(int delta) {
        danio = Math.max(0, danio + delta);
    }

    public void alterarVelocidad(float delta) {
        velocidad = Math.max(0, velocidad + delta);
    }

    public void mejorarRangoAtaque(float rango, float area) {
        rangoAtaque = Math.max(0, rangoAtaque + rango);
        areaAtaque = Math.max(0, areaAtaque + area);
    }

    // ======================
    // GETTERS
    // ======================
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getVelocidad() { return velocidad; }
    public int getDanio() { return danio; }
    public Rectangle getRectColision() { return rectColision; }

    public Vector2 getCentro() {
        return new Vector2(
            x + rectColision.width / 2f,
            y + rectColision.height / 2f
        );
    }

    // ======================
    // MIRAR
    // ======================
    public void mirarA(SerVivoServidor otro) {}
}
