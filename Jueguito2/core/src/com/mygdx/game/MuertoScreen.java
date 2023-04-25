package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MuertoScreen implements Screen {

    final MyGdxGame game;
    Texture fons;
    final Stage stage;
    Music morido;
    final AssetManager assetManager = new AssetManager();
    public MuertoScreen(MyGdxGame game) {
        this.game = game;

        //morido = assetManager.get(AssetDescriptors.deathSound);

        System.out.println(Gdx.graphics.getHeight());
        System.out.println(Gdx.graphics.getWidth());

        // create the camera
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Creem l'escena amb un viewport equivalent a la càmera sencera
        stage = new Stage(new StretchViewport(800, 480, camera));
        loadAssets();
    }
    @Override
    public void show() {
        // Carreguem els assets
        loadAssets();
        morido = assetManager.get(AssetDescriptors.deathSound);
        morido.play();
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.deathSound);
        assetManager.load(AssetDescriptors.youdiedTexture);
        assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        // Esperem a que estiguin carregats els assets
        while (!assetManager.update()) {
            assetManager.getProgress();
        }
        fons = assetManager.get(AssetDescriptors.youdiedTexture);
        morido = assetManager.get(AssetDescriptors.deathSound);

        ScreenUtils.clear(0.05f, 0.05f, 0.05f, 1);

        stage.getBatch().begin();
        //Fuente Como en la practica 1
        /*
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("FtyStrategycideNcv-elGl.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color.set(254f / 255f, 208f / 255f, 0, 1);
        BitmapFont fuente = generator.generateFont(parameter);

        game.batch.begin();
        fuente.draw(game.batch, "Touch to play!", 100, 150);
        game.batch.end();
        */
        //Resto
        stage.getBatch().draw(fons ,0,0);
        game.font.setColor(new Color(255, 0.5f, 0.2f, 1.0f));
        game.font.draw(stage.getBatch(), "Touch to return to the menu", 80, 100);

        stage.getBatch().end();
        stage.act(delta);
        stage.draw();

        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

}
