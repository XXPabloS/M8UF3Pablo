package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * La galleda, un actor de tipus image
 */
public class Bucket extends Image {

    /**
     * El constructor carrega la textura i crida al constructor de la superclasse Image
     * Estableix la seva posició centrat a la part inferior de la pantalla
     */
    public Bucket(Texture textura) {
        super(textura);
        setPosition(800 / 2 - 64 / 2, 20);
        setSize(64, 64);
    }


}