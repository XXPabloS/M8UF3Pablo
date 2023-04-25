package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class SplashScreen implements Screen {
    MyGdxGame game = null;
    Stage stage = null;
    final AssetManager assetManager = new AssetManager();

    private SpriteBatch batch;
    private Texture texture;
    private OrthographicCamera camera;
    private long startTime;
    private int rendCount;

    public SplashScreen(MyGdxGame game) {
        this.game = game;
        this.stage = stage;

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        loadAssets();
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.mainpicTexture);
        assetManager.load(AssetDescriptors.bucketTexture);
        assetManager.load(AssetDescriptors.dropletTexture);
        assetManager.load(AssetDescriptors.background);
        assetManager.load(AssetDescriptors.dropSound);
        assetManager.load(AssetDescriptors.rainMusic);
        assetManager.load(AssetDescriptors.deathSound);
        assetManager.load(AssetDescriptors.youdiedTexture);
        assetManager.finishLoading();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        rendCount++;
        if (TimeUtils.millis()>(startTime+5000)) game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void show() {
        Gdx.app.log("my Splash Screen", "show called");
        texture = new Texture(Gdx.files.internal("splash.png")); //** texture is now the splash image **//
        startTime = TimeUtils.millis();
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
        Gdx.app.log("my Splash Screen", "hide called");
        Gdx.app.log("my Splash Screen", "rendered " + rendCount + " times.");
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }
}
