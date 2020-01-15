package rami.project.grey.core.gridsystem;

import java.util.Random;

final class SpawningTimer {
    private Random rand;

    long lastSpawnedTime;

    private int BETWEEN_SPAWN_COOLDOWN;
    static final int MAX_EXTRA_RANDOM_TIME = 2100;

    private long nextSpawningChance;

    SpawningTimer(Random rand, int BETWEEN_SPAWN_COOLDOWN){
        this.rand = rand;

        this.BETWEEN_SPAWN_COOLDOWN = BETWEEN_SPAWN_COOLDOWN;

        justCompletedSpawningCycle();
    }

    long nextSpawningChance(){
        return nextSpawningChance;
    }

    void justCompletedSpawningCycle(){
        lastSpawnedTime = System.currentTimeMillis();
        nextSpawningChance = System.currentTimeMillis() + BETWEEN_SPAWN_COOLDOWN + rand.nextInt(MAX_EXTRA_RANDOM_TIME);
    }

    void postponeNextSpawningCycle(){
        nextSpawningChance = System.currentTimeMillis() + rand.nextInt(MAX_EXTRA_RANDOM_TIME);
    }
}
