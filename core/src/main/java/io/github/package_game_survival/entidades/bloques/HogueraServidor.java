package io.github.package_game_survival.entidades.bloques;

public class HogueraServidor {

    private final float x, y;
    private static final float RADIO_CALOR = 180f;

    public HogueraServidor(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean daCalor(float px, float py) {
        float dx = px - x;
        float dy = py - y;
        return dx * dx + dy * dy <= RADIO_CALOR * RADIO_CALOR;
    }
}
