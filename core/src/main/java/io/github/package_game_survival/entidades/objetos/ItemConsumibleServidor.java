package io.github.package_game_survival.entidades.objetos;

import io.github.package_game_survival.entidades.seres.jugadores.JugadorServidor;

public abstract class ItemConsumibleServidor extends ItemServidor {

    protected int vidaCurada;
    protected int bonoDanio;
    protected float bonoVelocidad;
    protected int bonoVidaMaxima;
    protected float bonoRango;
    protected float bonoArea;

    protected ItemConsumibleServidor(
        String nombre,
        int vidaCurada,
        int bonoDanio,
        float bonoVelocidad,
        int bonoVidaMaxima,
        float bonoRango,
        float bonoArea
    ) {
        super(nombre);
        this.vidaCurada = vidaCurada;
        this.bonoDanio = bonoDanio;
        this.bonoVelocidad = bonoVelocidad;
        this.bonoVidaMaxima = bonoVidaMaxima;
        this.bonoRango = bonoRango;
        this.bonoArea = bonoArea;
    }

    @Override
    public void usar(JugadorServidor jugador) {

        if (vidaCurada != 0)
            jugador.alterarVida(vidaCurada);

        if (bonoVidaMaxima != 0)
            jugador.aumentarVidaMaxima(bonoVidaMaxima);

        if (bonoDanio != 0)
            jugador.alterarDanio(bonoDanio);

        if (bonoVelocidad != 0)
            jugador.alterarVelocidad(bonoVelocidad);

        if (bonoRango != 0 || bonoArea != 0)
            jugador.mejorarRangoAtaque(bonoRango, bonoArea);
    }
}
