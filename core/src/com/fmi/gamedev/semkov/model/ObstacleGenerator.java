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
    private boolean doubleRow = false;


    public ObstacleGenerator(PlaneEscape planeEscape, World physicsWorld, Stage stage){
        this.planeEscape = planeEscape;
        this.physicsWorld = physicsWorld;
        this.random = new Random();
        this.stage = stage;
        initObstacles();
    }

    public void regenerateObstacle(){
        if(obstacleList.getFirst().getY() < stage.getCamera().position.y - PlaneEscape.WORLD_HEIGHT / 2) {
            if (!doubleRow) {
                addObstacle();
                Obstacle obstacle = obstacleList.removeFirst();
                obstacle.remove();
                if (obstacle.getY() == obstacleList.getFirst().getY()) {
                    obstacleList.removeFirst().remove();
                }
            } else {
                addDoubleObstacle();
                Obstacle obstacle = obstacleList.removeFirst();
                obstacle.remove();
                if (obstacle.getY() == obstacleList.getFirst().getY()) {
                    obstacleList.removeFirst().remove();
                }
            }
        }
    }

    private void initObstacles(){
        obstacleList = new ArrayDeque<Obstacle>(OBSTACLE_SIZE);
        Obstacle obstacle = new Obstacle(planeEscape, physicsWorld, getRandomX(),  8f,1.5f, 1.5f);
        obstacleList.add(obstacle);
        stage.addActor(obstacle);
        for(int i = 1; i < OBSTACLE_SIZE; i++) {
            if (!doubleRow) {
                addObstacle();
            } else {
                addDoubleObstacle();
            }
        }

    }

    private int getRandomX() {
        return random.nextInt((int)Math.floor(planeEscape.WORLD_WIDTH));
    }

    private int getRandomX(final int index) {
        int newIndex = random.nextInt((int)Math.floor(planeEscape.WORLD_WIDTH));
        while (newIndex == index) {
            newIndex = random.nextInt((int)Math.floor(planeEscape.WORLD_WIDTH));
        }
        return newIndex;
    }

    private void addObstacle() {
        Obstacle obstacle = new Obstacle(planeEscape, physicsWorld, getRandomX(), obstacleList.getLast().getY() + 4,1.5f, 1.5f);
        doubleRow = true;
        obstacleList.add(obstacle);
        stage.addActor(obstacle);
    }

    private void addDoubleObstacle() {
        int index = getRandomX();
        Obstacle obstacle = new Obstacle(planeEscape, physicsWorld, getRandomX(), obstacleList.getLast().getY() + 4,1.5f, 1.5f);
        obstacleList.add(obstacle);
        Obstacle obstacle2 = new Obstacle(planeEscape, physicsWorld, getRandomX(index), obstacleList.getLast().getY(),1.5f, 1.5f);
        obstacleList.add(obstacle2);
        stage.addActor(obstacle);
        stage.addActor(obstacle2);
        doubleRow = false;
    }
}
