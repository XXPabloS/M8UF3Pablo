package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Gestiona les gotes que van apareixent, caient i desapareixent. És un grup d'actors que
 * contindrà les gotes com a actors
 */
public class DropletHandler extends Group {
    private long lastDropletTime; // Temps en què s'h generat la darrera gota
    private final long DROPLET_TIME_INTERVAL = 1000000000; // Interval que transcorre entre gotes
    // La imatge de la gota
    private final Texture texture;
    // El so que fa quan es recull una gota amb la galleda
    private final Sound dropSound;
    //private final Sound bottomSound;
    final AssetManager assetManager = new AssetManager();
    boolean finishedBool = false;

    /**
     * El constructor genera la primera gota i guarda l'instant de temps
     */
    public DropletHandler(AssetManager assetManager, boolean terminado) {
        texture = assetManager.get(AssetDescriptors.dropletTexture);
        //bottomSound = assetManager.get(AssetDescriptors.sad_sfx);
        finishedBool = terminado;
        dropSound = assetManager.get(AssetDescriptors.dropSound);
        lastDropletTime = TimeUtils.nanoTime();
        spawnDroplet(finishedBool);

    }

    /**
     * Crea una gota si és necessari
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        spawnDroplet(finishedBool);
    }

    /**
     * Comprova el tems transcorregut des de la darrera gota generada i si supera l'interval estblert
     * se'n genera una altra
     */
    private void spawnDroplet(boolean finishedBool) {
        if ( TimeUtils.nanoTime() - lastDropletTime > DROPLET_TIME_INTERVAL ){
            lastDropletTime = TimeUtils.nanoTime();
            Droplet droplet = new Droplet(texture);
            // Afegim a la gota l'acció de desplaçar-se fins la part inferior de la pantalla
            // en dos segons
            droplet.addAction(Actions.moveTo(droplet.getX(), 0,2));
            //Afegim la gota  la llista d'actors del grup
            addActor(droplet);
        }
    }

    /**
     * Comprova si alguna de les gotes ha caigut a la galleda
     *
     * @param bucket   la galleda
     * @param finished
     * @return cert si alguna gota ha caigut a la galleda
     */
    public boolean collectDroplet(Bucket bucket, Boolean finished) {
        boolean result = false;

        // Obtenim l'iterador que ens servirà per a recórrer els actors
        Iterator<Actor> it = getChildren().iterator();

        while (!finishedBool && it.hasNext()) {
            // Fem el cast d'actor a gota
            Droplet droplet = (Droplet) it.next();
            if ( droplet.inBucket(bucket) ) {
                // Si la gota ha caigut a la galleda eliminem la gota de la llista d'actors
                removeActor(droplet);
                result = true;
                // Reproduim el so
                dropSound.play();
            }
        }
        return result;
    }
    public void dispose() {
        assetManager.dispose();
    }
}
