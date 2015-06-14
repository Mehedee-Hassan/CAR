package com.race.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.race.game.main.RaceGameMain;
import com.race.game.screen.BaseScreen;
import com.race.game.screen.RaceGameScreen;
import com.race.game.util.Assets;
import com.race.game.util.Constants;
import com.race.game.util.GamePreferences;

public class MenuScreen extends BaseScreen {
	private Stage stage;
	// private Image screenBg;
	private Skin skin;
	private Image title;
	// private Label helpTip;
	private Table table;
	private Table options;
	private Window howToPlay;
	private Table howToPlayTable;
	private Table exit;
	private CheckBox muteCheckBox;
	private Slider volumeSlider;
	private TextButton backButton;
	private TextButton playButton;
//	private TextButton optionsButton;
	private TextButton exitButton;
	private TextButton yesButton;
	private TextButton noButton;
	private TextButton showHowToPlayButton;
	private TextButton nextFromHowToPlayS1Button;
	private boolean menuShown;
	private boolean exitShown;
	private TextButton backButton2;
	private Skin skin2;
	private TextButton showScoreBoardButton;
	private Table aboutTable;
	private TextButton aboutButton;
	private boolean aboutShown;
	private Actor backButton3;
	private boolean menuSoundPlayed;
	private long menuSoundPlayTime;
	private SpriteBatch batch;
	private int screenwidth;
	private int screenHeight;
	private Table howToPlayTab1Rule1;
	private Table howToPlayTab4Rule2;
	private Table howToPlayTab2Rule1;
	private Table howToPlayTab3Rule2;
	private Table tableHowToPlayMain;
	private TextButton backFromHowToPlayS1Button;

	private Table tableHowToPlayMain2;
	private TextButton backFromHowToPlayS1Button1;
	private TextButton playFromHowToPlayS1Button1;
	private Table scoreBoardTableMain;
	private TextButton backFromScoreBoard;

	public MenuScreen(final RaceGameMain game) {
		super(game);

		batch = new SpriteBatch();
		getScreen();
		stage = new Stage(game.viewport);
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("take/uiskin.json"));
		skin2 = new Skin(Gdx.files.internal("take/uiskin.json"));

		skin.add("menu_back_street", new Texture("menu_street.png"));

		title = new Image(game.manager.get("take/car_logo2.png", Texture.class));
		
		// helpTip=new Label("Tap around the plane to move it!",skin);
		// helpTip.setColor(Color.NAVY);

		
		Gdx.input.setCatchBackKey(true);
		
		
		mainMenuTable();
		optionMenu();
		howToPlayTable();
		aboutTable();
	 // howToPlayTable.setPosition(400, -200);
		exitTable();

		howToPlayScreen1();
		howToPlayScreen2();
		createScoreboardTable();
		
		// stage.addActor(screenBg);
		stage.addActor(title);
		// stage.addActor(helpTip);
		stage.addActor(table);
		stage.addActor(options);
		stage.addActor(exit);
		stage.addActor(howToPlayTable);
		stage.addActor(aboutTable);
		stage.addActor(scoreBoardTableMain);

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				
				if(GamePreferences.instance.firstTimeAppRunRet() < 3){

					GamePreferences.instance.saveFirstTimeOpend();
					showHowToPlay(false);
				}
				else 
					game.setScreen(new RaceGameScreen(game));
			}
		});

		showHowToPlayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 showHowToPlay(false);
//				game.setScreen(new HowToPlayScreen(game));
			}
		});

		backButton2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		backButton3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showAbout(false);
			}
		});

//		optionsButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				showMenu(false);
//			}
//		});
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				// or System.exit(0);
			}
		});
		yesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				// or System.exit(0);
			}
		});
		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showExit2(false);
			}
		});

		volumeSlider.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.soundVolume = volumeSlider.getValue();
			}
		});

		muteCheckBox.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.soundEnabled = !muteCheckBox.isChecked();
			}
		});
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showMenu(true);
			}
		});

		aboutButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				showAbout(true);
			}
		});

		showScoreBoardButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.setScreen(new ScoreBoardScreen(game));
				 showScoreBoard(true);
				
			}

		});
		
		backFromScoreBoard.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.setScreen(new ScoreBoardScreen(game));
				 showScoreBoard(false);
				
			}

		});

		
		nextFromHowToPlayS1Button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showHowToPlay2(false);
			}
		});
		
		
		backFromHowToPlayS1Button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showHowToPlay(true);
			}
		});
		
		
		backFromHowToPlayS1Button1.addListener(new ClickListener(){@Override
		public void clicked(InputEvent event, float x, float y) {
			showHowToPlay2(true);
		}});
		
		playFromHowToPlayS1Button1.addListener(new ClickListener(){@Override
		public void clicked(InputEvent event, float x, float y) {
			game.setScreen(new RaceGameScreen(game));
		}});
		
		
		
		
		
		
		
	
	}

	private void exitTable() {
		exit = new Table();
		Label exitTitle = new Label("Confirm Exit", skin,"default2");
		exit.add(exitTitle).padBottom(25).colspan(2);
		exit.row();
		yesButton = new TextButton(" YES ", skin);
		exit.add(yesButton).padBottom(20);
		noButton = new TextButton(" NO ", skin);
		exit.add(noButton).padBottom(20);
		exit.setPosition(150, -600);

	}

	private void howToPlayTable() {
		howToPlayTable = new Table();
		howToPlayTable.add(new Label("Collision with drams ", skin));
		howToPlayTable.row();
		howToPlayTable.add(new Label("causes damage.Damage", skin));
		howToPlayTable.row();
		howToPlayTable.add(new Label("level upper left", skin));
		howToPlayTable.row();
		howToPlayTable.add(new Label("corner.Eat Power up", skin));
		howToPlayTable.row();
		howToPlayTable.add(new Label("to speed up", skin));
		howToPlayTable.row();
		howToPlayTable.row();
		backButton2 = new TextButton(" BACK ", skin);
		howToPlayTable.add(backButton2).colspan(2).padTop(10).size(250, 60);

		howToPlayTable.setPosition(800, 400);
	}

	private void howToPlayScreen1() {

		
	
		
		
		
		Image dramImage = new Image(new TextureRegion(Assets.instance.dram));
		Image lorryImage = new Image(new TextureRegion(Assets.instance.lorry));
		Image powerUpImage = new Image(new TextureRegion(Assets.instance.powerUpAnimation.getKeyFrame(0)));
		Image lifeUpImage = new Image(new TextureRegion(Assets.instance.life));
		TextureRegion tt = new TextureRegion() ;
		
		tt = Assets.instance.fence;
		int x = Assets.instance.fence.getRegionX();
		int y = Assets.instance.fence.getRegionY();
		int width = Assets.instance.fence.getRegionWidth();
		tt.setRegion(x, y+10, width, 380);
		
		
		Image streetSide = new Image(new TextureRegion(tt));
		
		skin.add("menu_back_street", new Texture("menu_street.png"));
		
		
		howToPlayTab1Rule1 = new Table(skin);
		howToPlayTab2Rule1 = new Table(skin);
		howToPlayTab3Rule2 = new Table(skin);
		howToPlayTab4Rule2 = new Table(skin);
		
//		table.row();
		
		
		howToPlayTab1Rule1.columnDefaults(2);
		
		howToPlayTab1Rule1.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Rule2=>", skin,"default2")).padRight(50);
		
		howToPlayTab1Rule1.add();
		howToPlayTab1Rule1.add(dramImage).width(60).height(80).fill().padRight(10).padBottom(10).center();
		howToPlayTab1Rule1.add(lorryImage).width(60).height(180).fill().center().padLeft(20).center();
		howToPlayTab1Rule1.add(streetSide).width(15).height(180).fill().center().padLeft(30).center();
		howToPlayTab1Rule1.row();
		
		howToPlayTab2Rule1.columnDefaults(2);
//		table4.debug();
		howToPlayTab2Rule1.add(new com.badlogic.gdx.scenes.scene2d.ui.Label(" Any collision casuses damage", skin,"default3")).left().bottom().maxWidth(200);
		howToPlayTab2Rule1.row();
		howToPlayTab2Rule1.add(new com.badlogic.gdx.scenes.scene2d.ui.Label(" and point loss", skin,"default3")).align(Align.center).padTop(0);;
		howToPlayTab2Rule1.row();
	
		
		

		howToPlayTab3Rule2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Rule1=>", skin,"default2")).padLeft(0).padRight(0);
		howToPlayTab3Rule2.add();
		howToPlayTab3Rule2.row();
		howToPlayTab3Rule2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label(">", skin,"default2")).padRight(0);
		howToPlayTab3Rule2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Tilt right to move car right", skin,"default3")).left().maxWidth(200);
		howToPlayTab3Rule2.row();
		howToPlayTab3Rule2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("<", skin,"default2")).padRight(0);
		howToPlayTab3Rule2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Tilt left to move car left", skin,"default3")).left().maxWidth(200);
		
		
		
		
		tableHowToPlayMain = new Table(skin);
		
		tableHowToPlayMain.add(howToPlayTab3Rule2).padTop(20).row();
		tableHowToPlayMain.add(howToPlayTab1Rule1).padTop(35).row();
		tableHowToPlayMain.add(howToPlayTab2Rule1).padBottom(2).row();

		
//		tableHowToPlayMain.add(howToPlayTab4Rule2).row();
		
		Table buttonTable = new Table(skin);
		
		backFromHowToPlayS1Button = new TextButton(" BACK ",skin);
		backFromHowToPlayS1Button.setHeight(80);
		buttonTable.add(backFromHowToPlayS1Button);;
		
		
		nextFromHowToPlayS1Button = new TextButton(" NEXT ",skin);
		nextFromHowToPlayS1Button.setHeight(80);
		buttonTable.add(nextFromHowToPlayS1Button);
		
		
		tableHowToPlayMain.add(buttonTable).padTop(30);
		
		
		tableHowToPlayMain.setPosition(850, 400);
		stage.addActor(tableHowToPlayMain);
		

	}
		
	
	
	private void howToPlayScreen2() {

		
		Table howToPlayTab1Rule3;
		Table howToPlayTab2Rule3;
		Table howToPlayTab3Rule4;
		Table howToPlayTab4Rule4;
		
		
	
		Image powerUpImage = new Image(new TextureRegion(Assets.instance.powerUpAnimation.getKeyFrame(0)));
		Image lifeUpImage = new Image(new TextureRegion(Assets.instance.life));
		Image coinImage = new Image(new TextureRegion(Assets.instance.coinAnimation.getKeyFrame(0)));
		
		TextureRegion tt = new TextureRegion() ;
//		
		tt = Assets.instance.fence;
		int x = Assets.instance.fence.getRegionX();
		int y = Assets.instance.fence.getRegionY();
		int width = Assets.instance.fence.getRegionWidth();
		tt.setRegion(x, y+10, width,380);
		Image streetSide = new Image(new TextureRegion(tt));
		
		skin.add("menu_back_street", new Texture("menu_street.png"));
		
		
		howToPlayTab1Rule3 = new Table(skin);
		howToPlayTab2Rule3 = new Table(skin);
		howToPlayTab3Rule4 = new Table(skin);
		howToPlayTab4Rule4 = new Table(skin);
		
		
//		howToPlayTab3Rule4.debug();
		howToPlayTab3Rule4.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Rule2=>", skin,"default2")).padLeft(50).align(Align.left).row();
		howToPlayTab3Rule4.add(coinImage).width(60).height(60).fill().left().padLeft(90).padRight(10);
		howToPlayTab3Rule4.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("=> Collect at least 5", skin,"default3")).align(Align.center).padTop(0);;
		howToPlayTab3Rule4.row();
		howToPlayTab3Rule4.add();
		howToPlayTab3Rule4.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("coins to pass barricade", skin,"default3")).align(Align.center).padTop(0);;
		
		
		howToPlayTab4Rule4.row();
		howToPlayTab4Rule4.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Rule3=>", skin,"default2")).padTop(30).padLeft(0f).padBottom(20).align(Align.left).row();

		howToPlayTab4Rule4.add(powerUpImage).width(60).height(60).fill().left().padLeft(50).padRight(0);
		howToPlayTab4Rule4.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("=> Speed Up (5 points)", skin,"default3")).align(Align.left);
		howToPlayTab4Rule4.row();
		howToPlayTab4Rule4.add(lifeUpImage).width(40).height(40).fill().left().padLeft(50).padTop(5).padRight(0);
		howToPlayTab4Rule4.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("=> Recover Damage", skin,"default3")).align(Align.left);
		howToPlayTab4Rule4.row();
		howToPlayTab4Rule4.add();
		howToPlayTab4Rule4.row();
		howToPlayTab4Rule4.add();
		howToPlayTab4Rule4.row();
		
//		setHowToPlayTablePos(650 ,600);
		
		
		tableHowToPlayMain2 = new Table(skin);
		
		tableHowToPlayMain2.add(howToPlayTab1Rule3).row();
		tableHowToPlayMain2.add(howToPlayTab2Rule3).row();

		tableHowToPlayMain2.add(howToPlayTab3Rule4).row();
		tableHowToPlayMain2.add(howToPlayTab4Rule4).row();
		
		Table buttonTable1 = new Table(skin);
		backFromHowToPlayS1Button1 = new TextButton(" back ",skin);
		buttonTable1.add(backFromHowToPlayS1Button1);
		
		playFromHowToPlayS1Button1 = new TextButton(" Play ",skin);
		buttonTable1.add(playFromHowToPlayS1Button1);
		
		
		
		tableHowToPlayMain2.add(buttonTable1).padTop(60);
		tableHowToPlayMain2.setPosition(850, 400);
		stage.addActor(tableHowToPlayMain2);
		

	}
		
		
	
	

	private void optionMenu() {
		options = new Table();
		Label soundTitle = new Label("SOUND OPTIONS", skin);
		soundTitle.setColor(Color.NAVY);
		options.add(soundTitle).padBottom(25).colspan(3);
		options.row();
		muteCheckBox = new CheckBox(" MUTE ALL", skin);
		options.add(muteCheckBox).padBottom(10).colspan(3);
		options.row();
		options.add(new Label("VOLUME ", skin)).padBottom(10).padRight(10);
		volumeSlider = new Slider(0, 2, 0.2f, false, skin);
		options.add(volumeSlider).padTop(10).padBottom(20).size(250, 60);
		options.row();

		backButton = new TextButton("BACK", skin);
		options.add(backButton).colspan(2).padTop(20).size(250, 60);
		options.setPosition(450, -200);

		muteCheckBox.setChecked(!game.soundEnabled);
		volumeSlider.setValue(game.soundVolume);
		volumeSlider.setSize(250, 60);
	}

	private void mainMenuTable() {
		table = new Table();

		playButton = new TextButton(" PLAY ", skin);
		table.add(playButton).size(270, 70);
		table.row();
		
		showHowToPlayButton = new TextButton(" HOW TO PLAY ", skin);
		table.add(showHowToPlayButton).size(270, 70);
		table.row();
//		optionsButton = new TextButton("OPTIONS", skin);
//		table.add(optionsButton).size(250, 70);
		table.row();
		// table.add(new TextButton("LEADERBOARD", skin)).size(250, 60);
		// table.row();
		showScoreBoardButton = new TextButton(" SCORE BOARD ", skin);
		table.add(showScoreBoardButton).size(270, 70);
		table.row();
		
		aboutButton = new TextButton(" ABOUT ", skin);
		table.add(aboutButton).size(270, 70);
		table.row();
		
		exitButton = new TextButton(" EXIT ", skin);
		
		table.add(exitButton).size(270, 70);
		
		table.setPosition(450, -200);

	}

	private void aboutTable() {
		aboutTable = new Table();

//		aboutTable.add(new Label("Yeah ,I know,not good.", skin, "default3"));
//		aboutTable.row();
		aboutTable.add(new Label("This is my first game!!", skin,
				"default3"));
		aboutTable.row();
		aboutTable.add(new Label("sorry if it bored u  :(", skin, "default3"));
		aboutTable.row();
		aboutTable.add(new Label(" ", skin, "default3"));
		aboutTable.row();
		aboutTable.add(new Label(" Get me at g+ ", skin, "default3"));
		aboutTable.row();
		aboutTable.add(new Label("razin.meha@gmail.com", skin, "default3"));
		aboutTable.row();
		aboutTable.add(new Label(" ", skin, "default3"));

		aboutTable.row();
		aboutTable.add(new Label(" ", skin, "default3"));
		aboutTable.row();
		aboutTable.add(new Label("'just love what you do'", skin, "default3"));
		aboutTable.row();


		aboutTable.row();
		aboutTable.add(new Label(" ", skin, "default3"));
		aboutTable.row();
		backButton3 = new TextButton(" BACK ", skin);
		aboutTable.add(backButton3);

		aboutTable.setPosition(-800, -300);

	}

	private void showHowToPlay(boolean flag) {
		MoveToAction actionMove1 = Actions.action(MoveToAction.class);// out
		actionMove1.setPosition(850, 330);
		actionMove1.setDuration(1);
		actionMove1.setInterpolation(Interpolation.swingIn);

		MoveToAction actionMove2 = Actions.action(MoveToAction.class);// in
		actionMove2.setPosition(270, 330);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);

		if (flag) {
			table.addAction(actionMove2);
			tableHowToPlayMain.addAction(actionMove1);
		} else {
			tableHowToPlayMain.addAction(actionMove2);
			table.addAction(actionMove1);
		}
		menuShown = flag;
		exitShown = false;
	}
	private void showHowToPlay2(boolean flag) {
		MoveToAction actionMove1 = Actions.action(MoveToAction.class);// out
		actionMove1.setPosition(850, 330);
		actionMove1.setDuration(1);
		actionMove1.setInterpolation(Interpolation.swingIn);

		MoveToAction actionMove2 = Actions.action(MoveToAction.class);// in
		actionMove2.setPosition(270, 330);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);

		if (flag) {
			tableHowToPlayMain.addAction(actionMove2);
			tableHowToPlayMain2.addAction(actionMove1);
		} else {
			tableHowToPlayMain2.addAction(actionMove2);
			tableHowToPlayMain.addAction(actionMove1);
		}
		menuShown = flag;
		exitShown = false;
	}


	private void howToPlayTableAddAction(MoveToAction actionMove1) {
		howToPlayTab1Rule1.addAction(actionMove1);
		howToPlayTab2Rule1.addAction(actionMove1);
		howToPlayTab3Rule2.addAction(actionMove1);
		howToPlayTab4Rule2.addAction(actionMove1);
		
	}

	@Override
	public void show() {
		title.setPosition(-30, -30);
		// helpTip.setPosition(0,0);

		MoveToAction actionMove = Actions.action(MoveToAction.class);
		actionMove.setPosition(112, Constants.BACK_HEIGHT - 130);

		actionMove.setDuration(2);
		actionMove.setInterpolation(Interpolation.elasticOut);
		title.addAction(actionMove);

		menuSoundPlayTime = System.currentTimeMillis();
		menuSoundPlayed = false;

		showMenu(true);
	}

	private void showMenu(boolean flag) {
		MoveToAction actionMove1 = Actions.action(MoveToAction.class);// out
		actionMove1.setPosition(450, -200);
		actionMove1.setDuration(1);
		actionMove1.setInterpolation(Interpolation.swingIn);

		MoveToAction actionMove2 = Actions.action(MoveToAction.class);// in
		actionMove2.setPosition(270, 340);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);

		if (flag) {
			table.addAction(actionMove2);
			options.addAction(actionMove1);
		} else {
			options.addAction(actionMove2);
			table.addAction(actionMove1);
		}
		menuShown = flag;
		exitShown = false;
	}

	private void showExit2(boolean flag) {
		MoveToAction actionMove1 = Actions.action(MoveToAction.class);// out
		actionMove1.setPosition(110, -600);
		actionMove1.setDuration(1);
		actionMove1.setInterpolation(Interpolation.swingIn);

		MoveToAction actionMove2 = Actions.action(MoveToAction.class);// in
		actionMove2.setPosition(270, 320);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);

		if (flag) {
			exit.addAction(actionMove2);
			table.addAction(actionMove1);
		} else {
			table.addAction(actionMove2);
			exit.addAction(actionMove1);
		}
	
	}

	private void showAbout(boolean flag) {
		MoveToAction actionMove1 = Actions.action(MoveToAction.class);// out
		actionMove1.setPosition(110, -300);
		actionMove1.setDuration(1);
		actionMove1.setInterpolation(Interpolation.swingIn);

		MoveToAction actionMove2 = Actions.action(MoveToAction.class);// in
		actionMove2.setPosition(270, 320);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);

		if (flag) {
			aboutTable.addAction(actionMove2);
			table.addAction(actionMove1);
		} else {
			table.addAction(actionMove2);
			aboutTable.addAction(actionMove1);
		}


	}
	
	
	private void showScoreBoard(boolean flag) {
		MoveToAction actionMove1 = Actions.action(MoveToAction.class);// out
		actionMove1.setPosition(-280, -550);
		actionMove1.setDuration(1);
		actionMove1.setInterpolation(Interpolation.swingIn);

		MoveToAction actionMove2 = Actions.action(MoveToAction.class);// in
		actionMove2.setPosition(270, 320);
		actionMove2.setDuration(1.5f);
		actionMove2.setInterpolation(Interpolation.swing);

		if (flag) {
			scoreBoardTableMain.addAction(actionMove2);
			table.addAction(actionMove1);
		} else {
			table.addAction(actionMove2);
			scoreBoardTableMain.addAction(actionMove1);
		}


	}
	
	private void createScoreboardTable() {
		
		
		long HighScore = getHighScore();
		String HighScorerName = getHighScorerName();
		
		scoreBoardTableMain = new Table(skin);
		
		backFromScoreBoard = new TextButton(" back ", skin);
		Table table2 ;
		table2 = new Table(skin);
		
		Table table1 ;
		table1 = new Table(skin);
		table1.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("Highest Score", skin,"score_label")).align(Align.center).padRight(30).padBottom(100);
		
		
		
		table2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("NAME", skin,"default2")).align(Align.center).padRight(30);
		table2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("SCORE", skin,"default2")).align(Align.center).padLeft(30);
		table2.row().padBottom(50);
		table2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label(HighScorerName, skin,"default2")).align(Align.center).padRight(30).padTop(50);
		table2.add(new com.badlogic.gdx.scenes.scene2d.ui.Label(""+HighScore, skin,"default3")).align(Align.center).padRight(30).pad(50);
		
		
//		table1.setPosition(260 , 600);
//		table2.setPosition(260, 400);
		scoreBoardTableMain.add();
		scoreBoardTableMain.row();
		scoreBoardTableMain.add();
		scoreBoardTableMain.row();
		scoreBoardTableMain.add(table1).row();
		scoreBoardTableMain.add();
		scoreBoardTableMain.row();
		scoreBoardTableMain.add();
		scoreBoardTableMain.row();
		scoreBoardTableMain.add(table2);
		scoreBoardTableMain.add();
		scoreBoardTableMain.row();
		scoreBoardTableMain.add();
		scoreBoardTableMain.row();
		scoreBoardTableMain.add(backFromScoreBoard);
		
		//280, 450
		scoreBoardTableMain.setPosition(-280, -550);
	}
	

	
	private String getHighScorerName() {
		return (GamePreferences.instance.highstScorerName);
	}




	private long getHighScore() {
		
		return ((long)GamePreferences.instance.highstScore);
	}

	
	
	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Show the loading screen
		

		
		batch.begin();
		batch.draw(skin.getRegion("menu_back_street"), 0, 0, 0, 0, screenwidth,
				screenHeight, 1, 1, 0);
		batch.end();

		stage.act();
		stage.draw();

		if (!menuSoundPlayed) {
			Assets.instance.menuSound.play();
			menuSoundPlayed = true;
		}

		if (System.currentTimeMillis() - menuSoundPlayTime > 4800) {
			Assets.instance.menuSound.play();
			menuSoundPlayed = true;
			menuSoundPlayTime = System.currentTimeMillis();
		}

		
		
		// Table.drawDebug(stage);
		super.render(delta);
	}
	
	

//	@Override
//	protected void handleBackPress() {
////		if (!menuShown) {
////			showMenu(!menuShown);
////		} else {
////			showExit(!exitShown);
////		}
//		
////		showExit2(true);
//		
//	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	private void getScreen() {
		screenHeight = Gdx.graphics.getHeight();

		screenwidth = Gdx.graphics.getWidth();
	}

}
