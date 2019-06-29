package com.fmi.gamedev.semkov.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.fmi.gamedev.semkov.PlaneEscape;
import com.fmi.gamedev.semkov.assets.Assets;

public class Obstacle extends Image {

    private PlaneEscape planeEscape;
    private World physicsWorld;
    private Body body;

    public Obstacle(PlaneEscape planeEscape, World physicsWorld,
                 float x, float y, float width, float height){
        super(planeEscape.assets.manager.get(Assets.bomb, Texture.class));
        this.planeEscape = planeEscape;
        this.physicsWorld = physicsWorld;
        setPosition(x,y);
        setOrigin(x,y);
        setWidth(width);
        setHeight(height);

        initBody();
    }

    private void initBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = physicsWorld.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getWidth() / 2.5f,getHeight() / 2.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;

        body.createFixture(fixtureDef);
        body.setUserData(this);

        bodyShape.dispose();
    }

    @Override
    public void act(float delta){
        this.setPosition(body.getPosition().x - getWidth() / 2,body.getPosition().y - getHeight() / 2);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
}
