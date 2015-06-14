package com.race.game.util;

import com.badlogic.gdx.math.MathUtils;

public class Constants {
	private Constants() {
	}

	public static final String race_asset = "race_asset20.pack";

	public static final float BACK_WIDTH = 532;
	public static final float BACK_HEIGHT = 732;
	public static final float new_fence_height = 380;
	public static final float BACK_WIDTH2 = 532;
	public static final float BACK_HEIGHT2 = 732;
	public static final float MAIN_STREET_LEFTX = 110;
	public static final float MAIN_STREET_RIGHTX = 622;
	public static final float back_height_half = BACK_HEIGHT2 / 2;
	public static final float back_width_half = 532 / 2;
	public static final String PREFERENCES = "car_game.prefs";
	public static final float ZERO = 0;

	// =========
	// DEBUG
	// =========
	public static final boolean debbugRectangleCollision = false;

	// ===end===

	// ========================
	// CAR
	// ========================
	public static final float CAR_HEIGHT = 63;
	public static final float CAR_WIDTH = 88;
	public static final float CAR_HEIGHT_rect = 50;
	public static final float CAR_WIDTH_rect = 88;
	public static final float max_car_left = 148;
	public static final float max_car_right = 445;
	public static final float car_rectangle_x = 200;
	public static final float car_rectangle_y = 100;
	public static final float car_total_life = 100;

	// ==========end=============

	// ========================
	// SPEED
	// ========================

	public static final float normal_speed = 400;
	public static final float power_up_speed = 600;
	public static final float token_speed = 20;
	public static final float dram_normal_speed = 250;
	public static final float lorry_normal_speed = 150;
	public static final float power_up_normal_speed = 150;
	public static final float life_up_normal_speed = 400f;
	public static final float coin_normal_speed = 370;
	public static final float bar_normal_speed = 210;

	// =====end==================

	// ========================
	// DRAMS
	// ========================

	public static final float dram_height = 50;
	public static final float dram_width = 60;
	public static final float dram_posy = 300;
	public static final float dram_posx = 300;
	public static final float power_up_draw_width = 60;
	public static final float power_up_draw_height = 60;
	public static final int pre_loaded_dram_size = 7;
	public static final int pre_loaded_dram_max_index = pre_loaded_dram_size - 1;
	public static final float damage_progress_multi = 2;
	public static final int dramCollisionDamageUnit = 4;

	// =========end============

	// ========================
	// LORRY
	// ========================
	public static final float lorry_posy = 400;
	public static final float lorry_posx = 400;
	public static final int pre_loaded_lorry_size = 2;
	public static final int pre_loaded_lorry_max_index = pre_loaded_lorry_size - 1;
	public static final float lorry_height = 60;
	public static final float lorry_width = 200;
	public static final int lorryCollisionDamageMulti = 1;
	public static final float do_not_print_lorry_in_middle_min = back_width_half - 30;
	public static final float do_not_print_lorry_in_middle_max = back_width_half + 30;
	public static final int lorryCollisionDamage = 0;

	// =========end============

	// ========================
	// POWER UP
	// ========================
	public static final float powerUpCounterDecBy = 20;
	public static final float power_pos_x_min = back_width_half - 50;
	public static final float power_pos_x_max = back_width_half + 50;
	public static final float power_up_width = 30 + 30;
	public static final float power_up_height = 30 + 30;
	public static final float power_up_total_time = 100;
	public static final float power_up_total_time_draw_factor = 0.01f;
	// =========end============

	//=========================
	// COIN
	//=========================
	public static final float coinCointer = 0;
	public static final float coin_x_min = back_width_half - 150;
	public static final float coin_x_max = back_width_half + 130;
	public static final float coin_width = 40;
	public static final float coin_height = 45;
	public static final float coin_total_time = 100;
	public static final float coin_total_time_draw_factor = 0.01f;
	//=========================
	
	// ======================
	// LIFE UP
	// ======================
	public static final float life_up_pos_x_min = power_pos_x_min;
	public static final float life_up_pos_x_max = power_pos_x_max;
	public static final float life_up_widht = 60;
	public static final float life_up_height = 60;

	// =========end============

	// ========================
	// PROGRESS BAR
	// ========================
	public static final float progress_bar_outline_width = 10;
	public static final float progress_bar_outline_height = 100;
	public static final float progress_bar_inside_width = progress_bar_outline_width - 2;
	public static final float progress_icon_power_up_width = 20;
	public static final float progress_icon_power_up_height = 20;
	// =========end============

	// ========================
	// FINISH
	// ========================
	public static final long game_finish_time_mili = 2000;
	public static final int finish_street_width = 350;
	public static final int finish_street_height = 100;

	// ==========end===========

	// ========================
	// FENCE
	// ========================
	public static final int fenceCollisionDamageMulti = 1;
	public static final int fenceCollisionDamage = 0;
	public static final float fence_left_rectangle_posx = 136;
	public static final float fence_right_rectangle_posx = 490;
	public static final float fence_left_x = 83;
	public static final float fence_left_y = 0;
	public static final float fence_right_x = 445;
	public static final float fence_right_y = 0;
	public static final float fence_height = BACK_HEIGHT;
	public static final float fence_width_left = 15;
	public static final float fence_width_right = 14;

	// =========end============

	// ========================
	// TIMES
	// ========================

	public static final long dram_interval_time = 460;
	public static final long lorry_interval_time = 2600;
	public static final long coin_interval_time = 3500;
	public static final long bar_interval_time = MathUtils.random(19000,22000);
	public static final long life_up_interval_time = 33000;
	public static final long power_up_interval_time = 29000;
	
	// =========end============

	// ========================
	// MUSIC&SOUND
	// ========================

	public static final float carRunningMusicVolume = 0.7f;
	public static final float sideCollisionSoundVolume = 0.8f;
	public static final float criticSoundVolume = 0.1f;
	public static final float dram_explo_sound_volume = 0.9f;
	public static final float game_over_sound_volume = 0.5f;
	// =========end============

	
//	BAR
	public static final float bar_x_min = fence_left_x + 30;
	public static final int bar_width = 344;
	public static final int bar_height = 30;


}
