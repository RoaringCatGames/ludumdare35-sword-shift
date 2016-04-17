package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;
import com.roaringcatgames.kitten2d.ashley.components.AnimationComponent;

/**
 * Created by rexsoriano on 4/16/16.
 */
public class PlayerComponent implements Component,Pool.Poolable{

    public AuraType transformType = AuraType.PURPLE;
    public int energyLevel = 0;

    public static AnimationComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(AnimationComponent.class);
        }else {
            return new AnimationComponent();
        }
    }

    @Override
    public void reset() {

    }
}
