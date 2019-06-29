package com.fmi.gamedev.semkov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fmi.gamedev.semkov.PlaneEscape;
import com.fmi.gamedev.semkov.assets.Assets;
import com.fmi.gamedev.semkov.game.GameWorld;

public class GameScreen implements Screen {

    private PlaneEscape planeEscape;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private Sprite background;
    private BitmapFont font;

    public GameScreen(PlaneEscape planeEscape) {
        this.planeEscape = planeEscape;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,PlaneEscape.WIDTH,PlaneEscape.HEIGHT);
        this.gameWorld = new GameWorld(this.planeEscape);
        this.background = new Sprite(planeEscape.assets.manager.get(Assets.background, Texture.class));
        this.background.setSize(PlaneEscape.WIDTH, PlaneEscape.HEIGHT);
        this.font = new BitmapFont();

        this.font.getData().scale(8);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(55/255f, 51/255f, 102/255f, 1); // 	0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.draw(batch);
        batch.end();
        gameWorld.render();
        batch.begin();
        drawScore(gameWorld.getScore(),batch);
        batch.end();

        gameWorld.update();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    private void drawScore(int score,SpriteBatch batch){

        GlyphLayout glyphLayout = new GlyphLayout();
        String item = "Score: " + score;
        glyphLayout.setText(font,item);
        float w = glyphLayout.width;
        font.draw(batch, glyphLayout, camera.position.x - w/2, PlaneEscape.HEIGHT - 200);
    }

}
