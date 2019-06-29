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

public class MenuScreen implements Screen {

    public static final String WELCOME_MESSAGE = "Welcome to Plane Escape!";
    public static final String PLAY_MESSAGE = "Press anywhere to play...";
    public static final String HIGH_SCORE_TEXT = "Highscore: ";



    private PlaneEscape planeEscape;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture menuBackground;
    private Sprite sprite;
    private BitmapFont font;

    public MenuScreen(PlaneEscape planeEscape){
        this.planeEscape = planeEscape;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,PlaneEscape.WIDTH,PlaneEscape.HEIGHT);
        this.camera.update();
        this.menuBackground = planeEscape.assets.manager.get(Assets.background, Texture.class);
        this.sprite = new Sprite(this.menuBackground );
        sprite.setSize(PlaneEscape.WIDTH, PlaneEscape.HEIGHT);
        this.font = new BitmapFont();

        this.font.getData().scale(8);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0/255f, 51/255f, 102/255f, 1); // 	0, 51, 102
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        batch.begin();
        sprite.draw(batch);
        drawHighScore(planeEscape.highScore,batch);
        batch.end();

        if(Gdx.input.justTouched()){
            planeEscape.gameState = PlaneEscape.GameState.PLAYING;
            planeEscape.setScreen(new GameScreen(planeEscape));
        }
    }

    private void drawHighScore(int score, SpriteBatch batch){

        GlyphLayout glyphLayout = new GlyphLayout();
        String helloMessage = WELCOME_MESSAGE;
        String playMessage = PLAY_MESSAGE;
        String highScoreMessage = HIGH_SCORE_TEXT + score;
        glyphLayout.setText(font, helloMessage);
        float width = glyphLayout.width;
        font.draw(batch, glyphLayout, camera.position.x - width/2, 3500);
        glyphLayout.setText(font, playMessage);
        width = glyphLayout.width;
        font.draw(batch, glyphLayout, camera.position.x - width/2, 600);
        glyphLayout.setText(font, highScoreMessage);
        width = glyphLayout.width;
        font.draw(batch, glyphLayout, camera.position.x - width/2, 300);

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

    }
}
