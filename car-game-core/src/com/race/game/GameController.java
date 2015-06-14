package com.race.game;

import java.util.Iterator;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.race.game.util.Assets;
import com.race.game.util.Constants;
import com.race.game.util.STATE;

public class GameController {

	public static String TAG = "Gdx.game.GameController";

	GameRenderer gameRenderer;
	private GameEnviornment gameEnviornment;
	private int i;
	private boolean dramCollid;
	private int currentLorry;
	Iterator<Rectangle> itr;
	private Rectangle dram;
	private Rectangle lorry;
	private float X;
	private float lorryX;
	private boolean powerUpSoundPlayed;
	public boolean gameOverMusicPlayed;
	public boolean gameOverVoicePlayed;
	private long gameOverMusicPlayedTime;
	private boolean lifeUpSoundPlayed;
	private boolean coinSoundPlayed;

	public GameController(GameEnviornment gameEnviornment) {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(TAG, "Constractor");
		this.gameEnviornment = gameEnviornment;
		// Gdx.input.setInputProcessor(this);

		init();
	}

	public void init() {
		powerUpSoundPlayed = false;
		gameOverMusicPlayed = false;
		gameOverVoicePlayed = false;
		lifeUpSoundPlayed = false;
		coinSoundPlayed = false;
		// getInputLogic();
		i = 0;
	}

	public void gameRestart() {

		Gdx.app.log(TAG, "=Restart=");
		init();
	}

	public void update(float deltaTime) {

		Gdx.app.log(TAG, "delta time from rend = " + deltaTime
				+ " this class =" + Gdx.graphics.getDeltaTime());
		getInputLogic();

		if (gameEnviornment.gameState() == STATE.RUNNING) {
			dramArrayUpdate();
			lorryArrayUpdate();
			powerUpLogic();
			lifeUpLogic();
			menuKeyLogic();
			yellowCarLogic();
			streetUpdate();
			coinLogic();
			barLogic();

		}
	}

	private void streetUpdate() {

	}

	public void getInputLogic() {

	}

	public boolean keyDown(int keycode) {

		return true;
	}

	private void yellowCarLogic() {

		if (gameEnviornment.yellowCarRect
				.overlaps(gameEnviornment.fenceRectLeft)) {

			gameEnviornment.addScore(0);
			gameEnviornment.damageCarBy(0.07f);

			gameEnviornment.sideCollisionColorChange = true;
			gameEnviornment.lastSideCollision = System.currentTimeMillis();

			// debug
			if (i == 10) {

				Assets.instance.sideCollisionSound.play(
						Constants.sideCollisionSoundVolume, 1, 0);

				gameEnviornment.sideCollision(true, true);

				i = 0;
			}
			i++;

		} else if (gameEnviornment.yellowCarRect
				.overlaps(gameEnviornment.fenceRectRight)) {

			gameEnviornment.addScore(0);
			gameEnviornment.damageCarBy(0.07f);

			gameEnviornment.sideCollisionColorChange = true;
			gameEnviornment.lastSideCollision = System.currentTimeMillis();

			// debug
			if (i == 10) {
				Assets.instance.sideCollisionSound.play(
						Constants.sideCollisionSoundVolume, 1, 0);
				i = 0;
			}

			i++;
			gameEnviornment.sideCollision(true, false);
		} else {

			gameEnviornment.sideCollision(false, false);
		}

	}

	private void menuKeyLogic() {
		if (Gdx.input.isKeyPressed(Keys.MENU)) {
			Gdx.app.debug(TAG, "menu pressed");
		}
	}

	private void lifeUpLogic() {

		if (gameEnviornment.lifeUpRect.overlaps(gameEnviornment.yellowCarRect)) {

			Gdx.app.log(TAG, "lifeUp eaten ==");

			if (!gameEnviornment.lifeUpEaten) {

				Assets.instance.lifeUpSound.play(0.8f);
				gameEnviornment.lifeUpEaten = true;
				gameEnviornment.lifeUpStartShowing = false;
				gameEnviornment.lifeUpRect.y = Constants.BACK_HEIGHT2;
				increaseLifeLevel();

			}

		}

	}

	private void increaseLifeLevel() {
		gameEnviornment.numberOfTimeDamageRecover++;
		gameEnviornment.decDamageLevelBy(20);
	}

	private void powerUpLogic() {

		if (gameEnviornment.powerUpRect.overlaps(gameEnviornment.yellowCarRect)) {
			gameEnviornment.powerBoost = true;

			if (!gameEnviornment.powerUpEaten) {

				Assets.instance.powerUpSound.play(0.8f);
				
				gameEnviornment.addScore(5);
				
				powerUpSoundPlayed = true;
				gameEnviornment.powerUpShow = false;
				gameEnviornment.powerUpEaten = true;
				gameEnviornment.powerUpRect.y = Constants.BACK_HEIGHT2;
			}

		}
	}

	private float goBackToTop() {
		// TODO Auto-generated method stub
		return Constants.BACK_HEIGHT;
	}

	private void coinLogic() {

		if (gameEnviornment.coinRect.overlaps(gameEnviornment.yellowCarRect)) {

			gameEnviornment.coinEaten = true;

			// if (!coinSoundPlayed)
			{

				Assets.instance.coinSound.play(0.8f);
				gameEnviornment.coinRect.y = goBackToTop();
				coinSoundPlayed = true;
				gameEnviornment.addToCoinCounter(1);
				if (gameEnviornment.getCoinCounter() == 5){
					gameEnviornment.addScore(5);
					Assets.instance.bonusSound.play(0.6f);
				}
				else
					gameEnviornment.addScore(3);
			}

		}
	}
	float incrementOfDamage;

	private void barLogic() {
		
		if (gameEnviornment.barRect.overlaps(gameEnviornment.yellowCarRect)) {

			if (!gameEnviornment.openGate() && !gameEnviornment.barEaten
					&& !gameEnviornment.counterLock) {
				gameEnviornment.barEaten = true;

				{
					incrementOfDamage = 0;
					if(gameEnviornment.gameStartTime  - System.currentTimeMillis() > 80000)
						incrementOfDamage = MathUtils.random(4, 7);
					
						
						
					gameEnviornment.damageCarBy(2f+incrementOfDamage);
					Assets.instance.sideCollisionSound.play(0.5f);
					Assets.instance.lorryCollisionSound.play(0.8f);

				}
			}
		}
	}

	private void lorryArrayUpdate() {
		Iterator<Rectangle> itr = gameEnviornment.lorries.iterator();
		currentLorry = 0;
		while (itr.hasNext()) {

			lorryLogic(itr);

		}
	}

	private void lorryLogic(Iterator<Rectangle> itr) {

		// Gdx.app.log(TAG, "lorry logic");

		lorry = itr.next();
		currentLorry++;

		lorry.y -= gameEnviornment.lorryNormalSpeed
				* Gdx.graphics.getDeltaTime();

		if (lorry.y + Constants.lorry_width <= 0) {
			itr.remove();
			gameEnviornment.addScore(1);
		}

		if (lorry.overlaps(gameEnviornment.yellowCarRect)) {

			// gameEnviornment.addScore(-1);
			gameEnviornment.damageCarBy(0.5f);

			// Assets.instance.criticalDamageLevelSound.stop();
			Assets.instance.lorryCollisionSound.play(
					Constants.sideCollisionSoundVolume, 1, 0);

			Assets.instance.breakFailSound.play(
					Constants.sideCollisionSoundVolume, 1, 0);


			gameEnviornment.carCrashRoundFlag = true;

			if (gameEnviornment.yellowCarRect.x
					+ gameEnviornment.yellowCarRect.width - 40 < lorry.x) {
				gameEnviornment.carCrashRoundAntiClockFlag = true;
				gameEnviornment.yellowCarRect.x = lorry.x
						- gameEnviornment.yellowCarRect.width - 20;
			} else {

				gameEnviornment.carCrashRoundAntiClockFlag = false;
				gameEnviornment.yellowCarRect.x = lorry.x
						+ gameEnviornment.yellowCarRect.width + 20;
			}
		}

		else {

		}

		// Gdx.app.log(TAG, "lorry logic ends");
	}

	private void dramArrayUpdate() {

		itr = gameEnviornment.drams.iterator();
		while (itr.hasNext()) {

			dramLogic(itr);

		}
	}

	private void dramLogic(Iterator<Rectangle> itr) {

		// Gdx.app.log(TAG, "dram logic");

		dram = itr.next();

		dram.y -= gameEnviornment.dramNormalSpeed * Gdx.graphics.getDeltaTime();

		if (dram.y + gameEnviornment.dramRect.height <= 0) {
			itr.remove();
			gameEnviornment.addScore(1);
		}

		if (dram.overlaps(gameEnviornment.yellowCarRect)) {

			gameEnviornment.setCollidWithCar(true, dram.x, dram.y);
			{
				itr.remove();
				gameEnviornment.addScore(1);
			}

			if (!dramCollid) {
				// gameEnviornment.addScore(-4);
				// Assets.instance.criticalDamageLevelSound.stop();

				Assets.instance.dramExplotionSound.play(0.8f, 1, 0);

				// Assets.instance.criticalDamageLevelSound.loop(
				// gameEnviornment.criticSoundVolume,
				// gameEnviornment.criticSoundPitch,
				// gameEnviornment.criticSoundPan);

				gameEnviornment.numberOfDramCollied++;

				dramCollid = true;
				gameEnviornment.collisionDramAddDamage = true;
			}

		} else {
			dramCollid = false;
		}
		if (gameEnviornment.lorries.size != 0)
			if (dram.overlaps(gameEnviornment.lorries.first())) {
				itr.remove();
			}
	}

	// game over

	public float getProgressAmount() {
		return (gameEnviornment.lifeProgressAmount());
	}

	public boolean isGameOver() {
		if (getProgressAmount() <= 0
				&& gameEnviornment.gameState() != STATE.BACK_TO_MENU) {
			gameEnviornment.setGameState(STATE.GAME_OVER);
			return true;
		}

		return false;
	}

	public boolean StopAll() {

		gameEnviornment.playGameOverExplosionFlag = true;
		gameEnviornment.soundStop();

		if (!gameOverMusicPlayed) {
			Assets.instance.gameOverSound.play(
					Constants.game_over_sound_volume, 2f, 0);

			gameOverMusicPlayed = true;
			gameOverMusicPlayedTime = System.currentTimeMillis();
		}

		if (!gameOverVoicePlayed
				&& (System.currentTimeMillis() - gameOverMusicPlayedTime) > 800) {
			Assets.instance.gameOverVoice.play(1f, 1f, 0);
			gameOverVoicePlayed = true;
		}

		return true;
	}

	// power up

	public boolean checkIfLorryOnTheWay(float xOfPoserUp) {
		if (gameEnviornment.lorries.size > 0)
			lorryX = gameEnviornment.lorries.first().x;

		if (Constants.power_up_width + gameEnviornment.powerUpX > lorryX
				&& gameEnviornment.powerUpX < lorryX + Constants.lorry_width)

			return false;

		return true;
	}

	public float getPowerUpAmount() {

		return gameEnviornment.powrUpTimeCounter;

	}

	public void setPowerBoostOff(float powrUpTimeCounter) {
		if (powrUpTimeCounter < 5) {
			gameEnviornment.powerUpShow = false;
			gameEnviornment.powerUpEaten = false;

		}
	}

	// public boolean isLifeUpToShow() {
	// // if (getProgressAmount() < 60 && !gameEnviornment.lifeUpEaten)
	// if (System.currentTimeMillis() - gameEnviornment.lastLifeUpEnterTime< 600
	// && !gameEnviornment.lifeUpEaten)
	// {
	//
	// gameEnviornment.lifeUpStartShowing = true;
	// gameEnviornment.lastLifeUpEnterTime = System.currentTimeMillis();
	//
	// return true;
	// }
	// return false;
	// }

	public void gameResume() {
		this.init();
	}

}
