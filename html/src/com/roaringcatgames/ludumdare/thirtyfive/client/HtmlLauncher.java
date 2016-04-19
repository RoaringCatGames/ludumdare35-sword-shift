package com.roaringcatgames.ludumdare.thirtyfive.client;

import com.badlogic.gdx.backends.gwt.GwtApplication;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.roaringcatgames.ludumdare.thirtyfive.SwordShiftGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(960, 720);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new SwordShiftGame();
        }
}