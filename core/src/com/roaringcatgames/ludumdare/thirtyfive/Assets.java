package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by barry on 4/16/16 @ 12:04 PM.
 */
public class Assets {

    public static AssetManager am;

    public static AssetManager load(){
        am = new AssetManager();

        am.load(LOADING_ATLAS, TEXTURE_ATLAS);
        am.finishLoading();
        am.load(ANI_ATLAS, TEXTURE_ATLAS);
        am.load(SPRITE_ATLAS, TEXTURE_ATLAS);

        am.load(DAGGER_HIT_SFX, SOUND);
        am.load(HATCHET_HIT_SFX, SOUND);
        am.load(KATANA_HIT_SFX, SOUND);
        am.load(BUSTER_HIT_SFX, SOUND);

        am.load(BEAR_ENEMY_SFX, SOUND);
        am.load(RAT_ENEMY_SFX, SOUND);
        am.load(TROLL_ENEMY_SFX, SOUND);

        am.load(COURIER_FONT_32, BITMAP_FONT);

        return am;
    }

    public static Array<TextureAtlas.AtlasRegion> getLoadingFrames(){
        return am.get(LOADING_ATLAS, TEXTURE_ATLAS).findRegions("loading");
    }

    public static TextureRegion getBadGuyTexture(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("badlogic");
    }

    public static Array<TextureAtlas.AtlasRegion> getTestFrames(){
        return am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions("weapon/dagger-idle");
    }

    public static Array<TextureAtlas.AtlasRegion> getDaggerIdleFrames(){
        return am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions("dagger-idle");
    }

    private static Array<TextureAtlas.AtlasRegion> purpleParticles = new Array<>();
    private static Array<TextureAtlas.AtlasRegion> yellowParticles = new Array<>();
    public static Array<TextureAtlas.AtlasRegion> getYellowParticles(){
        if(purpleParticles.size < 1) {
            purpleParticles.add(am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("particles/aura-yellow"));
        }
        return purpleParticles;
    }
    public static Array<TextureAtlas.AtlasRegion> getPurpleParticles(){
        if(yellowParticles.size < 1) {
            yellowParticles.add(am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("particles/aura-purple"));
        }
        return yellowParticles;
    }

    public static TextureAtlas.AtlasRegion getGrassBack1(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("background/grassback1");
    }
    public static TextureAtlas.AtlasRegion getGrassBack2(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("background/grassback2");
    }
    public static TextureAtlas.AtlasRegion getGrassFront1(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("background/grassfront1");
    }
    public static TextureAtlas.AtlasRegion getGrassFront2(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("background/grassfront2");
    }
    public static TextureAtlas.AtlasRegion getMountain(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("background/mountain");
    }
    public static TextureAtlas.AtlasRegion getWalkpath(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("background/walkpath");
    }


    public static Array<TextureAtlas.AtlasRegion> getRatWalkingRegions(){
        return am.get(ANI_ATLAS, TEXTURE_ATLAS).findRegions("enemies/rat-walk");
    }

    public static BitmapFont getFont32(){
        return am.get(COURIER_FONT_32, BITMAP_FONT);
    }




    private static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    private static Class<Music> MUSIC = Music.class;
    private static Class<BitmapFont> BITMAP_FONT = BitmapFont.class;
    private static Class<Sound> SOUND = Sound.class;

    /////////////////
    //SFX
    /////////////////
    public static Sound getDaggerSfx(){
        return am.get(DAGGER_HIT_SFX);
    }

    public static Sound getHatchetSfx(){
        return am.get(HATCHET_HIT_SFX);
    }

    public static Sound getKatanaSfx(){
        return am.get(KATANA_HIT_SFX);
    }

    public static Sound getBusterSfx(){
        return am.get(BUSTER_HIT_SFX);
    }

    public static Sound getBearSfx(){
        return am.get(BEAR_ENEMY_SFX);
    }

    public static Sound getRatSfx(){
        return am.get(RAT_ENEMY_SFX);
    }
    public static Sound getTrollSfx(){
        return am.get(TROLL_ENEMY_SFX);
    }


    private static final String COURIER_FONT_32 = "fonts/courier-new-bold-32.fnt";
    private static final String LOADING_ATLAS = "animations/loading.atlas";
    private static final String ANI_ATLAS = "animations/animations.atlas";
    private static final String SPRITE_ATLAS = "sprites/sprites.atlas";

    private static final String DAGGER_HIT_SFX = "sfx/Dagger.mp3";
    private static final String HATCHET_HIT_SFX = "sfx/hatchet.mp3";
    private static final String KATANA_HIT_SFX = "sfx/katana_sword.mp3";
    private static final String BUSTER_HIT_SFX = "sfx/big_buster.mp3";

    private static final String BEAR_ENEMY_SFX = "sfx/bear.mp3";
    private static final String RAT_ENEMY_SFX = "sfx/rat.mp3";
    private static final String TROLL_ENEMY_SFX = "sfx/troll.mp3";


}
