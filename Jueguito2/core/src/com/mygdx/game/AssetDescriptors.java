package com.mygdx.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class AssetDescriptors {
    //public static  final AssetDescriptor<BitmapFont> fuenteFont = new AssetDescriptor("FtyStrategycideNcv-elGl.ttf", BitmapFont.class);
    public static final AssetDescriptor<Texture> background = new AssetDescriptor("plybtn.png", Texture.class);
    public static final AssetDescriptor<Texture> mainpicTexture = new AssetDescriptor("MainPic.png",Texture.class);
    public static final AssetDescriptor<Texture> bucketTexture = new AssetDescriptor("plane.png",Texture.class);
    public static final AssetDescriptor<Music> deathSound = new AssetDescriptor("death.mp3", Music.class);
    public static final AssetDescriptor<Sound> dropSound = new AssetDescriptor("CrowDie.mp3", Sound.class);
    public static final AssetDescriptor<Texture> dropletTexture = new AssetDescriptor("bird.png", Texture.class);
    public static final AssetDescriptor<Music> rainMusic = new AssetDescriptor("planeSound.mp3", Music.class);
    public static final AssetDescriptor<Texture> youdiedTexture = new AssetDescriptor("youdied.png",Texture.class);






}