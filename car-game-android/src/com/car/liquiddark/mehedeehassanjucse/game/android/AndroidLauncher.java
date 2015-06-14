package com.car.liquiddark.mehedeehassanjucse.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.race.game.main.RaceGameMain;
import com.race.game.screen.RaceGameScreen;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass =true;
		config.useWakelock = true;
		
		initialize(new RaceGameMain(), config);
	}
}
