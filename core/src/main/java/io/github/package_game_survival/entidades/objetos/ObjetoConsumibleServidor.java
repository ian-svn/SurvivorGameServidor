package io.github.package_game_survival.entidades.objetos;


public abstract class ObjetoConsumibleServidor extends ObjetoServidor {

    protected final int vidaCurada;
    protected final int bonoDanio;
    protected final int bonoVelocidad;
    protected final int bonoVidaMaxima;
    protected final float bonoRango;
    protected final float bonoArea;

    protected ObjetoConsumibleServidor(
        float x, float y,
        int vidaCurada,
        int bonoDanio,
        int bonoVelocidad,
        int bonoVidaMaxima,
        float bonoRango,
        float bonoArea
    ) {
        super(x, y, 32, 32);
        this.vidaCurada = vidaCurada;
        this.bonoDanio = bonoDanio;
        this.bonoVelocidad = bonoVelocidad;
        this.bonoVidaMaxima = bonoVidaMaxima;
        this.bonoRango = bonoRango;
        this.bonoArea = bonoArea;
    }

    /** Cada consumible crea SU item */
    protected abstract ItemConsumibleServidor crearItem();

    @Override
    public final ItemServidor toItem() {
        return crearItem();
    }
}
