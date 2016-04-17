package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by rexsoriano on 4/16/16.
 */
public class PlayerComponent implements Component,Pool.Poolable{

    public AuraType transformType = AuraType.PURPLE;
    public int energyLevel = 0;

    public static PlayerComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(PlayerComponent.class);
        }else {
            return new PlayerComponent();
        }
    }

    @Override
    public void reset() {

    }
}
