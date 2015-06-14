package com.race.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Assets implements Disposable ,AssetErrorListener{

	
	public   Texture dropImage;
	public   Texture bucketImage;
	public   Sound sideCollisionSound;
	public   Music carRunningMusic;
	public   AtlasRegion yellowCar;
	public   AtlasRegion street1;
	public   AtlasRegion street2;
	public   AtlasRegion fence;
	public   OrthographicCamera orthographicCamera;
	public   FitViewport viewport;
	public 	 ParticleEffect sideCollision;
	public 	 ParticleEffect dramExplo;
	
	public AssetManager assetManager;
	public ParticleEffect carSmoke;
	public AtlasRegion fence2;
	public AtlasRegion dram;
	public AtlasRegion lorry;
	public Sound breakFailSound;
	public Sound dramExplotionSound;
	public BitmapFont bitmapFont;
	public AtlasRegion powerUp;
	public AtlasRegion life;
	public TextureRegion progressBarOutline;
	public TextureRegion progressBarBack;
	public BitmapFont bitmapFont_gameOver;
	public Sound powerUpSound;
	public ParticleEffect carSmokeChange;
	public Animation animation;
	public Sound criticalDamageLevelSound;
	public Sound lifeUpSound;
	public AtlasRegion progressIconDamage;
	public AtlasRegion progressIconPowerUp;
	public Sound gameOverVoice;
	public Sound gameOverSound;
	public Animation powerUpAnimation;
	public Animation damageRecUpAnimation;
	public BitmapFont simpleFontLarge;
	public BitmapFont simpleFontSmall;
	public BitmapFont simpleFontLargeLarge;
	public Sound menuSound;
	public Sound car3Sound;
	public Sound lorryCollisionSound;
	public BitmapFont bitMapFont2;
	public TextureRegion menuStreet;
//	public Animation barAnimation;
	public Animation coinAnimation;
	public Sound coinSound;
	public Texture simpleFontMedium;
	private TextureAtlas assetBar;
	public AtlasRegion bar0;
	public AtlasRegion bar4;
	public AtlasRegion bar5;
	public AtlasRegion bar7;
	public BitmapFont bitmapFont_simple_mid;
	public AtlasRegion resumeIcon;
	public AtlasRegion fence_menu;
	public Sound bonusSound;
	public TextureRegion pausedText;
	public TextureRegion gameOverText;
	
	public static final Assets instance = new Assets();
	public static Sound gameStartSound ;
 	
	
	private Assets() {
		
	}
	
	
	
	public void init3(AssetManager gameAssetManager) {
		
		gameAssetManager.load("take/car_logo2.png", Texture.class);
//		gameAssetManager.load("take/thrustcopterassets.txt", TextureAtlas.class);
		gameAssetManager.load("take/impact-40.fnt", BitmapFont.class);
		gameAssetManager.load("take/loading.pack", TextureAtlas.class);
		// Wait until they are finished loading
		

	}
	
	public void init(AssetManager assetManager1) {
		this.assetManager = assetManager1;
		
		init3(assetManager);
		
		sideCollision = new ParticleEffect();
		carSmoke= new ParticleEffect();
		carSmokeChange = new ParticleEffect();
		dramExplo = new ParticleEffect();
		
		assetManager.setErrorListener(this);

		
		assetManager.load(Constants.race_asset,TextureAtlas.class);
		assetManager.load("animation/explosion_end.pack",TextureAtlas.class);
		assetManager.load("animation/life_up_animation2.pack",TextureAtlas.class);
		assetManager.load("animation/damage_rec_animation.pack",TextureAtlas.class);
		assetManager.load("animation/bar_anim1.pack",TextureAtlas.class);
		assetManager.load("animation/coin.pack",TextureAtlas.class);
		

		assetManager.load("sound/hit1.ogg",Sound.class);
		assetManager.load("sound/excelle.ogg",Sound.class);
		assetManager.load("sound/boom.ogg",Sound.class);
		assetManager.load("sound/brek_fail.ogg" ,Sound.class); // yes yes !!the spelling is wrong
		assetManager.load("sound/power_up1.ogg",Sound.class);
		assetManager.load("sound/aa.mp3",Music.class);
		assetManager.load("sound/critical_damage_level.ogg",Sound.class);
		assetManager.load("sound/lifeup2.ogg",Sound.class);
		assetManager.load("sound/game_over_voice.ogg",Sound.class);
		assetManager.load("sound/game_over_music.ogg",Sound.class);
		assetManager.load("sound/start.ogg",Sound.class);
		assetManager.load("sound/menu.ogg",Sound.class);
		assetManager.load("sound/coin.ogg",Sound.class);
		assetManager.load("sound/collistion_sound2.ogg",Sound.class);
		assetManager.load("sound/bonus4.ogg",Sound.class);

		
		
		assetManager.load("particle/side_collision5.party", ParticleEffect.class);
		assetManager.load("particle/smoke6.party", ParticleEffect.class);
		assetManager.load("particle/dram_ex1.party", ParticleEffect.class);
		assetManager.load("particle/smoke6_ch.party", ParticleEffect.class);
		
//		assetManager.load("font/arial-15.fnt", BitmapFont.class);
		assetManager.load("font/font4_26.fnt",BitmapFont.class);
		assetManager.load("font/simple_font40.fnt", BitmapFont.class);
		assetManager.load("font/font1_40.fnt", BitmapFont.class);
		assetManager.load("font/score.fnt", BitmapFont.class);
		assetManager.load("font/score1.fnt",BitmapFont.class);
		assetManager.load("font/start.fnt", BitmapFont.class);
		assetManager.load("font/score2.fnt",BitmapFont.class);
		assetManager.load("font/score40_4.fnt",BitmapFont.class);


		assetManager.load("take/simple_font_mid.fnt",BitmapFont.class);		
		assetManager.load("font/score2up.fnt",BitmapFont.class);
		assetManager.load("font/score4.fnt",BitmapFont.class);
//		assetManaget.load(BitmapFont.class);
		
		
		//assetManager.load("side_collision2.party",ParticleEffect.class);
		
//		
//		assetManager.load("menu_street.png",Texture.class);
		
		
//		//delete later
//		assetManager.load("prog2.png",Texture.class);
//		assetManager.load("prog_back3.png",Texture.class);
		
		
		
		assetManager.finishLoading();
		

		sideCollision= assetManager.get("particle/side_collision5.party", ParticleEffect.class);
		sideCollision.flipY();
		
		carSmoke=assetManager.get("particle/smoke6.party", ParticleEffect.class);
		carSmoke.flipY();
		
		carSmokeChange=assetManager.get("particle/smoke6_ch.party", ParticleEffect.class);
		carSmokeChange.flipY();
		
		
		dramExplo = assetManager.get("particle/dram_ex1.party", ParticleEffect.class);
		dramExplo.flipY();
		// race game asset

		TextureAtlas assetRace = assetManager.get(Constants.race_asset);
		yellowCar = assetRace.findRegion("car");
		street1 = assetRace.findRegion("street");
		street2 = assetRace.findRegion("street");
		fence = assetRace.findRegion("fence");
		fence2 = assetRace.findRegion("fence");
		dram = assetRace.findRegion("dram");
		lorry = assetRace.findRegion("lorry");
		powerUp = assetRace.findRegion("powerup");
		life = assetRace.findRegion("lifeup");
		progressIconDamage  = assetRace.findRegion("damage_icon");
		progressIconPowerUp =  assetRace.findRegion("powerup");
		
		
//		fence_menu = assetRace.findRegion("menu fnece");
		
		resumeIcon = assetRace.findRegion("resume_icon");
		
		
		TextureAtlas assetExplosion = assetManager.get("animation/explosion_end.pack");
		animation = new Animation(0.1f, assetExplosion.getRegions());
		
		TextureAtlas assetPowerUpAnimation = assetManager.get("animation/life_up_animation2.pack");
		powerUpAnimation  = new Animation(0.5f , assetPowerUpAnimation.getRegions());
		
		

		TextureAtlas assetDamageRecUpAnimation = assetManager.get("animation/damage_rec_animation.pack");
		damageRecUpAnimation  = new Animation(0.5f , assetDamageRecUpAnimation.getRegions());
		
		
		assetBar = assetManager.get("animation/bar_anim1.pack");
		
//		barAnimation = new Animation(2.0f, assetBar.getRegions());
		
//		bar0 = assetBar.findRegion("b0");
//		bar4 = assetBar.findRegion("b4");
//		bar5 = assetBar.findRegion("b5");
//		bar7 = assetBar.findRegion("b7");
		
		bar0 =  assetRace.findRegion("bar0");
		bar4 =  assetRace.findRegion("bar4");
		bar5 =  assetRace.findRegion("bar5");
		progressBarOutline =  assetRace.findRegion("prog2");
		progressBarBack = assetRace.findRegion("prog_back3");
		
		
//		progressBarOutline = new TextureRegion(assetManager.get("prog2.png",Texture.class));
//		progressBarBack = new TextureRegion(assetManager.get("prog_back3.png",Texture.class));
		
		TextureAtlas assetCoin = assetManager.get("animation/coin.pack");
		coinAnimation = new Animation(0.2f, assetCoin.getRegions());
		
		
		
		
		
		
		coinSound = assetManager.get("sound/coin.ogg",Sound.class);
		carRunningMusic = assetManager.get("sound/aa.mp3",Music.class);
		sideCollisionSound = assetManager.get("sound/hit1.ogg",Sound.class);
		lorryCollisionSound = assetManager.get("sound/collistion_sound2.ogg",Sound.class);
		dramExplotionSound = assetManager.get("sound/boom.ogg",Sound.class);
		breakFailSound = assetManager.get("sound/brek_fail.ogg" ,Sound.class);
		powerUpSound = assetManager.get("sound/power_up1.ogg",Sound.class);
		criticalDamageLevelSound = assetManager.get("sound/critical_damage_level.ogg",Sound.class);
		lifeUpSound = assetManager.get("sound/lifeup2.ogg",Sound.class);
		gameOverVoice = assetManager.get("sound/game_over_voice.ogg",Sound.class);
		gameOverSound = assetManager.get("sound/game_over_music.ogg",Sound.class);
		gameStartSound = assetManager.get("sound/start.ogg",Sound.class);
		menuSound = assetManager.get("sound/menu.ogg",Sound.class);
		bonusSound = assetManager.get("sound/bonus4.ogg",Sound.class);
		
		
		
		carRunningMusic.setLooping(true);
		carRunningMusic.setVolume(0.8f);
//		carRunningMusic.setLooping(carRunningMusicIslooping);
//		carRunningMusic.setVolume(carRunningMusicVolume);
		
//		carRunningMusic = assetManager.get("car3.ogg",Music.class);
		
		
		bitmapFont_gameOver = assetManager.get("font/font1_40.fnt", BitmapFont.class);
		bitmapFont_gameOver.setScale(1f, 1f);
		
		
		bitmapFont_simple_mid = assetManager.get("take/simple_font_mid.fnt",BitmapFont.class);
		
		
		
		bitmapFont = assetManager.get("font/score2.fnt",BitmapFont.class);
		bitmapFont.setScale(0.9f);

		
		
		bitMapFont2 = new BitmapFont();
		bitMapFont2 = assetManager.get("font/score2up.fnt",BitmapFont.class);
		bitMapFont2.setScale(1.4f);

		
		
		simpleFontLarge = new BitmapFont();
		simpleFontLarge = assetManager.get("font/simple_font40.fnt", BitmapFont.class);
//		simpleFontLarge.setColor(Color.RED);
		
		simpleFontSmall = new BitmapFont();
		simpleFontSmall = assetManager.get("font/font4_26.fnt", BitmapFont.class);
		
		simpleFontSmall.setScale(1.1f,1.1f);
//		simpleFontSmall.setColor(Color.rgba8888(255f,215f,0f,1f));
		
//		simpleFontLit = new BitmapFont();
//		simpleFontLit = assetManager.get();
		
		
		simpleFontLargeLarge = new BitmapFont();
		simpleFontLargeLarge = assetManager.get("font/start.fnt", BitmapFont.class);
		simpleFontLargeLarge.setScale(1f,1f);
		simpleFontLargeLarge.setColor(Color.RED);
		
		
		pausedText = new TextureRegion(new Texture(Gdx.files.internal("texture/paused.png")));
		gameOverText = new TextureRegion (new Texture(Gdx.files.internal("texture/game_over.png")));
		
		
	}
	
	

	@Override
	public void dispose() {
		assetManager.dispose();
		
	}

	@Override
	public void error(AssetDescriptor arg0, Throwable arg1) {
		
	}
	
	

}
