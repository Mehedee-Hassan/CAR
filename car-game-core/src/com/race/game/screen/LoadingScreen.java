package com.race.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.race.game.loading.bar.LoadingBar;
import com.race.game.main.RaceGameMain;
import com.race.game.menu.MenuScreen;
import com.race.game.util.Assets;
import com.race.game.util.Constants;



public class LoadingScreen extends ScreenAdapter {
	private Stage stage;

	private Image logo;
	private Image loadingFrame;
	private Image loadingBarHidden;

	private Image loadingBg;
	TextureAtlas atlas;
	private float startX, endX;
	private float percent;

	private Actor loadingBar;
	RaceGameMain game;

	private float waitTime=1;

	public LoadingScreen(RaceGameMain raceGame) {
		game=raceGame;
	}

	@Override
	public void show() {

		// Tell the manager to load assets for the loading screen
		game.manager.load("take/loading.pack", TextureAtlas.class);
		// Wait until they are finished loading
		game.manager.finishLoading();

		// Initialize the stage where we will place everything
		stage = new Stage(game.viewport);
	
		// Get our textureatlas from the manager
		 atlas = game.manager.get("take/loading.pack", TextureAtlas.class);

		
		loadingFrame = new Image(atlas.findRegion("loading-frame"));
		loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
		loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

		
		
		// Add the loading bar animation
		Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim"));
		anim.setPlayMode(PlayMode.LOOP_REVERSED);
		
		//screen
		loadingBar = new LoadingBar(anim);
		loadingBar.setPosition(200, Constants.BACK_HEIGHT);
		loadingBg.setPosition(100, 400);
		stage.addActor(loadingBar);
		stage.addActor(loadingBg);
		stage.addActor(loadingBarHidden);
		stage.addActor(loadingFrame);

		
		
		// Or if you only need a static bar, you can do
		// loadingBar = new Image(atlas.findRegion("loading-bar1"));

		
		
		Assets.instance.init(game.manager);
		
		
		game.manager.finishLoading();
		// Add all the actors to the stage


	}

	@Override
	public void resize(int width, int height) {
		// Scale the viewport to fit the screen
		Vector2 scaledView = Scaling.fit.apply(Constants.BACK_HEIGHT, Constants.BACK_WIDTH, width, height);
		stage.getViewport().update((int)scaledView.x, (int)scaledView.y, true);

		// Make the background fill the screen
//		screenBg.setSize(Constants.BACK_WIDTH, Constants.BACK_HEIGHT);

		// Place the logo in the middle of the screen and 100 px up
//		logo.setX((width - logo.getWidth()) / 2);
//		logo.setY((height - logo.getHeight()) / 2 + 100);

		// Place the loading frame in the middle of the screen
		loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
		loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

		// Place the loading bar at the same spot as the frame, adjusted a few
		// px
		loadingBar.setX(loadingFrame.getX() + 15);
		loadingBar.setY(loadingFrame.getY() + 5);

		// Place the image that will hide the bar on top of the bar, adjusted a
		// few px
		loadingBarHidden.setX(loadingBar.getX() + 35);
		loadingBarHidden.setY(loadingBar.getY() - 3);
		// The start position and how far to move the hidden loading bar
		startX = loadingBarHidden.getX();
		endX = 440;

		// The rest of the hidden bar
		loadingBg.setSize(450, 50);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setY(loadingBarHidden.getY() + 3);
	}

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (game.manager.update()) { // Load some, will return true if done
										// loading
			waitTime-=delta;
			if(waitTime<=0){
			game.font=game.manager.get("take/impact-40.fnt", BitmapFont.class);
			game.setScreen(new MenuScreen(game));
			}
		}

		// Interpolate the percentage to make it more smooth
		percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);

		// Update positions (and size) to match the percentage
		loadingBarHidden.setX(startX + endX * percent);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setWidth(450 - 450 * percent);
		loadingBg.invalidate();

		// Show the loading screen
		stage.act();
		stage.draw();
	}

	@Override
	public void hide() {
		// Dispose the loading assets as we no longer need them
		game.manager.unload("take/loading.pack");
	}
	
	@Override
	public void dispose () {
		
		atlas.dispose();
		stage.dispose();
		
		
	}
}
