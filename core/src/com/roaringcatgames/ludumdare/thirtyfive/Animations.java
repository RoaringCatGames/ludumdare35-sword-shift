package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * All of our animations can be initialized here so we can prevent
 * initializing memory during the game.
 */
public class Animations {


    public static void init(){
        testAnimation = new Animation(1f/6f, Assets.getTestFrames());
    }

    private static Animation testAnimation;

    public static Animation getTestAnimation(){
        return testAnimation;
    }
}
