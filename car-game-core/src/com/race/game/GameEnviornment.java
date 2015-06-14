package com.race.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.race.game.util.Assets;
import com.race.game.util.Constants;
import com.race.game.util.GamePreferences;
import com.race.game.util.InputText;

import com.race.game.util.STATE;

public class GameEnviornment {

	private static final String TAG = Gdx.class.getName();

	public boolean criticalLevelSoundPlayed ;

	public GameController gameController;
	public FitViewport viewport;
	public OrthographicCamera orthographicCamera;
	public Rectangle yellowCarRect;
	public Rectangle fenceRectLeft;
	public Rectangle fenceRectRight;
	public Rectangle dramRect;
	public Rectangle powerUpRect;
	public SpriteBatch batch;
	public float posx;
	public float posy;
	public Sound dropSound;
	public float deltaTime;
	public float streetPosx;
	public float streetPosY;
	public float streetPosY2;
	public float fencePosx;
	public float fencePosY;
	public float fencePosY2;
	public float fencePosx2;
	public boolean sideCollisionFlag;
	private boolean isLeft;

	public float fencePosY21;
	public float fencePosY22;
	public long lastDramEnterTime;
	public long lastLorryEnterTime;
	public Array<Rectangle> drams;
	public Array<Rectangle> lorries;

	public long lastSideCollision;
	public boolean carCrashRoundFlag;
	public static boolean carCrashRoundAntiClockFlag;
	public boolean dynamicDramCollid;
	public static boolean powerBoost;
	public boolean sideCollisionColorChange;
	public Array<Rectangle> memorizedDrams;
	public long gameStartTime;
	private Array<Rectangle> memorizedLorries;
	private int memorizedDramsIndex;
	private int memorizedLorriesIndex;

	public int numberOfDramCollied;

	private float getRandomLorryX;

	public boolean showedPowerUp = false;

	public float powerUpX;

	// public boolean startPowerUpSpeed;

	public boolean powerUpShow;

	public float powrUpTimeCounter;

	public boolean powerUpEaten;

	public float normalSpeed_all;

	public float maxSpeed_all;
	public float dramNormalSpeed;
	public float lorryNormalSpeed;
	public boolean playGameOverExplosionFlag;
	public boolean stopDynamicObjectRenderFlag;
	public float powerUpNormalSpeed;
	public Rectangle lifeUpRect;
	// public boolean lifeUpShown;
	public float lifeUpNormalSpeed;
	public boolean lifeUpEaten;
	public boolean lifeUpStartShowing;
	public boolean lifeUpShownFixX;
	public int numberOfTimeDamageRecover;
	// public boolean stillKeepPowerUp;
	public long main_score;
	public long past_score_update_time;
	private STATE gameState;

	private int lorryCollisionDamage;
	private int fenceCollisionDamage;

	private float totalLife;
	private float damageLevel;
	private float prog;
	private Rectangle dramRectangle;
	private Rectangle lorryRectangle;
	public boolean collisionDramAddDamage;
	private int dramCollisionDamage;
	private float savedPauseDeltaTime;
	public boolean carRunningMusicIslooping;
	public float carRunningMusicVolume;

	public float sideCollisionSoundVolume;

	public float sideCollisionSoundPitch;

	public int sideCollisionSoundPan;

	public float criticSoundVolume;

	public int criticSoundPitch;

	public int criticSoundPan;

	public float dramExploSoundVolume;

	public float gameOverSoundVolume;

	public boolean gameStateBackToMainMenu;

	// public boolean DrawTimer;

	public int countDown;

	public long lastTime;

	private long lastPrintedTAB;
	private long pausedTimeStarted;
	private boolean pausedTimeStartedFlag;
	public boolean gameStartSoundPlayed;

	private float collision;
	private float collisionDamage;

	public boolean dramToLorryCollisionRemoved;
	private boolean dialogShown;
	public boolean drawCoinFlag;
	public boolean drawBarFlag;

	public boolean showedCoinUp;
	private float coinX;
	public Rectangle coinRect;
	public boolean coinSpeedStart;
	public float coinNormalSpeed;
	private float coinTimeCounter;
	private boolean startCoinSpeed;
	public boolean coinEaten;
	public long coinLastEnterTime;

	public Rectangle barRect;

	private int coinCounter;

	public long barLastEnterTime;

	public float barNormalSpeed;

	public boolean startBarSpeed;

	public boolean barSpeedStart;

	public boolean barEaten;

	public int barCounter;

	private boolean showedBarUp;

	public int lastBarState;

	private boolean lock;

	private float barY;
	public boolean counterLock;
	public long lastLifeUpEnterTime;
	public float lifeUpY;
	public long lastPowerUpEnterTime;
	

	// public boolean isLifeUpToShow;

	public GameEnviornment(float deltaTime) {
		
		init();
		
	}


	
	
	public void init() {

		initObjects();
		initCamera();

		initFlagsNdVar();
		initSpeeds(1f);
		initLifeUpVar();
		initPowerUpVar();
		initYellowCarRect();
		initCoinRect();
		initBarRect();

		preLoadedDram();
		preLoadedLorry();

		initDramRect();
		initPowerUpRect();
		initLifeUpRect();
		initFenceRect();

		initStreet();

		zeroScore();

		resetGameStartTime();
		
		
		memorizedLorriesIndex = 0;
		memorizedDramsIndex = 0;
	}

	
	public void initSecond() {
		initGameState();
		loadPref();
		Gdx.app.debug(TAG, "gamestate="+gameState().toString());
	}
	
	
	
	private void loadPref() {
		
		GamePreferences.instance.load();
		Gdx.app.debug(TAG, "pref ");
	}

	public void gameRestart() {

		// Gdx.app.log(TAG, "=Restart=");

		initFlagsNdVar();
		initSpeeds(1);
		initLifeUpVar();
		initPowerUpVar();
		initYellowCarRect();
		initCoinRect();

		initPowerUpRect();
		initLifeUpRect();
		initFenceRect();
		initStreet();

		zeroScore();
		initGameState();
		resetGameStartTime();

		loadPref();

		lorryCollisionDamage = Constants.lorryCollisionDamage;
		fenceCollisionDamage = Constants.fenceCollisionDamage;

	}

	public void resetGameStartTime() {

		gameStartTime = System.currentTimeMillis();

	}

	public void gameResume() {

		initSpeeds(1);
		timeDifferenceHandle();
		addlorryDramArrayDifferenceHandle();
		initGameState();

	}

	private void initCamera() {

		orthographicCamera.position.set(Constants.BACK_WIDTH / 2,
				Constants.BACK_HEIGHT / 2, 0);
		viewport = new FitViewport(Constants.BACK_WIDTH, Constants.BACK_HEIGHT,
				orthographicCamera);
	}

	private void addlorryDramArrayDifferenceHandle() {
		lastDramEnterTime = System.currentTimeMillis();
		lastLorryEnterTime = System.currentTimeMillis();
	}

	
	
	
	public void timeDifferenceHandle() {
		past_score_update_time = System.currentTimeMillis();
		lastDramEnterTime = System.currentTimeMillis();
		lastLorryEnterTime = System.currentTimeMillis();
		coinLastEnterTime = System.currentTimeMillis();
		lastLifeUpEnterTime = System.currentTimeMillis();
		lastPowerUpEnterTime = System.currentTimeMillis();
		barLastEnterTime = System.currentTimeMillis();

	}

	private void initGameState() {
		if (gameState() == STATE.RESUME)
			setGameState(STATE.RUNNING);
		else if (gameState() == STATE.PAUSE)
			setGameState(STATE.PAUSE);
		else
			setGameState(STATE.START);

		Gdx.app.debug(TAG, "system time diff = "
				+ (System.currentTimeMillis() - gameStartTime));

	}

	private void zeroScore() {
		main_score = 0;

	}

	private void initStreet() {
		streetPosx = 0;
		streetPosY = 0;
		streetPosY2 = Constants.BACK_HEIGHT2;

	}

	private void initSpeeds(float deltaTime) {
		normalSpeed_all = Constants.normal_speed * deltaTime;
		maxSpeed_all = Constants.power_up_speed * deltaTime;
		dramNormalSpeed = Constants.dram_normal_speed * deltaTime;
		lorryNormalSpeed = Constants.lorry_normal_speed * deltaTime;

	}

	private void initFlagsNdVar() {

		playGameOverExplosionFlag = false;
		stopDynamicObjectRenderFlag = false;
		carCrashRoundAntiClockFlag = false;
		carCrashRoundFlag = false;
		sideCollisionFlag = false;
		numberOfDramCollied = 0;
		totalLife = Constants.car_total_life;
		damageLevel = 0;
		collisionDramAddDamage = false;
		dramCollisionDamage = Constants.dramCollisionDamageUnit;
		gameStateBackToMainMenu = false;
		countDown = 4;
		lastTime = 0;
		pausedTimeStartedFlag = false;
		gameStartSoundPlayed = false;
		collisionDamage = 0;
		dramToLorryCollisionRemoved = false;
		drawCoinFlag = false;
		showedCoinUp = false;
		criticalLevelSoundPlayed = false;

	
		
		
		initLifeUpVar();
		initPowerUpVar();
		initCoinVar();
		initBarVar();
		soundNdMusicVar();
	}

	public void setDramToLorryCollistion() {
		dramToLorryCollisionRemoved = false;
	}

	private void soundNdMusicVar() {

		dialogShown = false;

		dramExploSoundVolume = Constants.dram_explo_sound_volume;

		criticSoundVolume = Constants.criticSoundVolume;
		criticSoundPitch = 1;
		criticSoundPan = 0;

		sideCollisionSoundVolume = Constants.sideCollisionSoundVolume;
		sideCollisionSoundPitch = 1;
		sideCollisionSoundPan = 0;

		carRunningMusicIslooping = true;
		carRunningMusicVolume = Constants.carRunningMusicVolume;

		gameOverSoundVolume = Constants.game_over_sound_volume;
	}


	private void initObjects() {

		orthographicCamera = new OrthographicCamera();
		gameStartTime = System.currentTimeMillis();

		yellowCarRect = new Rectangle();
		fenceRectLeft = new Rectangle();
		fenceRectRight = new Rectangle();
		dramRect = new Rectangle();

		powerUpRect = new Rectangle();
		lifeUpRect = new Rectangle();
		coinRect = new Rectangle();
		barRect = new Rectangle();

		drams = new Array<Rectangle>();
		lorries = new Array<Rectangle>();
		memorizedDrams = new Array<Rectangle>();
		memorizedLorries = new Array<Rectangle>();

	}

	private void initYellowCarRect() {
		yellowCarRect.x = Constants.car_rectangle_x - 2;
		yellowCarRect.y = Constants.car_rectangle_y;
		yellowCarRect.width = Constants.CAR_HEIGHT - 10;
		yellowCarRect.height = Constants.CAR_WIDTH - 2;

	}

	private void initDramRect() {
		dramRect.x = Constants.dram_posx;
		dramRect.y = Constants.dram_posy;
		dramRect.width = Constants.dram_width - 4;
		dramRect.height = Constants.dram_height;

	}

	private void initFenceRect() {
		fenceRectLeft.width = Constants.fence_width_left;
		fenceRectLeft.height = Constants.BACK_HEIGHT;
		fenceRectLeft.setPosition(Constants.fence_left_rectangle_posx,
				Constants.fence_left_y);

		fenceRectRight.width = Constants.fence_width_right;
		fenceRectRight.height = Constants.BACK_HEIGHT;
		fenceRectRight.setPosition(Constants.fence_right_rectangle_posx,
				Constants.fence_left_y);

		fencePosx = Constants.fence_left_x;
		fencePosx2 = Constants.fence_right_x;
		fencePosY = 0;
		fencePosY2 = Constants.BACK_HEIGHT;
		fencePosY21 = Constants.BACK_HEIGHT;
		fencePosY22 = Constants.BACK_HEIGHT + Constants.BACK_HEIGHT;

	}

	private void initPowerUpRect() {
		powerUpRect.x = Constants.power_pos_x_min;
		powerUpRect.y = Constants.BACK_HEIGHT2;
		powerUpRect.width = Constants.power_up_width - 20;
		powerUpRect.height = Constants.power_up_height;

	}

	public void initLifeUpRect() {

		lifeUpRect.x = Constants.life_up_pos_x_min;
		lifeUpRect.y = Constants.BACK_HEIGHT;
		lifeUpRect.width = Constants.life_up_widht - 5;
		lifeUpRect.height = Constants.life_up_height - 5;

	}

	private void initCoinRect() {
		coinRect.x = Constants.coin_x_min;
		coinRect.y = Constants.BACK_HEIGHT2;
		coinRect.width = Constants.coin_width - 4;
		coinRect.height = Constants.coin_height - 10;

	}

	private void initBarRect() {
		barRect.x = Constants.bar_x_min;
		barRect.y = Constants.BACK_HEIGHT2;
		barRect.width = Constants.bar_width + 5;
		barRect.height = Constants.bar_height;

	}

	private void initBarVar() {
		barNormalSpeed = Constants.bar_normal_speed;
		startBarSpeed = false;
		barSpeedStart = false;
		barEaten = false;
		counterLock = false;
		barCounter = 0;
		barLastEnterTime = System.currentTimeMillis();
		drawBarFlag = false;
		showedBarUp = false;
		lastBarState = 1;
	}

	private void initBarAtNewTop() {
		counterLock = false;
		barRect.y = Constants.BACK_HEIGHT2;
	}

	private void initCoinVar() {
		coinNormalSpeed = Constants.coin_normal_speed;
		startCoinSpeed = false;
		coinSpeedStart = false;
		coinEaten = false;
		coinCounter = 0;
		coinLastEnterTime = System.currentTimeMillis();

	}

	private void initPowerUpVar() {
		powerUpNormalSpeed = Constants.power_up_normal_speed;
		powerUpShow = false;
		initPowerUpStatPowerCommon();
	}

	public void startPowerUp() {
		powerUpShow = true;
		initPowerUpStatPowerCommon();

	}

	private void initPowerUpStatPowerCommon() {
		powerUpRect.y = Constants.BACK_HEIGHT2;
		powrUpTimeCounter = Constants.power_up_total_time;
		powerBoost = false;
		powerUpEaten = false;
		lastPowerUpEnterTime = System.currentTimeMillis();

	}

	public void initLifeUpVar() {

		lifeUpNormalSpeed = Constants.life_up_normal_speed;
		numberOfTimeDamageRecover = 0;
		lifeUpStartShowing = false;
		initLifeUpStartLifeUpCommon();

	}

	public void startLifeUp() {
		lifeUpStartShowing = true;
		initLifeUpStartLifeUpCommon();

	}

	private void initLifeUpStartLifeUpCommon() {
		lifeUpEaten = false;
		lifeUpRect.y = Constants.BACK_HEIGHT2;
		lifeUpShownFixX = true;
		lastLifeUpEnterTime = System.currentTimeMillis();

	}

	public void addToDramArray_old() {

		Rectangle dramRectangle = new Rectangle();

		dramRectangle.x = MathUtils.random(Constants.fence_left_rectangle_posx,
				Constants.fence_right_rectangle_posx - 10);
		dramRectangle.y = Constants.BACK_HEIGHT;
		dramRectangle.height = Constants.dram_height;
		dramRectangle.width = Constants.dram_width - 4;

		drams.add(dramRectangle);

		lastDramEnterTime = System.currentTimeMillis();
	}

	public void addToDramArray() {

		if (memorizedDramsIndex < Constants.pre_loaded_dram_max_index)
			memorizedDramsIndex++;
		else
			memorizedDramsIndex = 0;

		// Gdx.app.log(TAG, "memorized daram = " + memorizedDramsIndex);

		Rectangle dramRectangle = (Rectangle) memorizedDrams
				.get(memorizedDramsIndex);

		dramRectangle.x = MathUtils.random(
				Constants.fence_left_rectangle_posx + 2,
				Constants.fence_right_x);

		dramRectangle.y = Constants.BACK_HEIGHT;

		// dramRectangle.height = Constants.dram_height;
		// dramRectangle.width = Constants.dram_width-4;

		drams.add(dramRectangle);

		lastDramEnterTime = System.currentTimeMillis();
	}

	private void preLoadedDram() {
		Rectangle dramRectangle;
		for (int i = 0; i < Constants.pre_loaded_dram_size; i++) {

			dramRectangle = new Rectangle();

			dramRectangle.height = Constants.dram_height;
			dramRectangle.width = Constants.dram_width - 4;

			memorizedDrams.add(dramRectangle);
		}
	}

	public void preLoadedLorry() {
		Rectangle lorryRectangle;

		for (int j = 0; j < Constants.pre_loaded_lorry_size; j++) {
			lorryRectangle = new Rectangle();

			lorryRectangle.width = Constants.lorry_height;
			lorryRectangle.height = Constants.lorry_width;

			memorizedLorries.add(lorryRectangle);
		}

	}

	public void addToLorryArray() {

		if (memorizedLorriesIndex < Constants.pre_loaded_lorry_max_index)
			memorizedLorriesIndex++;
		else
			memorizedLorriesIndex = 0;
		// Gdx.app.log(TAG, "memorized lorries = " + memorizedLorriesIndex);

		Rectangle lorryRectangle = memorizedLorries.get(memorizedLorriesIndex);

		getRandomLorryX = MathUtils.random(Constants.fence_left_rectangle_posx,
				Constants.fence_right_x- 10);

		if (getRandomLorryX > Constants.do_not_print_lorry_in_middle_min
				&& getRandomLorryX < Constants.do_not_print_lorry_in_middle_max+20) {
			getRandomLorryX = Constants.do_not_print_lorry_in_middle_min - 30;
		}

		lorryRectangle.x = getRandomLorryX;

		lorryRectangle.y = Constants.BACK_HEIGHT + 20;
		// lorryRectangle.width = Constants.lorry_height;
		// lorryRectangle.height = Constants.lorry_width;

		lorries.add(lorryRectangle);

		lastLorryEnterTime = System.currentTimeMillis();
	}

	public float getYellowCarRectX() {
		return yellowCarRect.x;
	}

	public void setYellowCarRectX(float yellowCarRectX) {
		this.yellowCarRect.x = yellowCarRectX;

	}

	public void addYellowCarRectX(float addYellowCarRectX) {
		this.yellowCarRect.x += addYellowCarRectX;
	}

	public void sideCollision(boolean b, boolean isLeft) {

		sideCollisionFlag = b;
		this.isLeft = isLeft;
	}

	public boolean isLeftSide() {
		return isLeft;
	}

	public boolean getIfSideCollision() {
		return sideCollisionFlag;
	}

	public void setCollidWithCar(boolean b, float x, float y) {
		this.dynamicDramCollid = true;
	}

	public void setPowerUpX(float powerUpX2) {
		this.powerUpX = powerUpX2;
	}

	public void setPowerUpXYToRectangle(float powerUpX2, float powerUpY) {
		powerUpRect.x = powerUpX2;
		powerUpRect.y = powerUpY;

	}

	public void setSpeed(float speed) {

		this.stopDynamicObjectRenderFlag = true;

		this.normalSpeed_all = speed;
		this.maxSpeed_all = speed;
		this.dramNormalSpeed = speed;
		this.lorryNormalSpeed = speed;
		this.powerUpNormalSpeed = speed;

	}

	public void soundStop() {
		Assets.instance.carSmoke.reset();
		Assets.instance.breakFailSound.stop();

		Assets.instance.carRunningMusic.setLooping(false);
		Assets.instance.carRunningMusic.stop();
		Assets.instance.criticalDamageLevelSound.stop();
		Assets.instance.carRunningMusic.stop();

		// Assets.instance.dramExplotionSound.play();
	}

	public boolean isTimeFinis() {

		if (System.currentTimeMillis() - gameStartTime > Constants.game_finish_time_mili)
			return true;

		return false;
	}

	public long getGameScoreUnit() {

		if (System.currentTimeMillis() - past_score_update_time > 1000) {
			past_score_update_time = System.currentTimeMillis();
			return 20;
		}

		return 0;
	}

	public void setDramSpeed(String zero) {

	}

	public void lifeUpSoundPlay() {

		Assets.instance.lifeUpSound.play(sideCollisionSoundVolume,
				sideCollisionSoundPitch, sideCollisionSoundPan);
	}

	public void powerUpSoundPlay() {
		Assets.instance.powerUpSound.play(sideCollisionSoundVolume,
				sideCollisionSoundPitch, sideCollisionSoundPan);

	}

	public void coinSoundPlay() {
		Assets.instance.powerUpSound.play(sideCollisionSoundVolume,
				sideCollisionSoundPitch, sideCollisionSoundPan);

	}

	public void addScore(long newScore) {

		if (gameState() == STATE.RUNNING) {
			if (main_score < 0) {
				main_score = 0;
			} else {
				main_score += (newScore);
			}
		}

	}

	public long getScore() {
		return main_score;
	}

	public void setGameState(STATE gameState) {

		this.gameState = gameState;

	}

	public STATE gameState() {
		return gameState;
	}

	public float lifeProgressAmount() {

		// boolean debug = true;
		boolean debug = false;

		if (collisionDramAddDamage && !debug) {
			damageLevel += (dramCollisionDamage * Constants.damage_progress_multi)
					+ (lorryCollisionDamage * Constants.lorryCollisionDamageMulti)
					+ (fenceCollisionDamage * Constants.fenceCollisionDamageMulti)
					+ collisionDamage;

			collisionDramAddDamage = false;
		}
		prog = totalLife - damageLevel;

		// Gdx.app.log(TAG, "damage amount == "+ damageLevel);
		return prog;
	}

	public void playCriticalDamageSound() {

		if (gameState == STATE.RUNNING)
			Assets.instance.criticalDamageLevelSound.loop(0.5f, 1f, 0f);

	}

	public void decDamageLevelBy(int newAddition) {

		damageLevel -= newAddition;

	}

	public void damageCarBy(float damage) {
		damageLevel += damage;
	}

	public boolean setBackToMenuScreen() {
		if (gameState() == STATE.BACK_TO_MENU) {
			setGameState(STATE.PAUSE);
//			setGameState(STATE.GAME_OVER);
			return true;
		}
		return false;
	}

	public void saveDeltaTime(float deltaTime2) {
		savedPauseDeltaTime = deltaTime2;
	}

	public boolean printTab() {

		if (System.currentTimeMillis() - lastPrintedTAB > 900) {
			if (System.currentTimeMillis() - lastPrintedTAB > 1600) {

				lastPrintedTAB = System.currentTimeMillis();
				return false;
			}
			return true;
		}

		return false;
	}

	public void startPausedTime() {
		if (!pausedTimeStartedFlag) {
			pausedTimeStarted = System.currentTimeMillis();
			lastPrintedTAB = pausedTimeStarted;

			pausedTimeStartedFlag = true;
		}
	}

	public void handleHighScore() {

		if (main_score > GamePreferences.instance.highstScore) {
			if (!dialogShown) {
				GamePreferences.instance.saveScore(main_score);
				dialogShown = true;
			}
		}

	}

	public void setCoinUpX(float coinX2) {
		this.coinX = coinX2;
	}

	public void startCoin() {

		coinLastEnterTime = System.currentTimeMillis();

	}

	public void startCoinFlag() {

		coinSpeedStart = true;
		showedCoinUp = false;
		coinEaten = false;
	}

	public int getCoinCounter() {
		return coinCounter;
	}

	public void addToCoinCounter(int coinCounter) {
		this.coinCounter += coinCounter;
	}

	public void startBar() {
		barLastEnterTime = System.currentTimeMillis();
		barRect.y = Constants.BACK_HEIGHT2;
		counterLock = false;
	}

	public void startBarFlag() {

		barSpeedStart = true;
		showedBarUp = true;
		barEaten = false;
		drawBarFlag = true;
	}

	public boolean openGate() {
		if (getCoinCounter() >= 5) {

			// if(barRect.y < 0)
			{

				if (!barEaten && !counterLock) {

					coinCounter -= 5;
					counterLock = true;
				}

			}

			return true;
		}
		return false;
	}

	public boolean openGateDraw() {

		return (coinCounter >= 5);
	}

	public boolean ifLifeUpDraw() {

		if (lifeUpStartShowing) {
			if (!lifeUpEaten && lifeUpRect.y > -10) {

				return true;
			}
		} else {
			lifeUpStartShowing = false;
		}

		return false;
	}

	public boolean ifStartPowerUp() {

		if (powerUpShow) {
			if (!powerUpEaten && powerUpRect.y > -10) {
				return true;
			}

		} else {
			powerUpRect.y = Constants.BACK_HEIGHT2;
		}

		return false;
	}

}
