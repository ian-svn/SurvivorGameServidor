package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.package_game_survival.entidades.mapas.EscenarioServidor;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.PathManager;
import io.github.package_game_survival.network.GameController;
import io.github.package_game_survival.network.ServerThread;
import io.github.package_game_survival.standards.LabelStandard;

public class CharacterSelectionScreen implements Screen, GameController {

    private final MyGame game;
    private final Stage stage;

    private Skin skin;
    private ServerThread serverThread;
    private LabelStandard esperandoJugadores;

    public enum TipoClase {
        GUERRERO,
        CAZADOR
    }

    public CharacterSelectionScreen(MyGame game) {
        this.game = game;

        EscenarioServidor escenario = new EscenarioServidor();
        serverThread = new ServerThread(escenario);
        serverThread.start();

        stage = new Stage(
            new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA)
        );
        Gdx.input.setInputProcessor(stage);

        crearUI();
    }

    private void crearUI() {
        Table tabla = new Table();
        tabla.setFillParent(true);

        skin = Assets.get(PathManager.BACKGROUND, Skin.class);
        tabla.setBackground(skin.getDrawable("fondoMenu"));

        LabelStandard titulo =
            new LabelStandard("PRONTO COMENZARÁ TU AVENTURA");

        esperandoJugadores =
            new LabelStandard("Esperando jugadores (0/2)");

        tabla.add(titulo).padBottom(30).colspan(2).row();
        tabla.add(esperandoJugadores).padBottom(30).colspan(2);

        stage.addActor(tabla);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.act(delta);
        stage.draw();

        int conectados = serverThread.getConnectedClients();
        esperandoJugadores.setText(
            "Esperando jugadores (" + conectados + "/2)"
        );

        if (conectados >= 2) {
            System.out.println("Juego listo para comenzar (servidor)");
            // ACÁ NO CAMBIAMOS DE PANTALLA
            // El juego empieza del lado del servidor
        }
    }

    // =====================
    // GameController
    // =====================

    @Override
    public void onConnected() {
        // opcional
    }

    @Override
    public void startGame() {
        // opcional
    }

    @Override
    public void startGame(String mensaje) {
        // opcional
    }

    // =====================
    // Screen
    // =====================

    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
