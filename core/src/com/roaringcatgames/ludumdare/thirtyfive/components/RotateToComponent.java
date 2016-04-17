package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

/**
 * Created by rexsoriano on 4/17/16.
 */
public class RotateToComponent implements Component, Pool.Poolable {
    public Array<RotateTo> Rotations = new Array<>();

    public static RotateToComponent create(Engine engine){
        if(engine instanceof PooledEngine) {
            return ((PooledEngine)engine).createComponent(RotateToComponent.class);
        }else {
            return new RotateToComponent();
        }
    }

    public RotateToComponent addRotateTo(float target, float speed){
        this.Rotations.add(new RotateTo(target, speed));
        return this;
    }

    @Override
    public void reset() {

    }
}
