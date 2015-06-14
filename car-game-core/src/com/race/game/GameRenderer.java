package com.race.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.race.game.util.Assets;
import com.race.game.util.Constants;
import com.race.game.util.GamePreferences;
import com.race.game.util.STATE;
import com.sun.corba.se.impl.orbutil.closure.Constant;

public class GameRenderer implements Disposable {

	public GameController gameController;
	public SpriteBatch batch;
	public float carPosX;
	public float carPosY;
	private GameEnviornment gameEnviornment;
	private float streetPosX;
	private float streetPosY;
	private float streetPosY2;
	private float fencePosX;
	private float fencePosY2;
	private float fencePosY11;
	private float fencePosX2;
	private ShapeRenderer shapeRenderer;
	private int i;
	private int k = 0;
	private boolean old;
	private float fencePosY12;
	private float fencePosY21;
	private float fencePosY22;
	private float dramPosx;
	private float dramPosy;
	private boolean bounce;
	public int angleRound;
	Color realColor = new Color();
	private float deltatimeStreet;
	private float progressAmount;
	private float powerUpX;
	private float powerUpY;
	public float finishX;
	public float finishY;
	private float lifeUpX;
	private float lifeUpY;
	private float barY;
	private static final String TAG = GameRenderer.class.getName();

	public GameRenderer() {
	}

	public GameRenderer(GameController gameController,
			GameEnviornment gameEnviornment) {

		batch = new SpriteBatch();
		batch.setProjectionMatrix(gameEnviornment.orthographicCamera.combined);
		
		shapeRenderer = new ShapeRenderer();
		this.gameController = gameController;
		this.gameEnviornment = gameEnviornment;
		init();

	}

	private void init() {
		angleRound = 90;
		lifeUpY = gameEnviornment.lifeUpY;

		coinY = Constants.BACK_HEIGHT2;
		barY = Constants.BACK_HEIGHT2 - 10;
		barX = Constants.fence_left_x + 25;

		powerUpY = Constants.BACK_HEIGHT2;
		finishX = Constants.fence_left_x + Constants.fence_width_left + 5;
		finishY = Constants.BACK_HEIGHT2 - 100;

		streetPosX = gameEnviornment.streetPosx;
		streetPosY = 0;
		streetPosY2 = Constants.BACK_HEIGHT2;

		fencePosX = gameEnviornment.fencePosx;
		fencePosX2 = gameEnviornment.fencePosx2;

		fencePosY11 = gameEnviornment.fencePosY;
		fencePosY12 = gameEnviornment.fencePosY2;
		fencePosY21 = gameEnviornment.fencePosY21;
		fencePosY22 = gameEnviornment.fencePosY22;

		dramPosx = gameEnviornment.dramRect.x;
		dramPosy = gameEnviornment.dramRect.y;

		carPosX = gameEnviornment.yellowCarRect.x;
		carPosY = gameEnviornment.yellowCarRect.y;
		i = 1;

		update();

	}

	public void update() {

		switch (gameEnviornment.gameState()) {

		case START:
			// pauseGame();

			gameStart();
			break;
		case RUNNING:
			Gdx.app.log(TAG, "=run=");
			runningGame();
			break;

		case PAUSE:
			pauseGame();
			break;
		case BACK_TO_MENU:

			backToMainMenuGame();

			break;

		case RESUME:
			Gdx.app.debug(TAG, "==resume==");
			gameEnviornment.gameResume();

			break;

		case RESTART:
			Gdx.app.log(TAG, "=restart=");
			gameOverVarReset();
			gameEnviornment.gameRestart();
			gameController.gameRestart();
			this.gameRestart();

			break;

		case GAME_OVER:
			gameOverVarReset();
			pauseGame();
			removeDramsAndLorries();
			gameEnviornment.handleHighScore();

			break;

		default:
			Gdx.app.log(TAG, "=default=");

			break;
		}

	}

	private void gameStart() {

		if (abs(System.currentTimeMillis() - gameEnviornment.gameStartTime) > 4000) {

			gameEnviornment.resetGameStartTime();
			gameEnviornment.setGameState(STATE.RUNNING);

		} else {
			if (!gameEnviornment.gameStartSoundPlayed) {
				// Assets.instance.gameStartSound.play()
				Assets.gameStartSound.play(0.8f, 0.67f, 0);
				gameEnviornment.gameStartSoundPlayed = true;
			}
		}
		// gameEnviornment.DrawTimer = true;

	}

	private void backToMainMenuGame() {
		gameEnviornment.setSpeed(Constants.ZERO);
		Gdx.app.log(TAG, "=menu=");

		gameEnviornment.soundStop();
		GamePreferences.instance.save();

	}

	private void removeDramsAndLorries() {

		gameEnviornment.drams.clear();
		gameEnviornment.lorries.clear();

	}

	private boolean wait5Sec() {
		if ((abs(System.currentTimeMillis() - gameEnviornment.gameStartTime) > 500)) {
			return true;
		}
		return false;
	}

	private long abs(long l) {
		if (l < 0)
			l = (-1) * l;

		return l;
	}

	private void gameOverVarReset() {
		gameEnviornment.lastLorryEnterTime = System.currentTimeMillis();
		gameEnviornment.lastDramEnterTime = System.currentTimeMillis();
	}

	private void pauseGame() {

		gameEnviornment.setSpeed(Constants.ZERO);
		Gdx.app.log(TAG, "=PAUSE=");
		gameEnviornment.soundStop();
		gameEnviornment.startPausedTime();
		GamePreferences.instance.save();

	}

	public void gameRestart() {
		Gdx.app.log(TAG, "=Restart=");

		init();
	}

	private void runningGame() {

		Gdx.app.log(TAG, "gamerunning");

		deltatimeStreet = Gdx.graphics.getDeltaTime();

		// Gdx.app.log(TAG, "update = "
		// + (System.currentTimeMillis() - gameEnviornment.gameStartTime));

		updateCounters();

		if (gameEnviornment.powerUpEaten) {

			streetPosY -= ((gameEnviornment.maxSpeed_all * Gdx.graphics
					.getDeltaTime()));// gameEnviornment.streetPosY-10;
			streetPosY2 -= ((gameEnviornment.maxSpeed_all * Gdx.graphics
					.getDeltaTime()));// gameEnviornment.streetPosY2-10;

			fencePosX = gameEnviornment.fencePosx;
			fencePosX2 = gameEnviornment.fencePosx2;

			fencePosY11 -= (gameEnviornment.maxSpeed_all)
					* Gdx.graphics.getDeltaTime();// Gdx.graphics.getDeltaTime());
			fencePosY12 -= (gameEnviornment.maxSpeed_all)
					* Gdx.graphics.getDeltaTime();// *
													// Gdx.graphics.getDeltaTime());
			fencePosY21 -= (gameEnviornment.maxSpeed_all)
					* Gdx.graphics.getDeltaTime();// *
													// Gdx.graphics.getDeltaTime());
			fencePosY22 -= (gameEnviornment.maxSpeed_all)
					* Gdx.graphics.getDeltaTime();// *
													// Gdx.graphics.getDeltaTime());

			carPosX = gameEnviornment.yellowCarRect.x;
			carPosY = gameEnviornment.yellowCarRect.y;

			if (gameEnviornment.powerUpShow) {
				// Gdx.app.debug(TAG, "powerUpStart = " + powerUpY);

				if (!gameEnviornment.powerUpEaten)
					powerUpY -= gameEnviornment.powerUpNormalSpeed
							* Gdx.graphics.getDeltaTime();
			}

			if (gameEnviornment.coinSpeedStart) {
				// Gdx.app.debug(TAG, "powerUpStart = " + powerUpY);

				if (!gameEnviornment.coinEaten)
					coinY -= gameEnviornment.coinNormalSpeed
							* Gdx.graphics.getDeltaTime();
			}

			if (gameEnviornment.lifeUpStartShowing
					&& !gameEnviornment.lifeUpEaten) {
				lifeUpY -= gameEnviornment.lifeUpNormalSpeed
						* Gdx.graphics.getDeltaTime();
			}
			if (gameEnviornment.barSpeedStart) {
				// Gdx.app.debug(TAG, "powerUpStart = " + powerUpY);

				if (gameEnviornment.drawBarFlag) {
					barY -= gameEnviornment.coinNormalSpeed
							* Gdx.graphics.getDeltaTime();

				}
			}

			// Gdx.app.debug(TAG, "powerUp");

		} else {

			streetPosY -= gameEnviornment.normalSpeed_all
					* Gdx.graphics.getDeltaTime(); // gameEnviornment.streetPosY-10;
			streetPosY2 -= gameEnviornment.normalSpeed_all
					* Gdx.graphics.getDeltaTime();// gameEnviornment.streetPosY2-10;

			fencePosX = gameEnviornment.fencePosx;
			fencePosX2 = gameEnviornment.fencePosx2;

			fencePosY11 -= gameEnviornment.normalSpeed_all
					* Gdx.graphics.getDeltaTime();// Gdx.graphics.getDeltaTime();
			fencePosY12 -= gameEnviornment.normalSpeed_all
					* Gdx.graphics.getDeltaTime();// Gdx.graphics.getDeltaTime();
			fencePosY21 -= gameEnviornment.normalSpeed_all
					* Gdx.graphics.getDeltaTime();// Gdx.graphics.getDeltaTime();
			fencePosY22 -= gameEnviornment.normalSpeed_all
					* Gdx.graphics.getDeltaTime();// Gdx.graphics.getDeltaTime();

			if (gameEnviornment.powerUpShow) {
				// Gdx.app.debug(TAG, "powerUpStart = " + powerUpY);

				if (!gameEnviornment.powerUpEaten)
					powerUpY -= gameEnviornment.powerUpNormalSpeed
							* Gdx.graphics.getDeltaTime();
			}

			if (gameEnviornment.coinSpeedStart) {
				// Gdx.app.debug(TAG, "powerUpStart = " + powerUpY);

				if (!gameEnviornment.coinEaten)
					coinY -= gameEnviornment.coinNormalSpeed
							* Gdx.graphics.getDeltaTime();
			}
			if (gameEnviornment.barSpeedStart) {
				// Gdx.app.debug(TAG, "powerUpStart = " + powerUpY);

				if (gameEnviornment.drawBarFlag)
					barY -= gameEnviornment.coinNormalSpeed
							* Gdx.graphics.getDeltaTime();
			}

			if (gameEnviornment.lifeUpStartShowing
					&& !gameEnviornment.lifeUpEaten) {
				lifeUpY -= gameEnviornment.lifeUpNormalSpeed
						* Gdx.graphics.getDeltaTime();
			}

			carPosX = gameEnviornment.yellowCarRect.x;
			carPosY = gameEnviornment.yellowCarRect.y;
		}
		// Gdx.app.log(TAG, "update ends");

		gameEnviornment.saveDeltaTime(Gdx.graphics.getDeltaTime());
		gameController.update(Gdx.graphics.getDeltaTime());

		// handleScore();
	}

	private void handleScore() {

		gameEnviornment.addScore(gameEnviornment.getGameScoreUnit());

	}

	private void updateCounters() {

		if (gameEnviornment.powerBoost) {
			gameEnviornment.powrUpTimeCounter -= Constants.powerUpCounterDecBy
					* Gdx.graphics.getDeltaTime();
			gameController.setPowerBoostOff(gameEnviornment.powrUpTimeCounter);
		}

	}

	public void render() {
		// Gdx.app.debug(TAG, "" + Gdx.graphics.getDeltaTime());
		// clear screen

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		

		update();

		batch.begin();

		// if(gameEnviornment.gameState() == STATE.START)

		realColor = batch.getColor();

		fenceDrawHandler();

		drawStreet1(batch, 0, streetPosY);
		drawStreet2(batch, 0, streetPosY2);

		drawFence1(batch, fencePosX, fencePosX2, fencePosY11, fencePosY12);
		drawFence2(batch, fencePosX, fencePosX2, fencePosY21, fencePosY22);

		powerUpHandler();

		// boolean debug = gameController.isLifeUpToShow();

		if (gameEnviornment.ifLifeUpDraw()) {
			// lifeUpStartShowing
			Gdx.app.debug(TAG, "under2=" + lifeUpY);
			drawLifeUp();
		}

		carDrawHandler();

		batch.setColor(realColor);

		if (gameEnviornment.getIfSideCollision()) {
			drawSideCollisionBlast(batch, carPosX, carPosY,
					gameEnviornment.isLeftSide());
		} else {

			// drawSideCollisionBlast(batch,carPosX,carPosY,gameEnviornment.isLeftSide());
		}

		
		
		for (Rectangle rectDram : gameEnviornment.drams) {
			Gdx.app.log(TAG, "dram y==" + rectDram.y);

			drawDram(batch, rectDram.x, rectDram.y);

		}
		for (Rectangle rectLorry : gameEnviornment.lorries) {

			Gdx.app.log(TAG, "lorry y==" + rectLorry.y);
			// batch.setColor(Color.GRAY);
			drawLorry(batch, rectLorry.x - 20, rectLorry.y);
			// batch.setColor(realColor);

		}

		//
		if (gameController.isGameOver()) {
			drawGameOverScreen();
			gameController.StopAll();

		}
		//
		drawScore();

		if (gameEnviornment.playGameOverExplosionFlag) {
			drawExplosionAnimation();
		}

		if (gameEnviornment.isTimeFinis()) {
			// drawFinish();
		}

		if (gameEnviornment.gameState() == STATE.PAUSE) {
			drawPauseScreen();
		}

		if (gameEnviornment.gameState() == STATE.START) {
			// drawStartTimer(batch,true);
			if (abs(System.currentTimeMillis() - gameEnviornment.gameStartTime) < 3000){
				drawStartTimer(batch, true);
//				pauseGame();
			}
			else
				{
					drawStartTimer(batch, false);
//					gameEnviornment.gameResume();
				}
		}

		drawCoinAnimation();
		drawBarAnimation();

		batch.end();

		debugRectangles(Constants.debbugRectangleCollision);

		if (gameEnviornment.gameState() == STATE.RUNNING) {
			
			Assets.instance.carRunningMusic.play();

			if (System.currentTimeMillis() - gameEnviornment.lastDramEnterTime > Constants.dram_interval_time) {
				gameEnviornment.addToDramArray();
			}

			if (System.currentTimeMillis()
					- gameEnviornment.lastPowerUpEnterTime > Constants.power_up_interval_time) {
				powerUpY = Constants.BACK_HEIGHT2;
				gameEnviornment.startPowerUp();
			}

			if (System.currentTimeMillis()
					- (gameEnviornment.lastLifeUpEnterTime) > Constants.life_up_interval_time) {

				lifeUpY = Constants.BACK_HEIGHT2;
				gameEnviornment.startLifeUp();
			}

			if (System.currentTimeMillis()
					- (gameEnviornment.lastLorryEnterTime) > Constants.lorry_interval_time) {
				gameEnviornment.addToLorryArray();
			}

			if (System.currentTimeMillis()
					- (gameEnviornment.coinLastEnterTime) > Constants.coin_interval_time) {
				startCoin();
				startCoinFlag();
			}

			if (System.currentTimeMillis() - (gameEnviornment.barLastEnterTime) > Constants.bar_interval_time) {
				startBar();
				startBarFlag();
			}

		}

	}

	private void startBarFlag() {
		gameEnviornment.startBarFlag();
	}

	private void startBar() {
		barY = Constants.BACK_HEIGHT2 - 10;
		gameEnviornment.startBar();
	}

	private void startCoinFlag() {
		gameEnviornment.startCoinFlag();
	}

	private void startCoin() {
		coinY = Constants.BACK_HEIGHT2 + 10;
		gameEnviornment.startCoin();

	}

	private void drawStartTimer(SpriteBatch batch, boolean printGoFlag) {

		drawHighScore();

		if (printGoFlag) {
			if ((System.currentTimeMillis() - gameEnviornment.lastTime) > 1000) {
				gameEnviornment.countDown--;
				gameEnviornment.lastTime = System.currentTimeMillis();

			}
			Assets.instance.simpleFontLargeLarge.draw(batch, ""
					+ gameEnviornment.countDown,
					Constants.back_width_half - 40, Constants.back_height_half);

		} else {
			Assets.instance.simpleFontLargeLarge.draw(batch, "GO",
					Constants.back_width_half - 60, Constants.back_height_half);

		}

	}

	private void drawHighScore() {
		Assets.instance.bitMapFont2.draw(batch, "Highest Score",
				Constants.back_width_half - 160,
				Constants.back_height_half + 200);

		Assets.instance.bitMapFont2.draw(batch, ""
				+ GamePreferences.instance.highstScore,
				Constants.back_width_half - 30,
				Constants.back_height_half + 150);

	}

	private void drawPauseScreen() {

		if (gameEnviornment.printTab()) {
//			Assets.instance.simpleFontLarge.draw(batch, "PAUSED",
//					Constants.back_width_half - 120,
//					Constants.back_height_half + 30);

			
			batch.draw(Assets.instance.pausedText, Constants.back_width_half - 100,
					Constants.back_height_half + 60, 0, 0,190, 51, 1, 1, 0);

		}

//		batch.setColor(Color.BLUE);

		Assets.instance.simpleFontSmall.draw(batch, " RESUME ",
				Constants.back_width_half - 110,
				Constants.back_height_half - 80);

		Assets.instance.simpleFontSmall.draw(batch, "TOUCH TO",
				Constants.back_width_half - 110,
				Constants.back_height_half - 20);

//		batch.setColor(realColor);

	}

	private void fenceDrawHandler3() {
		if (fencePosY11 + Constants.new_fence_height <= 0) {
			fencePosY11 = Constants.new_fence_height;
			fencePosY12 = Constants.new_fence_height + Constants.new_fence_height;

		} else if (fencePosY21 + Constants.new_fence_height <= 0) {

			fencePosY21 = Constants.new_fence_height;
			fencePosY22 = Constants.new_fence_height + Constants.new_fence_height;

		}

		if (streetPosY + Constants.BACK_HEIGHT2 <= 0) {

			streetPosY = streetPosY2 + Constants.BACK_HEIGHT2;// Constants.BACK_HEIGHT2
																// - (streetPosY
																// +
																// Constants.BACK_HEIGHT2);

		} else if (streetPosY2 + Constants.BACK_HEIGHT2 <= 0) {
			streetPosY2 = streetPosY + Constants.BACK_HEIGHT2;// Constants.BACK_HEIGHT2-
																// (streetPosY2
																// +
																// Constants.BACK_HEIGHT2);
		}
	}

	
	private void fenceDrawHandler() {
		if (fencePosY11 + Constants.BACK_HEIGHT <= 0) {
			fencePosY11 = fencePosY21+Constants.BACK_HEIGHT;
			fencePosY12 = fencePosY22 + Constants.BACK_HEIGHT;

		} else if (fencePosY21 + Constants.BACK_HEIGHT <= 0) {

			fencePosY21 = fencePosY11+Constants.BACK_HEIGHT;
			fencePosY22 = fencePosY12 + Constants.BACK_HEIGHT;

		}

		if (streetPosY + Constants.BACK_HEIGHT2 <= 0) {

			streetPosY = streetPosY2 + Constants.BACK_HEIGHT2;// Constants.BACK_HEIGHT2
																// - (streetPosY
																// +
																// Constants.BACK_HEIGHT2);

		} else if (streetPosY2 + Constants.BACK_HEIGHT2 <= 0) {
			streetPosY2 = streetPosY + Constants.BACK_HEIGHT2;// Constants.BACK_HEIGHT2-
																// (streetPosY2
																// +
																// Constants.BACK_HEIGHT2);
		}
	}

	private void carDrawHandler() {
		if (gameEnviornment.dynamicDramCollid) {

			batch.setColor(Color.RED);
		}

		if (((System.currentTimeMillis() - gameEnviornment.lastSideCollision > 10) && gameEnviornment.sideCollisionColorChange)) {


			gameEnviornment.lastSideCollision = System.currentTimeMillis();

			gameEnviornment.sideCollisionColorChange = false;

			batch.setColor(Color.ORANGE);
		}

		if (gameEnviornment.carCrashRoundFlag) {

			// batch.setColor(Color.GRAY);
		}

		if (gameEnviornment.playGameOverExplosionFlag)
			batch.setColor(Color.GRAY);

		drawCar(batch, carPosX, carPosY);

	}

	private void powerUpHandler() {
		// if (gameController.getProgressAmount() < 60
		// && !GameEnviornment.powerBoost)

		if (gameEnviornment.ifStartPowerUp()) {
			drawPowerUp();
		}

	}

	// temp
	public float elapsedTime2 = 0;

	public float elapsedTime3 = 0;

	private void drawLifeUp() {

		// if (!gameEnviornment.lifeUpEaten && lifeUpY > -10)
		{

			// temporary

			if (gameEnviornment.lifeUpShownFixX) {
				lifeUpX = MathUtils.random(Constants.life_up_pos_x_min,
						Constants.life_up_pos_x_max);
				gameEnviornment.lifeUpShownFixX = false;
			}

			gameEnviornment.lifeUpRect.x = lifeUpX + 40;
			gameEnviornment.lifeUpRect.y = lifeUpY;

			batch.draw(Assets.instance.life, lifeUpX - 10, lifeUpY, 0, 0,
					Constants.life_up_widht, Constants.life_up_height, 1, 1, 0);

		}

	}

	private void drawFinish() {


	}

	// temp
	private float elapsedTime = 0;
	private float elapsedTime1 = 0;
	private float coinX;
	private float coinY;
	private long timeToStartAnimation;
	private float barX;

	private void drawExplosionAnimation() {

		elapsedTime += Gdx.graphics.getDeltaTime();

		Assets.instance.animation.setPlayMode(PlayMode.NORMAL);
		Assets.instance.animation.setFrameDuration(0.06f);
		batch.draw(Assets.instance.animation.getKeyFrame(elapsedTime),
				gameEnviornment.yellowCarRect.x - 215,
				gameEnviornment.yellowCarRect.y - 140);

		// batch.setColor(Color.LIGHT_GRAY);
	}

	// /barAnimation

	private void drawBarAnimation() {

		if (gameEnviornment.drawBarFlag) {

			gameEnviornment.barRect.y = barY + 40;// bar is above 20 from
													// street
			gameEnviornment.barRect.x = barX;

			if (gameEnviornment.openGateDraw()) {
				if (barY < Constants.BACK_HEIGHT2 - 200) {

					batch.draw(Assets.instance.bar4,
							Constants.fence_left_x + 25, barY, 0, 0, 345,
							Assets.instance.bar4.getRotatedPackedHeight(), 1,
							1, 0);

				} else {
					batch.draw(Assets.instance.bar0,
							Constants.fence_left_x + 25, barY, 0, 0, 345,
							Assets.instance.bar0.getRotatedPackedHeight(), 1,
							1, 0);

				}

				if (barY < 0) {
				}

			} else {

				if (!gameEnviornment.barEaten) {
					batch.draw(Assets.instance.bar0,
							Constants.fence_left_x + 25, barY, 0, 0, 345,
							Assets.instance.bar0.getRotatedPackedHeight(), 1,
							1, 0);

				} else {
					batch.draw(Assets.instance.bar5,
							Constants.fence_left_x + 25, barY, 0, 0, 345,
							Assets.instance.bar5.getRotatedPackedHeight(), 1,
							1, 0);

				}

				if (barY < 0) {
				}
			}

		}
	}

	float elapsedTime5 = 0;

	private void drawCoinAnimation() {

		if (gameEnviornment.coinSpeedStart && !gameEnviornment.coinEaten) {

			if (!gameEnviornment.showedCoinUp) {
				coinX = MathUtils.random(Constants.coin_x_min,
						Constants.coin_x_max);
				gameEnviornment.setCoinUpX(coinX);

				gameEnviornment.showedCoinUp = true;
			}

			gameEnviornment.coinRect.y = coinY;
			gameEnviornment.coinRect.x = coinX + 32;

			elapsedTime += Gdx.graphics.getDeltaTime();

			Assets.instance.coinAnimation.setPlayMode(PlayMode.LOOP);

		
			elapsedTime5 += 0.03;
			if(elapsedTime5 > 5)
				elapsedTime5 = 0;
			
			batch.draw(Assets.instance.coinAnimation.getKeyFrame(elapsedTime5),
					coinX - 30, coinY, 0, 0, Constants.coin_width,
					Constants.coin_height, 1, 1, 0);

			if (coinY < -10) {
				// startCoin();
				// gameEnviornment.coinLastEnterTime =
				// System.currentTimeMillis();
			}

			// elapsedTime += Gdx.graphics.getDeltaTime();
			//
			// Assets.instance.coinAnimation.setPlayMode(PlayMode.LOOP);
			// Assets.instance.coinAnimation.setFrameDuration(0.2f);
			//
			// batch.draw(Assets.instance.coinAnimation.getKeyFrame(elapsedTime),
			// Constants.fence_left_x+ 30,
			// 440);

		}
	}

	private void drawPowerUp() {

		if (!gameEnviornment.showedPowerUp) {
			powerUpX = MathUtils.random(Constants.power_pos_x_min,
					Constants.power_pos_x_max);
			gameEnviornment.setPowerUpX(powerUpX);
			gameEnviornment.showedPowerUp = true;
		}

		if (gameController.checkIfLorryOnTheWay(powerUpX)) {
			if (gameEnviornment.lorries.size > 0) {

				// Gdx.app.debug(TAG,
				// " lorry x = " + gameEnviornment.lorries.first().x
				// + " powerupx = " + powerUpX);

				powerUpX = gameEnviornment.lorries.first().x - 90;

				if (Constants.fence_left_x + Constants.fence_width_left >= powerUpX)
					powerUpX = gameEnviornment.lorries.first().x - 10
							- gameEnviornment.lorries.first().width;

				// Gdx.app.debug(TAG,
				// " lorry x = " + gameEnviornment.lorries.first().x
				// + " powerupx = " + powerUpX);

			}
		}

		gameEnviornment.powerUpRect.y = powerUpY;
		gameEnviornment.powerUpRect.x = powerUpX + 30;

		elapsedTime2 += Gdx.graphics.getDeltaTime();

		Assets.instance.powerUpAnimation.setPlayMode(PlayMode.LOOP);
		Assets.instance.powerUpAnimation.setFrameDuration(0.06f);
		batch.draw(Assets.instance.powerUpAnimation.getKeyFrame(elapsedTime2),
				powerUpX - 35, powerUpY-9, 0, 0,
				Constants.power_up_draw_width + 10,
				Constants.power_up_draw_height + 10, 1, 1, 0);

		//
		// batch.draw(Assets.instance.powerUp, powerUpX + 5, powerUpY, 0, 0,
		// Constants.power_up_draw_width, Constants.power_up_draw_height,
		// 1, 1, 0);
	}

	private void drawGameOverScreen() {

		// Assets.instance.bitmapFont_gameOver.setColor(Color.ORANGE);
//		Assets.instance.bitmapFont_gameOver.setScale(1.5f);
//		Assets.instance.bitmapFont_gameOver.draw(batch, "GAME OVER", 90, 400);
//		
		
		
		batch.draw(Assets.instance.gameOverText, Constants.back_width_half - 200,
				Constants.back_height_half + 3, 0, 0,400, 70, 1, 1, 0);
		

		drawHighScore();
		batch.draw(Assets.instance.resumeIcon,218, 248, 0 ,0, 70,70, 1, 1, 0);
	}

	private void drawScore() {
		// draw score\

		// batch.draw(Assets.instance.dram, Constants.fence_left_x + 20,
		// Constants.BACK_HEIGHT - 70, 0, 0, Constants.dram_width,
		// Constants.dram_height, .70f, .70f, 90, true);

		Assets.instance.bitmapFont.draw(batch, "Score: ",
				Constants.fence_left_x - 65, Constants.BACK_HEIGHT - 29);

		Assets.instance.bitmapFont.draw(batch, "" + gameEnviornment.getScore(),
				Constants.fence_left_x + 30, Constants.BACK_HEIGHT - 29);

		batch.draw(Assets.instance.coinAnimation.getKeyFrame(0),
				Constants.fence_right_x + 28, Constants.BACK_HEIGHT - 90, 0, 0,
				Constants.coin_width+10, Constants.coin_height , 1f, 1f, 90,
				true);

		Assets.instance.simpleFontSmall.draw(batch,
				"" + gameEnviornment.getCoinCounter(),
				Constants.fence_right_x - 18, Constants.BACK_HEIGHT - 70);

//		damageProgressBarHandler(Color.BLUE, Color.ORANGE, Color.RED);
		
		damageProgressBarHandler(new Color(173/255f,1f,47/255f, 1f), Color.YELLOW, Color.RED);
		
		
		if (gameEnviornment.powerBoost)
			powerUpProgressBarHandler(Color.YELLOW, Color.YELLOW, Color.YELLOW,
					Constants.fence_left_x + 39, Constants.BACK_HEIGHT - 109);

	}

	private void powerUpProgressBarHandler(Color firstColor, Color secondColor,
			Color thirdColor, float X, float Y) {

		progressAmount = gameController.getPowerUpAmount();

		if (progressAmount <= 50 && progressAmount > 20) {
			batch.setColor(firstColor);
		} else if (progressAmount <= 20) {
			batch.setColor(secondColor);
			// gameEnviornment.powerUpSpeedStart = true;

		} else
			batch.setColor(thirdColor);

		if (progressAmount < 0) {
			gameEnviornment.powerUpShow = false;

		}
		if (progressAmount > 15) {
			drawProgressBar(X, Y, progressAmount);

			batch.draw(Assets.instance.progressIconPowerUp, X + 4, Y - 2 - 5,
					0, 0, 20, 20, 1, 1, 0);

			batch.draw(Assets.instance.progressBarOutline, X + 1f, Y - 5, 0, 0,
					Constants.progress_bar_outline_width+4,
					Constants.progress_bar_outline_height+2, 1f, 1f, 90, true);
		}

		batch.setColor(realColor);

	}

	private void damageProgressBarHandler(Color firstColor, Color secondColor,
			Color thirdColor) {

		batch.draw(Assets.instance.progressIconDamage,
				Constants.fence_left_x + 44, Constants.BACK_HEIGHT - 92 - 5, 0,
				0, 40, 30, 1, 1, 0);

		batch.draw(Assets.instance.progressBarOutline,
				Constants.fence_left_x + 40, Constants.BACK_HEIGHT - 90 - 5, 0,
				0, Constants.progress_bar_outline_width+4,
				Constants.progress_bar_outline_height+2, 1f, 1f, 90, true);

		progressAmount = gameController.getProgressAmount();

		if (progressAmount <= 50 && progressAmount > 20) {
			batch.setColor(secondColor);
		} else if (progressAmount <= 20) {

			if (gameEnviornment.gameState() == STATE.RUNNING) {
				// Assets.instance.criticalDamageLevelSound.play(0.3f , 1.1f ,
				// 0f);
				// gameEnviornment.playCriticalDamageSound();

			}
			batch.setColor(thirdColor);

		}

		else {
			batch.setColor(firstColor);
		}

		if (progressAmount <= 40) {
//			gameEnviornment.powerUpShow = true;
		}

		if (progressAmount > 20) {
			Assets.instance.criticalDamageLevelSound.stop();
			gameEnviornment.criticalLevelSoundPlayed = false;
		} else {

			if (!gameEnviornment.criticalLevelSoundPlayed) {
				Assets.instance.criticalDamageLevelSound.loop(0.6f,1f, 0f);
				gameEnviornment.criticalLevelSoundPlayed = true;
			}
		}
		drawProgressBar(Constants.fence_left_x + 39,
				Constants.BACK_HEIGHT - 89, progressAmount);
		batch.setColor(realColor);

	}

	private void drawProgressBar(float X, float Y, float progressAmount) {

		
		if(progressAmount >=0 )
		batch.draw(Assets.instance.progressBarBack, X, Y - 5, 0, 0,
				Constants.progress_bar_inside_width+3, progressAmount, 1f, 1f,
				90, true);

	}

	private void drawFence2(SpriteBatch batch, float fencePosX,
			float fencePosX2, float fencePosY21, float fencePosY22) {
		batch.draw(Assets.instance.fence2, fencePosX, fencePosY21, 0, 0,
				Constants.fence_width_left, Constants.fence_height, 1, 1, 0);

		batch.draw(Assets.instance.fence2, fencePosX2, fencePosY22, 0, 0,
				Constants.fence_width_right, Constants.fence_height, 1, 1, 180);

	}

	private void drawFence1(SpriteBatch batch, float fencePosX,
			float fencePosX2, float fencePosY11, float fencePosY12) {
		batch.draw(Assets.instance.fence, fencePosX, fencePosY11, 0, 0,
				Constants.fence_width_left, Constants.fence_height, 1, 1, 0);

		batch.draw(Assets.instance.fence, fencePosX2, fencePosY12, 0, 0,
				Constants.fence_width_right, Constants.fence_height, 1, 1, 180);

	}

	private void drawStreet2(SpriteBatch batch, float posx, float posy) {
		batch.draw(Assets.instance.street1, posx, posy, 0, 0,
				Constants.BACK_WIDTH, Constants.BACK_HEIGHT2 + 8, 1, 1, 0);

	}

	private void drawStreet1(SpriteBatch batch, float posx, float posy) {

		batch.draw(Assets.instance.street2, posx, posy, 0, 0,
				Constants.BACK_WIDTH, Constants.BACK_HEIGHT2 + 4, 1, 1, 0);

	}

	private void drawSideCollisionBlast(SpriteBatch batch, float carPosXL,
			float carPosYL, boolean isLeft) {

		Assets.instance.sideCollision.update(Gdx.graphics.getDeltaTime());

		if (isLeft) {

			Assets.instance.sideCollision
					.getEmitters()
					.first()
					.setPosition(
							carPosXL - gameEnviornment.yellowCarRect.width + 5,
							carPosYL + gameEnviornment.yellowCarRect.height - 5);

			Assets.instance.sideCollision.start();

			tryBounce(true);

		} else {
			Assets.instance.sideCollision
					.getEmitters()
					.first()
					.setPosition(carPosXL - 5,
							carPosYL + gameEnviornment.yellowCarRect.height - 5);

			Assets.instance.sideCollision.start();
			tryBounce(false);
			// Assets.instance.sideCollision.draw(batch);
		}

		if (old != isLeft) {

			Assets.instance.sideCollision.reset();
			;
			// Assets.instance.sideCollision.start();
		}
		old = isLeft;
		Assets.instance.sideCollision.draw(batch);

	}

	int w = 0;
	private boolean dramCollWithCar;
	private int newAngle = 90;
	private float collidAway = 0;

	private void tryBounce(boolean isLeft) {
		w++;
		if (w == 15) {

			if (isLeft)
				gameEnviornment.yellowCarRect.x += 2;
			else
				gameEnviornment.yellowCarRect.x -= 4;

			w = 0;
		}

	}

	private void debugRectangles(boolean debug) {
		debug = false;
//		 debug = true;
		if (debug) {
			shapeRenderer.setAutoShapeType(true);
			shapeRenderer.begin();
			shapeRenderer.setColor(Color.RED);

			shapeRenderer.rect(gameEnviornment.yellowCarRect.x,
					gameEnviornment.yellowCarRect.y,
					gameEnviornment.yellowCarRect.width,
					gameEnviornment.yellowCarRect.height);

			shapeRenderer.rect(gameEnviornment.fenceRectLeft.x,
					gameEnviornment.fenceRectLeft.y,
					gameEnviornment.fenceRectLeft.width,
					gameEnviornment.fenceRectLeft.height);

			shapeRenderer.rect(gameEnviornment.dramRect.x,
					gameEnviornment.dramRect.y, gameEnviornment.dramRect.width,
					gameEnviornment.dramRect.height);

			// shapeRenderer.rect(gameEnviornment.lorryRect.x,
			// gameEnviornment.lorryRect.y,
			// gameEnviornment.lorryRect.width,
			// gameEnviornment.lorryRect.height);
			shapeRenderer.rect(gameEnviornment.fenceRectRight.x,
					gameEnviornment.fenceRectRight.y,
					gameEnviornment.fenceRectRight.width,
					gameEnviornment.fenceRectRight.height);

			shapeRenderer.rect(gameEnviornment.powerUpRect.x,
					gameEnviornment.powerUpRect.y,
					gameEnviornment.powerUpRect.width,
					gameEnviornment.powerUpRect.height);

			shapeRenderer.rect(gameEnviornment.lifeUpRect.x,
					gameEnviornment.lifeUpRect.y,
					gameEnviornment.lifeUpRect.width,
					gameEnviornment.lifeUpRect.height);

			shapeRenderer.rect(gameEnviornment.coinRect.x,
					gameEnviornment.coinRect.y, gameEnviornment.coinRect.width,
					gameEnviornment.coinRect.height);

			shapeRenderer.rect(gameEnviornment.barRect.x,
					gameEnviornment.barRect.y, gameEnviornment.barRect.width,
					gameEnviornment.barRect.height);

			shapeRenderer.end();
		}

		return;

	}

	private void drawFence(SpriteBatch batch, float fencePosX,
			float fencePosX2, float fencePosY, float fencePosY2) {
		batch.draw(Assets.instance.fence, fencePosX, fencePosY, 0, 0,
				Constants.fence_width_left, Constants.fence_height, 1, 1, 0);

		batch.draw(Assets.instance.fence, fencePosX2, fencePosY2, 0, 0,
				Constants.fence_width_right, Constants.fence_height, 1, 1, 180);
	}

	private void drawDram(SpriteBatch batch, float posx, float posy) {

		// batch.setColor(0,1,0,1);
		// Gdx.app.debug(TAG, "dram = " + posx + " " + posy);

		batch.draw(Assets.instance.dram, posx, posy, 0, 0,
				Constants.dram_width, Constants.dram_height, 1, 1, 90, true);

		// if(dramCollWithCar)
		{

		}
	}

	private void drawLorry(SpriteBatch batch, float posx, float posy) {

		batch.draw(Assets.instance.lorry, posx + 30, posy, 0, 0,
				Constants.lorry_width, Constants.lorry_height + 10, 1, 1, 90,
				true);

	}

	private void drawCar(SpriteBatch batch, float posx, float posy) {

		if (!gameEnviornment.carCrashRoundFlag)
			if (gameEnviornment.yellowCarRect.x > gameEnviornment.fencePosx2 + 5) {
				gameEnviornment.yellowCarRect.x -= 5;
			} else if (gameEnviornment.yellowCarRect.x < gameEnviornment.fencePosx - 5) {
				gameEnviornment.yellowCarRect.x -= 5;
			}

		// batch.setColor(0,1,0,1);
		if (gameEnviornment.carCrashRoundFlag) {
			if (gameEnviornment.carCrashRoundAntiClockFlag) {
				angleRound += 5;
				newAngle = 110;
				collidAway -= 0.80;
			} else {
				newAngle = 80;// MathUtils.random(80, 60);;
				angleRound -= 5;
				collidAway += 0.80;
			}

			batch.draw(Assets.instance.yellowCar, posx + collidAway, posy, 0,
					0, Constants.CAR_WIDTH, Constants.CAR_HEIGHT, 1, 1,
					angleRound, true);
			if (gameEnviornment.dynamicDramCollid) {
				// batch.setColor(Color.WHITE);
			}

			if (angleRound > 40 || angleRound < -40)
				gameEnviornment.carCrashRoundFlag = false;

		} else {

			batch.draw(Assets.instance.yellowCar, posx, posy, 0, 0,
					Constants.CAR_WIDTH, Constants.CAR_HEIGHT, 1, 1, 90, true);

		}

		if (gameEnviornment.powerUpEaten) {
			Assets.instance.carSmokeChange.update(Gdx.graphics.getDeltaTime());
			Assets.instance.carSmokeChange.getEmitters().first()
					.setPosition(carPosX - 20, carPosY);

			Assets.instance.carSmokeChange.start();
			Assets.instance.carSmokeChange.draw(batch);
		} else {
			Assets.instance.carSmoke.update(Gdx.graphics.getDeltaTime());
			Assets.instance.carSmoke.getEmitters().first()
					.setPosition(carPosX - 20, carPosY);

			Assets.instance.carSmoke.start();
			Assets.instance.carSmoke.draw(batch);

		}

		if (gameEnviornment.dynamicDramCollid) {
			Assets.instance.dramExplo.update(Gdx.graphics.getDeltaTime());
			Assets.instance.dramExplo.getEmitters().first()
					.setPosition(carPosX - 20, carPosY + 90);

			Assets.instance.dramExplo.start();
			Assets.instance.dramExplo.draw(batch);
			if (k == 5) {
				batch.setColor(Color.alpha(0));
				gameEnviornment.dynamicDramCollid = false;
				Assets.instance.dramExplo.reset();

				k = 0;
			}
			k++;
		}

		// batch.setColor(1,1,1,1);

		System.out.println(gameEnviornment.yellowCarRect.x + " "
				+ gameEnviornment.yellowCarRect.width);
		System.out.println(gameEnviornment.yellowCarRect.y + " "
				+ gameEnviornment.yellowCarRect.height);

		if (gameEnviornment.dynamicDramCollid) {

		}

	}

	public void resize(int width, int height) {
		gameEnviornment.viewport.update(width, height);
	}

	@Override
	public void dispose() {
		Assets.instance.assetManager.dispose();
	}

}
