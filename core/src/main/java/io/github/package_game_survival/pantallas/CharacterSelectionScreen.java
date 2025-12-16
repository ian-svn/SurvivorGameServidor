package io.github.package_game_survival.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CharacterSelectionScreen implements Screen {

    private final MyGame game;
    private final Stage stage;

    public enum TipoClase { GUERRERO, CAZADOR }

    public CharacterSelectionScreen(MyGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGame.ANCHO_PANTALLA, MyGame.ALTO_PANTALLA));
        Gdx.input.setInputProcessor(stage);
        crearUI();
    }

    private void crearUI() {
        Table tabla = new Table();
        tabla.setFillParent(true);

        BitmapFont fuente = new BitmapFont();
        TextButton.TextButtonStyle estilo = new TextButton.TextButtonStyle();
        estilo.font = fuente;
        estilo.fontColor = Color.WHITE;
        estilo.downFontColor = Color.GRAY;

        Label.LabelStyle estiloLabel = new Label.LabelStyle(fuente, Color.GOLD);
        Label titulo = new Label("SELECCIONA TU PERSONAJE", estiloLabel);
        titulo.setFontScale(2f);

        TextButton btnGuerrero = new TextButton("GUERRERO\n(Vida: 1000, Dmg: 20)", estilo);
        btnGuerrero.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarJuego(TipoClase.GUERRERO);
            }
        });

        TextButton btnCazador = new TextButton("CAZADOR\n(Vida: 600, Spd: 160)", estilo);
        btnCazador.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarJuego(TipoClase.CAZADOR);
            }
        });

        tabla.add(titulo).padBottom(50).colspan(2).row();
        tabla.add(btnGuerrero).pad(20).width(300).height(100);
        tabla.add(btnCazador).pad(20).width(300).height(100);

        stage.addActor(tabla);
    }

    private void iniciarJuego(TipoClase tipo) {
        game.setScreen(new GameScreen(game, tipo));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
