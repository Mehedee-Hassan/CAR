package com.race.game.util;

import com.badlogic.gdx.Input.TextInputListener;

public class InputText implements TextInputListener {
		   @Override
		   public void input (String text) {
			   String name;
			   
			   if(text.length() > 10)
				   name = text.substring(0, 10);
			   else 
				   name = text;
			   GamePreferences.instance.highstScorerName=name;
		   }

		   @Override
		   public void canceled () {
			   GamePreferences.instance.highstScorerName= "";
		   }
	
}
