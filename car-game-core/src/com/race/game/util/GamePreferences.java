package com.race.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {

	public static final String TAG = GamePreferences.class.getName();

	public static final GamePreferences instance = new GamePreferences();

	public long highstScore = 0;

	private Preferences prefs;
	public int firstTimeAppRun = 0;
	public String highstScorerName;

	// public String highScorerName ="MHR";

	private GamePreferences() {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);

		load();
	}

	
	
	
	int i = 0;

	public void saveFirstTimeOpend() {

		
		load();
		firstTimeAppRun ++;
			
		prefs.putInteger("FirstTime", (firstTimeAppRun));
		prefs.flush();
		
	}

	public void saveScore(long main_score, String name) {
		this.highstScorerName = name;
		this.highstScore = main_score;

		save();

	}

	public void load() {
		highstScore = prefs.getLong("SCORE");

		this.highstScorerName = prefs.getString("highstScorerName");
		if (prefs.contains("FirstTime"))
			this.firstTimeAppRun = prefs.getInteger("FirstTime");
	}

	public void save() {
		prefs.putLong("SCORE", this.highstScore);
		prefs.putString("highstScorerName", this.highstScorerName);
		prefs.flush();
	}

	public void saveScore(long main_score) {
		this.highstScore = main_score;
		Gdx.input.getTextInput(new InputText(), "New High Score", "", "Name");

		save();

	}


	public int firstTimeAppRunRet() {
		load();
		
		return firstTimeAppRun;
	}

}
