package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.package_game_survival.managers.Assets;
import io.github.package_game_survival.managers.Audio.AudioManager;
import io.github.package_game_survival.managers.PathManager;
import io.github.package_game_survival.network.GameController;
import io.github.package_game_survival.network.ServerThread;
import io.github.package_game_survival.standards.LabelStandard;
import io.github.package_game_survival.standards.TextButtonStandard;

public class CharacterSelectionScreen implements Screen, GameController {

    private final MyGame game;
    private final Stage stage;

    // Recursos gráficos
    private Texture texGuerrero;
    private Texture texCazador;
    private Skin skin; // Para el fondo
    private ServerThread serverThread;
    private LabelStandard esperandoJugadores;


    @Override
    public void onConnected() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void startGame(String mensaje) {

    }

    public enum TipoClase { GUERRERO, CAZADOR }

    public CharacterSelectionScreen(MyGame game) {
        serverThread = new ServerThread(this);
        serverThread.start();

        this.game = game;
        this.stage = new Stage(new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA));
        Gdx.input.setInputProcessor(stage);
        crearUI();
    }

    private void crearUI() {
        Table tabla = new Table();
        tabla.setFillParent(true);

        this.skin = Assets.get(PathManager.BACKGROUND, Skin.class);
        tabla.setBackground(skin.getDrawable("fondoMenu"));

        LabelStandard titulo = new LabelStandard("PRONTO COMENZARA TU AVENTURA");
        esperandoJugadores = new LabelStandard("Esperando jugadores (0/2)");

        tabla.add(titulo).padBottom(30).colspan(2).row();
        tabla.add(esperandoJugadores).padBottom(30).colspan(2);

        stage.addActor(tabla);
    }

    private void iniciarJuego(TipoClase tipo) {
        game.setScreen(new GameScreen(game, tipo));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
        esperandoJugadores.setText("Esperando jugadores ("+ serverThread.getConnectedClients() +"/2)");
        if(serverThread.getConnectedClients()>=2){
            System.out.println("juegoComenzado");
        }
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
