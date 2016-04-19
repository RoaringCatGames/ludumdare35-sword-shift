package com.roaringcatgames.ludumdare.thirtyfive.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.kitten2d.ashley.components.TextureComponent;
import com.roaringcatgames.kitten2d.ashley.components.TransformComponent;
import com.roaringcatgames.ludumdare.thirtyfive.App;
import com.roaringcatgames.ludumdare.thirtyfive.Assets;
import com.roaringcatgames.ludumdare.thirtyfive.Z;
import com.roaringcatgames.ludumdare.thirtyfive.components.BackgroundComponent;

/**
 * Created by barry on 4/17/16 @ 12:40 PM.
 */
public class BackgroundSystem extends IteratingSystem {

    private boolean isInitialized = false;

    public BackgroundSystem(){
        super(Family.all(BackgroundComponent.class).get());
    }

    private void init(){

        PooledEngine engine = (PooledEngine)getEngine();
        for(int i = 0;i<App.TileCount + 1;i++) {
            float x = (App.W / 2f) + (i*App.W);
            Entity walkpath = engine.createEntity();
            walkpath.add(TextureComponent.create(engine)
                    .setRegion(Assets.getWalkpath()));
            walkpath.add(TransformComponent.create(engine)
                    .setPosition(x, 5.5f, Z.walkpath));
            engine.addEntity(walkpath);

            Entity mountain = engine.createEntity();
            mountain.add(TextureComponent.create(engine)
                    .setRegion(Assets.getMountain()));
            mountain.add(TransformComponent.create(engine)
                    .setPosition(x, 20f, Z.mountain));
            engine.addEntity(mountain);

            Entity grassback1 = engine.createEntity();
            grassback1.add(TextureComponent.create(engine)
                    .setRegion(Assets.getGrassBack1()));
            grassback1.add(TransformComponent.create(engine)
                    .setPosition(x, 11.8f, Z.grassback1));
            engine.addEntity(grassback1);

            Entity grassback2 = engine.createEntity();
            grassback2.add(TextureComponent.create(engine)
                    .setRegion(Assets.getGrassBack2()));
            grassback2.add(TransformComponent.create(engine)
                    .setPosition(x, 14f, Z.grassback2));
            engine.addEntity(grassback2);

            Entity grassfront1 = engine.createEntity();
            grassfront1.add(TextureComponent.create(engine)
                    .setRegion(Assets.getGrassFront1()));
            grassfront1.add(TransformComponent.create(engine)
                    .setPosition(x, 0.9f, Z.grassfront1));
            engine.addEntity(grassfront1);

            Entity grassfront2 = engine.createEntity();
            grassfront2.add(TextureComponent.create(engine)
                    .setRegion(Assets.getGrassFront2()));
            grassfront2.add(TransformComponent.create(engine)
                    .setPosition(x, 1.7f, Z.grassfront2));
            engine.addEntity(grassfront2);


            for(int j=0;j<8;j++){
                float xAdjust = K2MathUtil.getRandomInRange(0f, 40f);
                float yAdjust = K2MathUtil.getRandomInRange(1f, 12f);
                float k2 = K2MathUtil.getRandomInRange(0f, 7f);

                TextureAtlas.AtlasRegion r = k2 < 1f ? Assets.getRock(1) :
                                             k2 < 2f ? Assets.getRock(2) :
                                             k2 < 3f ? Assets.getRock(3) :
                                             k2 < 4f ? Assets.getRock(4) :
                                             k2 < 5f ? Assets.getRock(5) :
                                             k2 < 6f ? Assets.getRock(6) :
                                                       Assets.getRock(7);
                Entity e = engine.createEntity();
                e.add(TextureComponent.create(engine)
                    .setRegion(r));
                e.add(TransformComponent.create(engine)
                    .setPosition(x + xAdjust, yAdjust, Z.rocks));
                engine.addEntity(e);
            }

            if(i == 2){
                Entity river = engine.createEntity();
                river.add(TextureComponent.create(engine)
                        .setRegion(Assets.getRiver()));
                river.add(TransformComponent.create(engine)
                        .setPosition(x, 9.5f, Z.river));
                engine.addEntity(river);

                Entity bridge = engine.createEntity();
                bridge.add(TextureComponent.create(engine)
                        .setRegion(Assets.getBridge()));
                bridge.add(TransformComponent.create(engine)
                        .setPosition(x, 8.5f, Z.bridge));
                engine.addEntity(bridge);
            }
        }
        isInitialized = true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(!isInitialized){
            init();
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
