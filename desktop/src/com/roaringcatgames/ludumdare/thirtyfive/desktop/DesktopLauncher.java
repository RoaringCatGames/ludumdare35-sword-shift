package com.roaringcatgames.ludumdare.thirtyfive.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.roaringcatgames.ludumdare.thirtyfive.App;
import com.roaringcatgames.ludumdare.thirtyfive.SwordShiftGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Chopping Spree";
		config.width = App.PixelW;
		config.height = App.PixelH;
		new LwjglApplication(new SwordShiftGame(), config);
	}
}
