package com.fmi.gamedev.semkov.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fmi.gamedev.semkov.model.Obstacle;
import com.fmi.gamedev.semkov.model.Player;

public class B2dContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Class classA = contact.getFixtureA().getBody().getUserData().getClass();
        Class classB = contact.getFixtureB().getBody().getUserData().getClass();

        if(classA.equals(Player.class) && classB.equals(Obstacle.class)){
            Player player = (Player)(contact.getFixtureA().getBody().getUserData());
            player.die();
        }
        else if(classA.equals(Obstacle.class) && classB.equals(Player.class)){
            Player player = (Player)(contact.getFixtureB().getBody().getUserData());
            player.die();

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
