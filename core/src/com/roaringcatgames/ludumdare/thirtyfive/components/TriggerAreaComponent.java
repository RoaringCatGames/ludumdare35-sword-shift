package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by barry on 4/18/16 @ 6:22 PM.
 */
public class TriggerAreaComponent implements Component, Pool.Poolable {

    public Vector3 offset = new Vector3(0f, 0f, 0f);
    public Rectangle triggerBox = new Rectangle(0f, 0f, 0f, 0f);

    public static TriggerAreaComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(TriggerAreaComponent.class);
        }else{
            return new TriggerAreaComponent();
        }
    }

    public TriggerAreaComponent setOffset(float x, float y, float z){
        this.offset.set(x, y, z);
        return this;
    }
    public TriggerAreaComponent setOffset(float x, float y){
        this.offset.set(x, y, this.offset.z);
        return this;
    }

    public TriggerAreaComponent setTriggerBox(float width, float height){
        this.triggerBox.setWidth(width);
        this.triggerBox.setHeight(height);
        return this;
    }

    @Override
    public void reset() {
        this.offset.set(0f, 0f, 0f);
        this.triggerBox.set(0f, 0f, 0f, 0f);
    }
}
