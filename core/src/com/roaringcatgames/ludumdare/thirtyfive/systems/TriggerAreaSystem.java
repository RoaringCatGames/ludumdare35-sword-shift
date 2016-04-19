package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.ludumdare.thirtyfive.components.TriggerAreaComponent;

/**
 * Created by barry on 4/18/16 @ 6:30 PM.
 */
public class TriggerAreaSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<TriggerAreaComponent> tam;

    public TriggerAreaSystem(){
        super(Family.all(TransformComponent.class, TriggerAreaComponent.class).get());
        tm = ComponentMapper.getFor(TransformComponent.class);
        tam = ComponentMapper.getFor(TriggerAreaComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent tc = tm.get(entity);
        TriggerAreaComponent tac = tam.get(entity);

        float newX = tc.position.x + tac.offset.x;
        float newY = tc.position.y + tac.offset.y;

        tac.triggerBox.setPosition(newX, newY);
    }
}
