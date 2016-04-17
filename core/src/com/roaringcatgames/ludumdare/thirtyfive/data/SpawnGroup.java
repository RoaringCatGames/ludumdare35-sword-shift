package com.roaringcatgames.ludumdare.thirtyfive.data;

import com.badlogic.gdx.utils.Array;
import com.roaringcatgames.kitten2d.ashley.K2MathUtil;
import com.roaringcatgames.ludumdare.thirtyfive.components.AuraType;

/**
 * Created by barry on 4/17/16 @ 10:36 AM.
 */
public class SpawnGroup {

    private static Array<SpawnBatch> spawns;

    public static Array<SpawnBatch> getSpawns(){
        if(spawns == null){
            spawns = new Array<>();
            spawns.add(generateBatch(45f, 0f, EnemyType.RAT, Formation.THREE_EVEN));
            spawns.add(generateBatch(55f, 0f, EnemyType.RAT, Formation.STAR));
            spawns.add(generateBatch(65f, 0f, EnemyType.RAT, Formation.PYRAMID));
            spawns.add(generateBatch(85f, 0f, EnemyType.BEAR, Formation.THREE_EVEN));
            spawns.add(generateBatch(100f, 0f, EnemyType.BEAR, Formation.STAR));
            spawns.add(generateBatch(45f, 0f, EnemyType.BEAR, Formation.PYRAMID));
            spawns.add(generateBatch(45f, 0f, EnemyType.TROLL, Formation.THREE_EVEN));
        }

        return spawns;
    }


    private enum Formation{
        /**
         *     *
         *
         *     *
         *
         *     *
         */
        THREE_EVEN,
        /**
         *   *     *
         *
         *      *
         *
         *   *     *
         */
        STAR,
        /**
         *    *
         *       *
         *    *     *
         *       *
         *    *
         */
        PYRAMID
    }

    private static SpawnBatch generateBatch(float xPos, float baseXOffset, EnemyType eType, Formation formation){

        SpawnBatch batch = new SpawnBatch(xPos);
        switch(formation){
            case THREE_EVEN:
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 7.5f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 3.75f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 11.25f));
                break;
            case STAR:
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 3.75f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 11.25f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset + 2f, 7.5f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset + 4f, 3.75f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset + 4f, 11.25f));
                break;
            case PYRAMID:
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 7.5f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 3.75f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset, 11.25f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset + 2f, 10f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset + 2f, 5f));
                batch.enemyDefs.add(new EnemyDefinition(eType, getRandomAuraType(), baseXOffset + 4f, 7.5f));
                break;
        }

        return batch;
    }

    private static AuraType getRandomAuraType(){
        return K2MathUtil.getRandomInRange(0f, 1f) > 0.5f ?
                AuraType.PURPLE :
                AuraType.YELLOW;
    }
}
