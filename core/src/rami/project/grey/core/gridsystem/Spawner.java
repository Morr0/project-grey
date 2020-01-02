package rami.project.grey.core.gridsystem;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.entity.consumable.thruster.Thruster;
import rami.project.grey.core.entity.consumable.thruster.ThrusterType;

public final class Spawner implements GridSubscriber {
    private GridManager gMan;
    private short maxAllowableSpawns;

    // UTIL
    private Random numGen;

    // Player position
    int playerX, playerY;

    // Spawning timings
    private long lastSpawnedTime = 0; // 0 indicates nothing spawned yet
    // Primitive cooldown between spawns in milliseconds
    private static final short BETWEEN_SPAWN_COOLDOWN = 2000;
    private long nextSpawningChance;
    // To be used after finishing the spawning cooldown and is generated randomly between 0 and 1000
    private long spawningAtTime;

    // CURRENT LEVEL TRACKING
    private boolean allowSpawning = false;
    // MUST BE MORE THAN THE COOLDOWN
    private static final short sameLevelToSpawnThreshold = BETWEEN_SPAWN_COOLDOWN + 3000; // milliseconds
    private short currentlySpawned = 0;

    public Spawner(GridManager gMan, short maxAllowableSpawns, int playerX, int playerY) {
        this.gMan = gMan;
        this.gMan.addSubscriber(this);

        this.maxAllowableSpawns = maxAllowableSpawns;
        this.playerX = playerX;
        this.playerY = playerY;

        // UTIL
        this.numGen = new Random();
    }

    // To be updated with the update loop
    public void update(){
        if (canSpawn() && (System.currentTimeMillis() >= spawningAtTime)){
            spawn();

            // KEEP IT LAST
            // To ensure when the spawning happens no need to continue the loop
            return;
        }

        // For when the player didn't jump out to allow to spawn again
        if ((System.currentTimeMillis() - lastSpawnedTime) >= sameLevelToSpawnThreshold){
            allowSpawning = true;
        }
    }

    // Does all the checking to allow for spawning opportunities
    private boolean canSpawn(){
        if (System.currentTimeMillis() >= nextSpawningChance){ // Checks if it passed the cooldown
            if (currentlySpawned < maxAllowableSpawns){

                // Checking if able to spawn whether is on same level or not
                if (allowSpawning){
                    // Establish the time to spawn
                    spawningAtTime = System.currentTimeMillis() + numGen.nextInt(1000);

                    return true;
                }
            }
        }

        return false;
    }

    private void spawn(){
        // Depending on the position of the player the spawning occurs on
        ArrayList<Vector2> locs = getSpawnableLocations();
        if (locs == null) // To postpone the spawning because there is already enough spawned
            postponeSpawning();
        else {
            // To know how much to spawn
            calculateWeightings();

            for (Vector2 loc: locs){
                spawnBasedOnWeighting((short) loc.x, (short) loc.y);
            }

            // Set tracking variables for update loop
            lastSpawnedTime = System.currentTimeMillis();
            nextSpawningChance = System.currentTimeMillis() + BETWEEN_SPAWN_COOLDOWN;

            if (allowSpawning)
                allowSpawning = false;
        }
    }

    private ArrayList<Vector2> getSpawnableLocations(){
        short howMuchToSpawn = (short) (maxAllowableSpawns - currentlySpawned);
        if (howMuchToSpawn <= 0) // Since it can be negative
            return null;

        ArrayList<Vector2> locs = new ArrayList<>(numGen.nextInt(howMuchToSpawn));

        // TODO make personalised spawning locations, that is they care about where the player is in the map
//        if (playerX > (gMan.columns / 2) || playerY > (gMan.rows))

        short counter = 0, x, y;
        while (counter < howMuchToSpawn){
            // Random grid point
            x = (short) numGen.nextInt(gMan.columns);
            y = (short) numGen.nextInt(gMan.rows);

            // To know the state of looking for occupied grids
            boolean gotMatch = false;
            for (int i = 0; i < gMan.occupiedGrids.size(); i++){
                if (gotMatch) // Exit inner loop if there is a match
                    break;

                Grid grid = gMan.occupiedGrids.get(i);
                if ((grid.x == x) && (grid.y == y)) // To make sure to exit since there is a match
                    gotMatch = true;
            }

            if (!gotMatch){ // If there is no match, then put the location in array
                locs.add(new Vector2(x, y));
                counter++;
            }
        }

        return locs;
    }

    // Allocates a new spawning time
    private void postponeSpawning(){
        // Random time to spawn in less than a second
        nextSpawningChance = System.currentTimeMillis() + numGen.nextInt(1000);
    }

    // SPAWNING WEIGHTS
    private short consumablesToSpawn = 0;
    private short chikasToSpawn = 0;
    private short enemiesToSpawn = 0;

    private void calculateWeightings(){
        short howMuchToSpawn = (short) (maxAllowableSpawns - currentlySpawned);

        consumablesToSpawn = 1;
        howMuchToSpawn--;

        while (howMuchToSpawn > 0){
            chikasToSpawn++;
            howMuchToSpawn--;
        }

        // TODO make a proper future proof spawning system that is not hardcoded
    }

    private void spawnBasedOnWeighting(short x, short y){
        // Spawn each type one after the other

        if (consumablesToSpawn > 0){
            // TODO make it so it spawns all types of consumables
            gMan.put(x, y, new Thruster(ThrusterType.BASIC));

            consumablesToSpawn--;
            currentlySpawned++;
        }

        if (chikasToSpawn > 0){
            byte randomChikaSize = (byte) numGen.nextInt(Chika.ChikaSize.LARGE.number + 1);
            gMan.put(x, y, new Chika(Chika.ChikaSize.valueOf(randomChikaSize)));

            chikasToSpawn--;
            currentlySpawned++;
        }

        if (enemiesToSpawn > 0){

            enemiesToSpawn--;
            currentlySpawned++;
        }
    }

    // GRID SUBSCRIBER

    @Override
    public void playerPosChanged(int newGridX, int newGridY) {
        this.playerX = newGridX;
        this.playerY = newGridY;
    }

    @Override
    public void jumpedLevel() {
        nextSpawningChance = System.currentTimeMillis() + numGen.nextInt(1000);

        currentlySpawned = 0;
    }
}
