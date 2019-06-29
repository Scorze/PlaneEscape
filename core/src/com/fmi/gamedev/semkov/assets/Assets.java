package com.fmi.gamedev.semkov.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    public AssetManager manager;

    public static String background = "background.png";
    public static String planeAtlas = "PlaneAnimation.atlas";
    public static String bomb = "bomba.png";

    public Assets() {
        manager = new AssetManager();
    }

    public void load() {
        manager.load(planeAtlas, TextureAtlas.class);
        manager.load(background, Texture.class);
        manager.load(bomb, Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }

}
