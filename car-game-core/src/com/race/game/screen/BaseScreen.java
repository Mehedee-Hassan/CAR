package com.race.game.screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.race.game.GameEnviornment;
import com.race.game.main.RaceGameMain;
import com.race.game.util.STATE;

public class BaseScreen extends ScreenAdapter{
	private static final String TAG = BaseScreen.class.getName();
	protected RaceGameMain game;
	private boolean keyHandled;
	public GameEnviornment gameEnviornment;
	
	
	
	public BaseScreen(RaceGameMain game) {
		
		gameEnviornment = game.gameEnviornment;
//		gameEnviornment.loadPref();
		gameEnviornment.initSecond();
		this.game=game;
		keyHandled=false;
		
		Gdx.input.setCatchBackKey(true);
//		Gdx.input.setCatchMenuKey(true);

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}
	protected void handleBackPress() {
		System.out.println("back");
	}

	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		

		game.viewport.update(width,height);
		Gdx.app.debug(TAG, "resize");
		super.resize(width, height);
	}

	@Override
	public void hide() {
		Gdx.app.debug(TAG, "hide");
		super.hide();
	}

	@Override
	public void pause() {
		
		gameEnviornment.setGameState(STATE.PAUSE);
		Gdx.app.debug(TAG, "pause");
		super.pause();
	}

	@Override
	public void resume() {
		Gdx.app.debug(TAG, "resume");

		super.resume();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	
	
	
}
