package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class MuertoScreen implements Screen {

    final MyGdxGame game;
    Texture MainScreenBackground;
    Rectangle background;
    Music deathsound;

    OrthographicCamera camera;

    public MuertoScreen(MyGdxGame game) {
        this.game = game;

        MainScreenBackground = new Texture(Gdx.files.internal("youdied.png"));

        deathsound = Gdx.audio.newMusic(Gdx.files.internal("death.mp3"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the background
        background = new Rectangle();
        background.x = 0; // center the bucket horizontally
        background.y = 0; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        background.width = 800;
        background.height = 480;
    }

    @Override
    public void show() {
        deathsound.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(MainScreenBackground, background.x, background.y, background.width, background.height);
        game.batch.end();

        //Fuente
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Android y.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color.set(254f / 255f, 208f / 255f, 0, 1);
        BitmapFont fuente = generator.generateFont(parameter);

        game.batch.begin();
        fuente.draw(game.batch, "Touch to return to the menu", 100, 150);
        game.batch.end();



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
        MainScreenBackground.dispose();
        deathsound.dispose();
    }

}