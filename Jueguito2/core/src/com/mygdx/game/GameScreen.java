package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
/**
 * La pantalla del joc
 */
public class GameScreen implements Screen {
    final MyGdxGame game;
    final Stage stage;
    final AssetManager assetManager = new AssetManager();
    Boolean finished = false;
    Music rainMusic;
    Bucket bucket;
    Texture fons;
    DropletHandler raindrops;
    int dropsGathered = 3;
    int esquivados = 0;

    public GameScreen(MyGdxGame game) {
        this.game = game;

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

        // load the drop sound effect and the rain background "music"

        rainMusic = assetManager.get(AssetDescriptors.rainMusic);
        rainMusic.setLooping(true);


        // Creem el grup d'actors que gestionarà les gotes
        raindrops = new DropletHandler(assetManager, finished);

        // Creem el cubell
        bucket = new Bucket(assetManager.get(AssetDescriptors.bucketTexture));

        // Afegim els actors a l'escena
        stage.addActor(raindrops);
        stage.addActor(bucket);

        //Assignem com a gestor d'entrada una instància de la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.bucketTexture);
        assetManager.load(AssetDescriptors.dropletTexture);
        assetManager.load(AssetDescriptors.background);
        assetManager.load(AssetDescriptors.dropSound);
        assetManager.load(AssetDescriptors.rainMusic);
        assetManager.finishLoading();
    }


    @Override
    public void render(float delta) {
        // Esperem a que estiguin carregats els assets
        while ( !assetManager.update() ) {
            assetManager.getProgress();
        }

        rainMusic = assetManager.get(AssetDescriptors.rainMusic);
        fons = assetManager.get(AssetDescriptors.background);

        ScreenUtils.clear(0, 0, 0.2f, 1);
        esquivados++;
        // Si alguna gota cau en la galleda incrementem el comptador
        if (raindrops.collectDroplet(bucket, finished)) {
            dropsGathered--;
            if (dropsGathered == 0) {
                float x = bucket.getX();
                stage.getActors().removeValue(bucket, true);
                bucket = new Bucket(assetManager.get(AssetDescriptors.bucketTexture));
                stage.addActor(bucket);
                bucket.setX(x);
                raindrops.clear();
                finished = true;
                rainMusic.stop();
                //muerto
                game.setScreen(new MuertoScreen(game));
                dispose();


            }
        }

        // Mostrem un missatge de text amb les gotes que s'han recollit
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
        //System.out.println(game.font.getColor());
        game.font.draw(stage.getBatch(), "Vidas: " + dropsGathered, 20,460);
        game.font.draw(stage.getBatch(), "Puntuación: " + esquivados, 670,460);
        stage.getBatch().end();

        stage.act(delta);
        // A continuació dibuixem l'escena, el mètode draw cridarà als mètodes draw de tots els actors
        stage.draw();

    }
    public void endGame() {
        dispose(); // cleanup any resources allocated during the game
        game.setScreen(new MainMenuScreen(game)); // switch to the home screen
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
