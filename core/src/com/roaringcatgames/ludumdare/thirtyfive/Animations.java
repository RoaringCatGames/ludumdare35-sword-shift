package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * All of our animations can be initialized here so we can prevent
 * initializing memory during the game.
 */
public class Animations {

    private static Animation testAnimation;
    private static Animation loading;
    private static Animation dagger;

    private static Animation ratWalking;
    private static Animation ratAttacking;
    private static Animation ratDying;
    private static Animation bearWalking;
    private static Animation bearAttacking;
    private static Animation bearDying;

    private static Animation trollWalking;
    private static Animation trollAttacking;
    private static Animation trollDying;


    public static void init(){
        testAnimation = new Animation(1f/6f, Assets.getTestFrames());

        ratWalking = new Animation(1f/13f, Assets.getRatWalkingRegions());
        ratAttacking = new Animation(1f/6f, Assets.getTestFrames());
        ratDying = new Animation(1f/6f, Assets.getTestFrames());

        bearWalking = new Animation(1f/6f, Assets.getTestFrames());
        bearAttacking = new Animation(1f/6f, Assets.getTestFrames());
        bearDying = new Animation(1f/6f, Assets.getTestFrames());

        trollWalking = new Animation(1f/6f, Assets.getTestFrames());
        trollAttacking = new Animation(1f/6f, Assets.getTestFrames());
        trollDying = new Animation(1f/6f, Assets.getTestFrames());

        loading = new Animation(1f / 16f, Assets.getLoadingFrames());
        dagger = new Animation(1f / 6f, Assets.getDaggerIdleFrames());
    }


    public static Animation getTestAnimation(){
        return testAnimation;
    }
    public static Animation getLoadingAnimation(){
        return loading;
    }
    public static Animation getDaggerIdleAnimation(){
        return dagger;
    }

    public static Animation getRatWalking(){
        return ratWalking;
    }
    public static Animation getRatAttacking(){
        return ratAttacking;
    }
    public static Animation getRatDying(){
        return ratDying ;
    }

    public static Animation getBearWalking(){
        return bearWalking;
    }
    public static Animation getBearAttacking(){
        return bearAttacking;
    }
    public static Animation getBearDying(){
        return bearDying ;
    }

    public static Animation getTrollWalking(){
        return trollWalking;
    }
    public static Animation getTrollAttacking(){
        return trollAttacking;
    }
    public static Animation getTrollDying(){
        return trollDying ;
    }



}
