package com.roaringcatgames.ludumdare.thirtyfive.data;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by barry on 4/17/16 @ 10:57 AM.
 */
public class EnemyDefinition {

    public Vector2 initialOffset = new Vector2(0f, 0f);
    public EnemyType enemyType;
    //public AuraType auraType;
    //Attack Mode??

    public EnemyDefinition(EnemyType eType, float xOff, float yOff){
        this.enemyType = eType;
        this.initialOffset.set(xOff, yOff);
    }
}
