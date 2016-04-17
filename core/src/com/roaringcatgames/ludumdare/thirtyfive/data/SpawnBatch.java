package com.roaringcatgames.ludumdare.thirtyfive.data;

import com.badlogic.gdx.utils.Array;

/**
 * Created by barry on 4/17/16 @ 10:36 AM.
 */
public class SpawnBatch {
    public float triggerXPosition;
    public Array<EnemyDefinition> enemyDefs = new Array<>();

    public SpawnBatch(float triggerPosition){
        this.triggerXPosition = triggerPosition;
    }
}
