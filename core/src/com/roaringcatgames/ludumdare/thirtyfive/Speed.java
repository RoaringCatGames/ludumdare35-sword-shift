package com.roaringcatgames.ludumdare.thirtyfive;

import com.roaringcatgames.ludumdare.thirtyfive.data.EnemyType;

/**
 * Created by barry on 4/17/16 @ 5:07 PM.
 */
public class Speed {

    public static float rat = 3f;
    public static float bear = 2f;
    public static float troll = 1f;

    public static float getSpeed(EnemyType enemyType){
        float result = rat;
        switch(enemyType){
            case RAT:
                result = rat;
                break;
            case BEAR:
                result = bear;
                break;
            case TROLL:
                result = troll;
                break;
        }
        return result;
    }
}
