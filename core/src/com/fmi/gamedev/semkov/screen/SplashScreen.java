package com.fmi.gamedev.semkov.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.fmi.gamedev.semkov.PlaneEscape;

public class SplashScreen implements Screen {

    private PlaneEscape planeEscape;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Stage stage;

    public SplashScreen(PlaneEscape planeEscape) {
        this.planeEscape = planeEscape;
        this.texture = new Texture("toon-biplane-3d-model-low-poly-animated-obj-fbx-blend-mtl.jpg");
        this.sprite = new Sprite(texture);
        sprite.setSize(PlaneEscape.WIDTH, PlaneEscape.HEIGHT);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, PlaneEscape.WIDTH, PlaneEscape.HEIGHT);
        this.stage = new Stage(new FillViewport(PlaneEscape.WIDTH, PlaneEscape.HEIGHT));
        this.camera.update();
    }

    @Override
    public void show() {
        planeEscape.assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!planeEscape.assets.manager.update()) { // Load some, will return true if done loading
            System.out.println("Loading: " +  (int) (planeEscape.assets.manager.getProgress()*100)+"%");
        } else {
            planeEscape.setScreen(new MenuScreen(this.planeEscape));
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sprite.draw(batch);
        batch.end();
        stage.act();
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
        texture.dispose();
        stage.dispose();
    }

}
