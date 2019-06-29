package com.fmi.gamedev.semkov.model;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fmi.gamedev.semkov.PlaneEscape;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class ObstacleGenerator {

    private PlaneEscape planeEscape;
    private World physicsWorld;
    private Deque<Obstacle> obstacleList;
    private Stage stage;
    private static final int OBSTACLE_SIZE = 10;
    private Random random;


    public ObstacleGenerator(PlaneEscape planeEscape, World physicsWorld, Stage stage){
        this.planeEscape = planeEscape;
        this.physicsWorld = physicsWorld;
        this.random = new Random();
        this.stage = stage;
        initObstacles();
    }

    public void regenerateObstacle(){
        if(obstacleList.getFirst().getY() < stage.getCamera().position.y - PlaneEscape.WORLD_HEIGHT / 2){
            obstacleList.removeFirst().remove();
        }
        if(obstacleList.size() == OBSTACLE_SIZE){
            Obstacle obstacle = new Obstacle(planeEscape, physicsWorld, getRandomX(), obstacleList.getLast().getY() + 4,1.5f, 1.5f);
            obstacleList.add(obstacle);
            stage.addActor(obstacle);
        }
    }

    private void initObstacles(){
        obstacleList = new ArrayDeque<Obstacle>(OBSTACLE_SIZE);
        for(int i = 0; i < OBSTACLE_SIZE; i++) {
            Obstacle obstacle = new Obstacle(planeEscape, physicsWorld, getRandomX(),15+ i * 4,1.5f, 1.5f);
            obstacleList.add(obstacle);
        }
        for(Obstacle e: obstacleList){
            stage.addActor(e);
        }

    }

    private int getRandomX() {
        return random.nextInt((int)Math.floor(planeEscape.WORLD_WIDTH));
    }
}
