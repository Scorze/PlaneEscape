package com.fmi.gamedev.semkov.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.fmi.gamedev.semkov.PlaneEscape;
import com.fmi.gamedev.semkov.listener.B2dContactListener;
import com.fmi.gamedev.semkov.model.Obstacle;
import com.fmi.gamedev.semkov.model.ObstacleGenerator;
import com.fmi.gamedev.semkov.model.Player;
import com.fmi.gamedev.semkov.screen.MenuScreen;

public class GameWorld {

    private PlaneEscape planeEscape;
    private World physicsWorld;
    private Player player;
    private Stage stage;
    private ObstacleGenerator obstacleGenerator;
    private int score;
    private SpriteBatch batch;
    //private Box2DDebugRenderer debugRenderer;

    public GameWorld(PlaneEscape planeEscape) {
        this.planeEscape = planeEscape;
        this.physicsWorld = new World(new Vector2(0,0f),false);
        this.physicsWorld.setContactListener(new B2dContactListener());

        this.player = new Player(planeEscape, physicsWorld,
                planeEscape.WORLD_WIDTH / 2,1f,2.5f,2.5f);
        this.stage = new Stage(new StretchViewport(planeEscape.WORLD_WIDTH, PlaneEscape.WORLD_HEIGHT));
        this.obstacleGenerator = new ObstacleGenerator(planeEscape, physicsWorld, stage);

        this.stage.addActor(player);

        this.score = 0;
        this.batch = new SpriteBatch();
        //this.debugRenderer = new Box2DDebugRenderer();

    }

    public void render(){
        this.stage.draw();
        physicsWorld.step(Gdx.graphics.getDeltaTime(),6,2);

        this.batch.begin();
        //this.debugRenderer.render(physicsWorld,stage.getCamera().combined);
        this.batch.end();
    }

    public void update(){
        this.stage.act();
        if(Gdx.input.isTouched()){
            if (isTouchedLeft()) {
                this.player.rotateLeft();
            } else {
                this.player.rotateRight();
            }
        }
        this.stage.getCamera().position.y = player.getY() + PlaneEscape.WORLD_HEIGHT/2.5f;
        this.obstacleGenerator.regenerateObstacle();
        updateScore();
        this.checkState();
    }

    public int getScore() {
        return score;
    }

    public void updateScore() {
        if (getScore() < (int)Math.floor(player.getStateTime()) / 3) {
            score++;
        }
    }

    private boolean isTouchedLeft() {
        return (Gdx.graphics.getWidth() - Gdx.input.getX()) >= Gdx.graphics.getWidth()/2;
    }

    private void checkState() {
        if(planeEscape.gameState == PlaneEscape.GameState.MENU){
            if(planeEscape.highScore < score){
                planeEscape.highScore = score;
                planeEscape.updateHighScore(score);
            }
            planeEscape.setScreen(new MenuScreen(planeEscape));
        }
    }


}
