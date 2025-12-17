package io.github.package_game_survival.habilidades;

import com.badlogic.gdx.math.Vector2;
import io.github.package_game_survival.entidades.seres.SerVivoServidor;
import io.github.package_game_survival.interfaces.IAtaqueServidor;
import io.github.package_game_survival.interfaces.IMundoCombateServidor;

public abstract class AtaqueServidorBase implements IAtaqueServidor {

    protected float cooldownMax;
    protected float casteoMax;
    protected int danioBase;
    protected float rango;

    protected float timerCooldown = 0f;
    protected float timerCasteo = 0f;
    protected boolean casteando = false;

    protected SerVivoServidor atacante;
    protected Vector2 destino = new Vector2();
    protected IMundoCombateServidor mundo;

    public AtaqueServidorBase(
        float cooldown,
        float casteo,
        int danio,
        float rango
    ) {
        this.cooldownMax = cooldown;
        this.casteoMax = casteo;
        this.danioBase = danio;
        this.rango = rango;
    }

    // ======================
    // ATAQUE
    // ======================
    @Override
    public void intentarAtacar(
        SerVivoServidor atacante,
        Vector2 destino,
        IMundoCombateServidor mundo
    ) {
        if (isEnCooldown() || casteando) return;

        this.atacante = atacante;
        this.destino.set(destino);
        this.mundo = mundo;

        casteando = true;
        timerCasteo = casteoMax;

        onInicioCasteo();
    }

    @Override
    public void update(float delta) {

        if (timerCooldown > 0) {
            timerCooldown -= delta;
        }

        if (casteando) {
            timerCasteo -= delta;
            if (timerCasteo <= 0) {
                ejecutarGolpe();
                casteando = false;
                timerCooldown = cooldownMax;
            }
        }
    }

    // ======================
    // ESTADOS
    // ======================
    @Override
    public boolean estaCasteando() {
        return casteando;
    }

    @Override
    public boolean isEnCooldown() {
        return timerCooldown > 0;
    }

    protected abstract void onInicioCasteo();
    protected abstract void ejecutarGolpe();
}
