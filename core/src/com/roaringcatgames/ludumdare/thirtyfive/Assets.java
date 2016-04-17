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

    public static TextureAtlas.AtlasRegion getYellowParticle(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("particles/aura-yellow");
    }
    public static TextureAtlas.AtlasRegion getPurpleParticle(){
        return am.get(SPRITE_ATLAS, TEXTURE_ATLAS).findRegion("particles/aura-purple");
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


    public static BitmapFont getFont32(){
        return am.get(COURIER_FONT_32, BITMAP_FONT);
    }




    private static Class<TextureAtlas> TEXTURE_ATLAS = TextureAtlas.class;
    private static Class<Music> MUSIC = Music.class;
    private static Class<BitmapFont> BITMAP_FONT = BitmapFont.class;
    private static Class<Sound> SOUND = Sound.class;

    private static final String COURIER_FONT_32 = "fonts/courier-new-bold-32.fnt";
    private static final String LOADING_ATLAS = "animations/loading.atlas";
    private static final String ANI_ATLAS = "animations/animations.atlas";
    private static final String SPRITE_ATLAS = "sprites/sprites.atlas";


}
