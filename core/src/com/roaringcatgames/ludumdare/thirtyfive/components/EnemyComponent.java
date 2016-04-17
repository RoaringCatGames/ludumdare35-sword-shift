package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import com.roaringcatgames.ludumdare.thirtyfive.data.EnemyType;

/**
 * Created by barry on 4/17/16 @ 11:31 AM.
 */
public class EnemyComponent implements Component, Pool.Poolable {
    public EnemyType enemyType = EnemyType.RAT;
    public AuraType auraType = AuraType.PURPLE;

    public static EnemyComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(EnemyComponent.class);
        }else{
            return new EnemyComponent();
        }
    }

    public EnemyComponent setAura(AuraType at){
        this.auraType = at;
        return this;
    }

    public EnemyComponent setEnemyType(EnemyType et){
        this.enemyType = et;
        return this;
    }
    @Override
    public void reset() {

    }
}
