package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.ludumdare.thirtyfive.components.PlayerComponent;

/**
 * Created by rexsoriano on 4/17/16.
 */
public class RotateToSystem extends IteratingSystem {


    public RotateToSystem(){
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }


}
