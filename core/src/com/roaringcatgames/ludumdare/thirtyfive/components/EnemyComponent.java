package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.roaringcatgames.ludumdare.thirtyfive.data.EnemyType;

/**
 * Created by barry on 4/17/16 @ 11:31 AM.
 */
public class EnemyComponent implements Component, Pool.Poolable {
    public EnemyType enemyType = EnemyType.RAT;
    //public AuraType auraType

    @Override
    public void reset() {

    }
}
