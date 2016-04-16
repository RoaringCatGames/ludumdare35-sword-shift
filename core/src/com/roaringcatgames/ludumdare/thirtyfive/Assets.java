package com.roaringcatgames.ludumdare.thirtyfive;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by barry on 4/16/16 @ 12:04 PM.
 */
public class Assets {

    private static AssetManager am;

    public static AssetManager load(){
        am = new AssetManager();

        am.load("badlogic.jpg", TEXTURE);
        return am;
    }

    public static TextureRegion getBadGuyTexture(){
        return new TextureRegion(am.get("badlogic.jpg", TEXTURE));
    }

    private static Class<Texture> TEXTURE = Texture.class;
}
