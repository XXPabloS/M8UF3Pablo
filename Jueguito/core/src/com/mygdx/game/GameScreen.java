package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final MyGdxGame game;
    Texture gameScreenBackground;
    Texture planeImage;
    Texture birdImage;
    Music planeSound;
    Sound birdDieSound;
    OrthographicCamera camera;
    Rectangle plane;
    Rectangle background;
    Array<Rectangle> birddrops;
    long lastDropTime;
    int dropsGathered;

    int dropsEsquivados;

    public GameScreen(final MyGdxGame game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        birdImage = new Texture(Gdx.files.internal("bird.png"));
        planeImage = new Texture(Gdx.files.internal("plane.png"));
        gameScreenBackground = new Texture(Gdx.files.internal("plybtn.png"));

        // load the drop sound effect and the rain background "music"
        birdDieSound = Gdx.audio.newSound(Gdx.files.internal("CrowDie.mp3"));
        planeSound = Gdx.audio.newMusic(Gdx.files.internal("planeSound.mp3"));
        planeSound.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        plane = new Rectangle();
        plane.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        plane.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        plane.width = 64;
        plane.height = 64;

        // create a Rectangle to logically represent the background
        background = new Rectangle();
        background.x = 0; // center the bucket horizontally
        background.y = 0; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        background.width = 800;
        background.height = 480;


        // create the raindrops array and spawn the first raindrop
        birddrops = new Array<Rectangle>();
        spawnRaindrop();

        birddrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        birddrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        //Fuente
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Android y.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont fuente = generator.generateFont(parameter);

        game.batch.begin();
        game.batch.draw(gameScreenBackground, background.x, background.y, background.width, background.height);
        game.batch.end();

        game.batch.begin();
        fuente.draw(game.batch, "Pajaros Chocados: " + dropsGathered, 33, 480);
        game.batch.draw(planeImage, plane.x, plane.y, plane.width, plane.height);
        for (Rectangle raindrop : birddrops) {
            game.batch.draw(birdImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        //Pájaros esquivados:
        game.batch.begin();
        fuente.draw(game.batch, "Pajaros Esquivados: " + dropsEsquivados, 500, 480);
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            plane.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            plane.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            plane.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (plane.x < 0)
            plane.x = 0;
        if (plane.x > 800 - 64)
            plane.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = birddrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0){
                dropsEsquivados++;
                iter.remove();
            }
            if (raindrop.overlaps(plane)) {
                dropsGathered++;
                birdDieSound.play();
                iter.remove();
            }
            if (dropsGathered>=3){
                game.setScreen(new MuertoScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        planeSound.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        birdImage.dispose();
        planeImage.dispose();
        birdDieSound.dispose();
        planeSound.dispose();
        gameScreenBackground.dispose();
    }

}