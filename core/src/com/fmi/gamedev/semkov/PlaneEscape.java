package com.fmi.gamedev.semkov;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fmi.gamedev.semkov.assets.Assets;
import com.fmi.gamedev.semkov.screen.SplashScreen;

public class PlaneEscape extends Game {

	public enum GameState {
		PLAYING,
		MENU
	}

	public static final float WIDTH = 2520;
	public static final float HEIGHT = 4160;

	public static final float WORLD_HEIGHT = 20;
	public static float WORLD_WIDTH;

	public Assets assets;
	public GameState gameState;
	public int highScore;
	private Preferences preferences;
	
	@Override
	public void create () {
		float ratio = (float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		this.WORLD_WIDTH = PlaneEscape.WORLD_HEIGHT / ratio;

		this.assets = new Assets();
		this.setScreen(new SplashScreen(this));
		this.preferences = Gdx.app.getPreferences("highScorePreferences");
		if (preferences.contains("highScore")) {
			this.highScore = preferences.getInteger("highScore");
		} else {
			updateHighScore(0);
			this.highScore = 0;
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		this.assets.dispose();
	}

	public void updateHighScore(int newHighScore){
		preferences.putInteger("highScore", newHighScore);
		preferences.flush();
	}

}
