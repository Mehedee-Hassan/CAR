package com.race.game.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.race.game.GameEnviornment;
import com.race.game.screen.LoadingScreen;
import com.race.game.util.STATE;

public class RaceGameMain extends Game{

	public AssetManager manager = new AssetManager();
	public FPSLogger fpsLogger;
	public OrthographicCamera camera;
	public FitViewport viewport;
	public TextureAtlas atlas;
	public BitmapFont font;
	public boolean soundEnabled = true;
	public float soundVolume = 0.8f;

	public GameEnviornment gameEnviornment;
	
	
	
	
	
	public RaceGameMain() {

		fpsLogger=new FPSLogger();
		
		gameEnviornment = new GameEnviornment(0f);
		
		
		
		camera = gameEnviornment.orthographicCamera;
		viewport = gameEnviornment.viewport;
		
//		camera = new OrthographicCamera();
//		camera.position.set(Constants.BACK_WIDTH/2,Constants.BACK_HEIGHT/2,0);
//		viewport = new FitViewport(Constants.BACK_WIDTH,Constants.BACK_HEIGHT, camera);
		
		
	}
	
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new LoadingScreen(this));
	}


	@Override
	public void pause() {
		gameEnviornment.setGameState(STATE.PAUSE);
		super.pause();
	}


	@Override
	public void resume() {
		super.resume();
	}


	@Override
	public void dispose() {
		
		
		super.dispose();
	}

	
	

}
