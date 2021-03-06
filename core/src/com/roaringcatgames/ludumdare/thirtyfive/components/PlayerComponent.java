package com.roaringcatgames.ludumdare.thirtyfive.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by rexsoriano on 4/16/16.
 */
public class PlayerComponent implements Component,Pool.Poolable{

    public AuraType auraType = AuraType.PURPLE;
    public boolean isAttacking = false;
    public int energyLevel = 0;
    public WeaponType weaponType = WeaponType.DAGGER;

    public static PlayerComponent create(Engine engine){
        if(engine instanceof PooledEngine){
            return ((PooledEngine)engine).createComponent(PlayerComponent.class);
        }else {
            return new PlayerComponent();
        }
    }

    @Override
    public void reset() {
        energyLevel = 0;
        isAttacking = false;
        auraType = AuraType.PURPLE;
        weaponType = WeaponType.DAGGER;

    }
}
