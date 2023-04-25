package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * El joc
 */
public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private int rendCount; //** render count **//
	private long startTime; //** time app started **//
	private long endTime; //** time app ended **//
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(); // use libGDX's default Arial font
		//this.setScreen(new MainMenuScreen(this));
		Gdx.app.log("my Splash Game","App created");
		startTime = TimeUtils.millis();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
		rendCount++; //** another render - inc count **//
	}
	
	@Override
	public void dispose () {
		Gdx.app.log("my Splash Game", "App rendered " + rendCount + " times");
		Gdx.app.log("my Splash Game", "App ended");
		endTime = TimeUtils.millis();
		Gdx.app.log("my Splash Game", "App running for " + (endTime-startTime)/1000 + " seconds.");
	}
}
