package com.race.game.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.race.game.GameController;
import com.race.game.GameRenderer;
import com.race.game.main.RaceGameMain;
import com.race.game.menu.MenuScreen;
import com.race.game.util.Assets;
import com.race.game.util.Constants;
import com.race.game.util.STATE;

public class RaceGameScreen extends BaseScreen implements InputProcessor {

	private static final String TAG = Gdx.class.getName();
	GameController gameController;
	GameRenderer gameRenderer;
	private boolean paused;
//	private GameEnviornment gameEnviornment;
	private static float deltaTime;
	private Vector3 touchPosition = new Vector3();

	Texture img;
	private float X;
	private boolean androidDevise;

	public RaceGameScreen(RaceGameMain game) {

		super(game);
		create();
	}

	// assets;

	public void create() {

		deltaTime = Gdx.graphics.getDeltaTime();

		System.out.println("first" + (float) deltaTime);

//		gameEnviornment = new GameEnviornment(deltaTime);
		gameEnviornment = game.gameEnviornment;
		gameEnviornment.initSecond();
		gameEnviornment.timeDifferenceHandle();
		
		
		gameController = new GameController(gameEnviornment);
		gameRenderer = new GameRenderer(gameController, gameEnviornment);

		Gdx.input.setInputProcessor(this);
		isAndroidDevice();

		// initGameStartTime();
		// init();
	}

	public void initGameStartTime() {
		gameEnviornment.gameStartTime = (System.currentTimeMillis());
	}

	// public void init() {
	// // gameEnviornment.setGameState(STATE.RUNNING);
	//
	// }

	@Override
	public void render(float deltaTime) {

		// if (gameEnviornment.gameState() == STATE.RUNNING)
		{
			gameController.update(deltaTime);

		}

		gameRenderer.render();

		if (gameEnviornment.setBackToMenuScreen()) {
			// game.dispose();
//			gameEnviornment.setGameState(STATE.GAME_OVER);
			game.setScreen(new MenuScreen(game));

		}

		if (gameEnviornment.gameState() == STATE.RUNNING) {
			acceleromterInput();
			arrowInput();
		}

	}

	private void arrowInput() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			gameEnviornment.addYellowCarRectX((-200 * Gdx.graphics
					.getDeltaTime()));

			// System.out.println("left" + Gdx.graphics.getDeltaTime());
			if (gameEnviornment.getYellowCarRectX() <= 150) {
				gameEnviornment.yellowCarRect.x = ((150));
			}

		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {

			gameEnviornment.addYellowCarRectX((200 * Gdx.graphics
					.getDeltaTime()));

			if (gameEnviornment.yellowCarRect.x >= Constants.fence_right_x - 2) {
				gameEnviornment.yellowCarRect.x = (Constants.fence_right_x);

			}
		}

	}

	@Override
	public void hide() {
		gameEnviornment.setGameState(STATE.PAUSE);
		Gdx.app.debug(TAG, "==hide==");

	}
	
	

	@Override
	public void dispose() {
//		Assets.instance.assetManager.dispose();
		
		gameRenderer.dispose();

	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(TAG, "==resize==");
		gameRenderer.resize(width, height);
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		gameEnviornment.setGameState(STATE.PAUSE);

		Gdx.app.debug(TAG, "==resume==");
	}

	@Override
	public void pause() {
		Gdx.app.debug(TAG, "==pause==");
		gameEnviornment.setGameState(STATE.PAUSE);
		paused = true;
	}

	// input processor

	private void isAndroidDevice() {
		if (Gdx.app.getType() == ApplicationType.Android)
			androidDevise = true;

		else
			androidDevise = false;

	}

	// test =======

	// ============

	@Override
	public void show() {
		Gdx.app.debug(TAG, "==show==");
		game.gameEnviornment.initSecond();
		// gameRenderer.resize(width, height);
		super.show();
	}

	public boolean keyDown(int keys) {

		if (gameEnviornment.gameState() == STATE.GAME_OVER) {
			if (keys == Keys.BACKSPACE || (androidDevise && keys == Keys.BACK)) {
				gameEnviornment.setGameState(STATE.BACK_TO_MENU);
			}
		} else if (gameEnviornment.gameState() == STATE.RUNNING) {

			if (keys == Keys.BACKSPACE || (androidDevise && keys == Keys.BACK)) {
				gameEnviornment.setGameState(STATE.PAUSE);
			}

		} else if (gameEnviornment.gameState() == STATE.PAUSE) {

			if (keys == Keys.BACKSPACE || (androidDevise && keys == Keys.BACK)) {
				gameEnviornment.setGameState(STATE.BACK_TO_MENU);
			}
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (gameEnviornment.gameState() == STATE.GAME_OVER) {
			gameEnviornment.setGameState(STATE.RESTART);

		} else if (gameEnviornment.gameState() == STATE.PAUSE) {
			gameEnviornment.setGameState(STATE.RESUME);

		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void acceleromterInput() {

		if (gameEnviornment.gameState() == STATE.RUNNING) {
			if (androidDevise) {
				if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
					if (!gameEnviornment.carCrashRoundFlag) {
						X = Gdx.input.getAccelerometerX();

						{

							if (gameEnviornment.yellowCarRect.x >= Constants.fence_right_x) {
								gameEnviornment.yellowCarRect.x = (Constants.fence_right_x - 10);

							} else if (gameEnviornment.yellowCarRect.x <= Constants.fence_left_rectangle_posx) {
								gameEnviornment.yellowCarRect.x = (Constants.fence_left_rectangle_posx);
							} else if (Math.abs(X) > 0.1) {
								if (X > 0) {

									gameEnviornment.yellowCarRect.x -= (95 * X * Gdx.graphics
											.getDeltaTime());

								} else {

									gameEnviornment.yellowCarRect.x -= (95 * X * Gdx.graphics
											.getDeltaTime());

								}
							}

						}

					}
				} else {
					X = Gdx.input.getX();
					{

						if (gameEnviornment.yellowCarRect.x >= Constants.fence_right_x) {
							gameEnviornment.yellowCarRect.x = (Constants.fence_right_x - 10);

						} else if (gameEnviornment.yellowCarRect.x <= Constants.fence_left_rectangle_posx) {
							gameEnviornment.yellowCarRect.x = (Constants.fence_left_rectangle_posx);
						} else {

							gameEnviornment.yellowCarRect.x = X;

						}

					}

				}
			}
		}
	}
}