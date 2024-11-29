package com.badlogic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public class ListenerClass implements ContactListener {
    private long score;
    private final List<Body> bodiesToDestroy;

    public ListenerClass() {
        super();
        score = 0;
        bodiesToDestroy = new ArrayList<>();
    }

    public long getScore() {
        return score;
    }

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if ((userDataA instanceof Bird && userDataB instanceof Pig) ||
            (userDataA instanceof Pig && userDataB instanceof Bird)) {
            handleBirdPigBeginCollision(contact);
        }

        if ((userDataA instanceof Bird && userDataB instanceof Obstacle) ||
            (userDataA instanceof Obstacle && userDataB instanceof Bird)) {
            handleBirdObstacleBeginCollision(contact);
        }

        if ((userDataA instanceof Pig && userDataB instanceof Obstacle) ||
            (userDataA instanceof Obstacle && userDataB instanceof Pig)) {
            handlePigObstacleBeginCollision(contact);
        }

        if (userDataA instanceof Pig && userDataB instanceof Pig) {
            handlePigPigBeginCollision(contact);
        }

        if ((userDataA instanceof Pig && userDataB instanceof Ground) ||
            (userDataA instanceof Ground && userDataB instanceof Pig)) {
            handlePigGroundBeginCollision(contact);
        }

        if ((userDataA instanceof Pig && userDataB instanceof Stand) ||
            (userDataA instanceof Stand && userDataB instanceof Pig)) {
            handlePigStandBeginCollision(contact);
        }

        if ((userDataA instanceof Obstacle && userDataB instanceof Ground) ||
            (userDataA instanceof Ground && userDataB instanceof Obstacle)) {
            handleObstacleGroundBeginCollision(contact);
        }
        if (userDataA instanceof Obstacle && userDataB instanceof Obstacle) {
            handleObstacleObstacleBeginCollision(contact);
        }

        if ((userDataA instanceof Obstacle && userDataB instanceof Stand) ||
            (userDataA instanceof Stand && userDataB instanceof Obstacle)) {
            handleObstacleStandBeginCollision(contact);
        }
    }

    private void handlePigObstacleBeginCollision(Contact contact) {
        Pig pig = extractPig(contact);
        Obstacle obstacle = extractObstacle(contact);

        final float speedThreshold = 10;
        float relativeSpeed = calculateRelativeSpeed(pig.getBody(), obstacle.getBody());

        if (relativeSpeed > speedThreshold) {
            obstacle.damage();
            pig.damage();
        }
    }

    private void handlePigPigBeginCollision(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        Pig pigA = (Pig) bodyA.getUserData();
        Pig pigB = (Pig) bodyB.getUserData();
        pigA.damage(); pigB.damage();
    }

    private void handlePigGroundBeginCollision(Contact contact) {
        Pig pig = extractPig(contact);
        pig.damage();
    }

    private void handlePigStandBeginCollision(Contact contact) {
        Pig pig = extractPig(contact);
        pig.damage();
    }

    private void handleObstacleGroundBeginCollision(Contact contact) {
        Obstacle obstacle = extractObstacle(contact);
        obstacle.damage();
    }

    private void handleObstacleObstacleBeginCollision(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        Obstacle obstacleA = (Obstacle) bodyA.getUserData();
        Obstacle obstacleB = (Obstacle) bodyB.getUserData();
        final float speedThreshold = 20;
        float relativeSpeed = calculateRelativeSpeed(obstacleB.getBody(), obstacleA.getBody());

        if (relativeSpeed > speedThreshold) {
            obstacleA.damage();
            obstacleB.damage();
        }
    }

    private void handleObstacleStandBeginCollision(Contact contact) {
        Obstacle obstacle = extractObstacle(contact);
        obstacle.damage();
    }

    private void handleBirdObstacleBeginCollision(Contact contact) {
        Bird bird = extractBird(contact);
        Obstacle obstacle = extractObstacle(contact);

        final float speedThreshold = 10;
        float relativeSpeed = calculateRelativeSpeed(bird.getBody(), obstacle.getBody());

        if (relativeSpeed > speedThreshold) {
            obstacle.damage();
        }
    }

    private void handleBirdPigBeginCollision(Contact contact) {
        Bird bird = extractBird(contact);
        Pig pig = extractPig(contact);

        final float speedThreshold = 10;
        float relativeSpeed = calculateRelativeSpeed(bird.getBody(), pig.getBody());

        if (relativeSpeed > speedThreshold) {
            pig.damage();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if ((userDataA instanceof Bird && userDataB instanceof Pig) ||
            (userDataA instanceof Pig && userDataB instanceof Bird)) {
            handleBirdPigEndCollision(contact);
        }

        if ((userDataA instanceof Bird && userDataB instanceof Obstacle) ||
            (userDataA instanceof Obstacle && userDataB instanceof Bird)) {
            handleBirdObstacleEndCollision(contact);
        }

        if ((userDataA instanceof Pig && userDataB instanceof Obstacle) ||
            (userDataA instanceof Obstacle && userDataB instanceof Pig)) {
            handlePigObstacleEndCollision(contact);
        }

        if (userDataA instanceof Pig && userDataB instanceof Pig) {
            handlePigPigEndCollision(contact);
        }

        if ((userDataA instanceof Pig && userDataB instanceof Ground) ||
            (userDataA instanceof Ground && userDataB instanceof Pig)) {
            handlePigGroundEndCollision(contact);
        }

        if ((userDataA instanceof Pig && userDataB instanceof Stand) ||
            (userDataA instanceof Stand && userDataB instanceof Pig)) {
            handlePigStandEndCollision(contact);
        }

        if ((userDataA instanceof Obstacle && userDataB instanceof Ground) ||
            (userDataA instanceof Ground && userDataB instanceof Obstacle)) {
            handleObstacleGroundEndCollision(contact);
        }
        if (userDataA instanceof Obstacle && userDataB instanceof Obstacle) {
            handleObstacleObstacleEndCollision(contact);
        }

        if ((userDataA instanceof Obstacle && userDataB instanceof Stand) ||
            (userDataA instanceof Stand && userDataB instanceof Obstacle)) {
            handleObstacleStandEndCollision(contact);
        }
    }

    private void handleBirdObstacleEndCollision(Contact contact) {
        Obstacle obstacle = extractObstacle(contact);

        if (obstacle.getHealth() <= 0) {
            score += 50;
            Add(obstacle.getBody());
        }
    }

    private void handlePigObstacleEndCollision(Contact contact) {
        Pig pig = extractPig(contact);
        Obstacle obstacle = extractObstacle(contact);

        if (pig.getHealth() <= 0) {
            score += 100;
            Add(pig.getBody());
        }

        if (obstacle.getHealth() <= 0) {
            score += 50;
            Add(obstacle.getBody());
        }
    }

    private void handlePigPigEndCollision(Contact contact) {
        Body userDataA = contact.getFixtureA().getBody();
        Body userDataB = contact.getFixtureB().getBody();
        Pig A = (Pig) userDataA.getUserData();
        Pig B = (Pig) userDataB.getUserData();
        if (A.getHealth() <= 0) {
            score += 100;
            Add(A.getBody());
        }
        if (B.getHealth() <= 0) {
            score += 100;
            Add(B.getBody());
        }

    }

    private void handlePigGroundEndCollision(Contact contact) {
        Pig pig = extractPig(contact);
        if (pig.getHealth() <= 0) {
            score += 100;
            Add(pig.getBody());
        }
    }

    private void handlePigStandEndCollision(Contact contact) {
        Pig pig = extractPig(contact);
        if (pig.getHealth() <= 0) {
            score += 100;
            Add(pig.getBody());
        }
    }

    private void handleObstacleGroundEndCollision(Contact contact) {
        Obstacle obstacle = extractObstacle(contact);
        if (obstacle.getHealth() <= 0) {
            score += 50;
            Add(obstacle.getBody());
        }
    }

    private void handleObstacleObstacleEndCollision(Contact contact) {
        Body userDataA = contact.getFixtureA().getBody();
        Body userDataB = contact.getFixtureB().getBody();
        Obstacle A = (Obstacle) userDataA.getUserData();
        Obstacle B = (Obstacle) userDataB.getUserData();
        if (A.getHealth() <= 0) {
            score += 50;
            Add(A.getBody());
        }
        if (B.getHealth() <= 0) {
            score += 50;
            Add(B.getBody());
        }
    }

    private void handleObstacleStandEndCollision(Contact contact) {
        Obstacle obstacle = extractObstacle(contact);
        if (obstacle.getHealth() <= 0) {
            score += 50;
            Add(obstacle.getBody());
        }
    }

    private void handleBirdPigEndCollision(Contact contact) {
        Pig pig = extractPig(contact);

        if (pig.getHealth() <= 0) {
            score += 100;
            Add(pig.getBody());
        }
    }

    private Bird extractBird(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();
        return userDataA instanceof Bird ? (Bird) userDataA : (Bird) userDataB;
    }

    private Pig extractPig(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();
        return userDataA instanceof Pig ? (Pig) userDataA : (Pig) userDataB;
    }

    private Obstacle extractObstacle(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();
        return userDataA instanceof Obstacle ? (Obstacle) userDataA : (Obstacle) userDataB;
    }

    private float calculateRelativeSpeed(Body bodyA, Body bodyB) {
        Vector2 velocityA = bodyA.getLinearVelocity();
        Vector2 velocityB = bodyB.getLinearVelocity();

        Vector2 relativeVelocity = velocityA.sub(velocityB);

        return relativeVelocity.len();
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    private void Add(Body body) {
        if (body != null && !bodiesToDestroy.contains(body)) {
            bodiesToDestroy.add(body);
        }
    }

    public void processList() {
        List<Body> list = new ArrayList<>(bodiesToDestroy);
        for (Body body : list) {
            if (body != null) {
                if (body.getUserData() instanceof Bird bird) {
                    bird.setDestroy(true);
                } else if (body.getUserData() instanceof Pig pig) {
                    pig.destroy();
                } else if (body.getUserData() instanceof Obstacle obstacle) {
                    obstacle.destroy();
                }
                body.getWorld().destroyBody(body);
            }
        }
        bodiesToDestroy.clear();
    }
}
