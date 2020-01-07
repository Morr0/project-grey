package rami.project.grey.core.gridsystem;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.entity.consumable.attachables.thruster.Thruster;
import rami.project.grey.core.entity.consumable.attachables.thruster.ThrusterType;
import rami.project.grey.gameplay.PlayerController;

public final class Spawner implements GridSubscriber, PlayerController.PlayerMotionState {

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
    private long nextSpawningChance = spawningThresholdTime;

    // CURRENT LEVEL TRACKING
    private boolean allowSpawning = false;
    // MUST BE MORE THAN THE COOLDOWN
    private static final short spawningThresholdTime = BETWEEN_SPAWN_COOLDOWN + 1000; // milliseconds
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
        if (canSpawn()){
            spawn();

            // KEEP IT LAST
            // To ensure when the spawning happens no need to continue the loop
            return;
        }

        // To set whether to spawn or not
        if ((System.currentTimeMillis() - lastSpawnedTime) >= spawningThresholdTime){
            allowSpawning = true;
        }
    }

    // Does all the checking to allow for spawning opportunities
    private boolean canSpawn(){
        if (playerReady){
            if (System.currentTimeMillis() >= nextSpawningChance){ // Checks if it passed the cooldown
                if (currentlySpawned < maxAllowableSpawns){

                    if (allowSpawning){
                        // Establish the time to spawn

                        return true;
                    }
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
            return;
        }

        if (chikasToSpawn > 0){
            byte randomChikaSize = (byte) numGen.nextInt(Chika.ChikaSize.LARGE.number + 1);
            gMan.put(x, y, new Chika(Chika.ChikaSize.valueOf(randomChikaSize)));

            chikasToSpawn--;
            currentlySpawned++;
            return;
        }

        if (enemiesToSpawn > 0){

            enemiesToSpawn--;
            currentlySpawned++;
            return;
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
        postponeSpawning();

        currentlySpawned = 0;
    }

    @Override
    public void removedEntity() {
        // Since this gets called whenever ONE entity gets removed from the OccupiedGrids count
        currentlySpawned--;
    }

    // PLAYERCONTROLLER's callbacks

    // To not always spawn but only when PlayerController is fine with spawning
    private boolean playerReady = true;

    @Override
    public void stopped() {
        playerReady = false;
    }

    @Override
    public void moving() {
        playerReady = true;
    }

    @Override
    public void thrusting() {
        playerReady = false;
        currentlySpawned = 0;
    }
}
