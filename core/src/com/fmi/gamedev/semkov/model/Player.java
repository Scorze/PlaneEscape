package com.fmi.gamedev.semkov.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fmi.gamedev.semkov.PlaneEscape;
import com.fmi.gamedev.semkov.assets.Assets;

public class Player extends Actor {

    private PlaneEscape planeEscape;
    private World physicsWorld;
    private Body body;
    private Animation<TextureRegion> animation;
    private TextureRegion region;
    private float stateTime = 0;
    private static float PLAYER_SPEED = 3f;
    private static float MAXIMUM_RIGHT_TURN = 1f;
    private static float MAXIMUM_LEFT_TURN = -1f;

    public Player(PlaneEscape planeEscape, World physicsWorld, float x, float y,
                  float width, float height){
        this.planeEscape = planeEscape;
        this.physicsWorld = physicsWorld;

        animation = new Animation<TextureRegion>(1/15f, planeEscape.assets.manager.get(Assets.planeAtlas, TextureAtlas.class).getRegions(), Animation.PlayMode.LOOP);

        this.setX(x);
        this.setY(y);
        this.setOrigin(x, y);
        this.setScale(1f);
        this.setWidth(width);
        this.setHeight(height);

        this.initBody();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setPosition(body.getPosition().x - getWidth() / 2,body.getPosition().y - getHeight() / 2);
        this.setRotation(-body.getAngle() * MathUtils.radiansToDegrees);
        body.setLinearVelocity(MathUtils.sin(body.getAngle()) * PLAYER_SPEED, PLAYER_SPEED);
        if(getX() > PlaneEscape.WORLD_WIDTH - getWidth() / 2) {
            die();
        } else if (getX() < -getWidth() / 2) {
            die();
        }

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        float x = getX();
        float y = getY();
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        if (animation != null) {
            region = animation.getKeyFrame(stateTime);
            float rotation = getRotation();
            if (rotation == 0)
                batch.draw(region, x , y , getWidth(), getHeight());
            else {
                batch.draw(region, x, y , getWidth()/2, getHeight()/2 , getWidth(), getHeight(),
                        scaleX, scaleY, rotation);
            }
        }
    }

    public void rotateLeft() {
        if (body.getAngle() > MAXIMUM_LEFT_TURN) {
            body.setTransform(body.getPosition().x, body.getPosition().y, (body.getAngle() + -2f * MathUtils.degreesToRadians));
        }
    }

    public void rotateRight() {
        if (body.getAngle() < MAXIMUM_RIGHT_TURN) {
            body.setTransform(body.getPosition().x, body.getPosition().y, (body.getAngle() + 2f * MathUtils.degreesToRadians));
        }
    }

    public void die() {
        planeEscape.gameState = PlaneEscape.GameState.MENU;
    }

    public float getStateTime() {
        return this.stateTime;
    }

    private void initBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = physicsWorld.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getWidth() / 3f,getHeight() / 3f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        body.setLinearVelocity(0f, PLAYER_SPEED);

        bodyShape.dispose();
    }

}
