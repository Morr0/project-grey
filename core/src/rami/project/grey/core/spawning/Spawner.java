package rami.project.grey.core.spawning;

import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Random;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.gridsystem.GridManager;

public final class Spawner {
    private GridManager gridManager;
    private int gridColumns, gridRows;
    private LinkedList<Vector2> reservedGrids;

    // To control whether or not to spawn
    public boolean canSpawn = true;

    // The player/s are not to be counted as spawned
    private int currentlySpawnedItems = 0;

    // The amount of IEntities to be spawned in the next spawn loop
    private int toBeSpawnedNow;

    private int maxAllowableSpawns;

    // SPAWN TIMING
    // This is so spawning does not happen on every frame update
    private long lastSpawnTime = 0;
    // These describe the intervals of updating the spawner to spawn or not
    private static final short spawningIntervalMin = 900, spawningIntervalMax = 1100;
    private static final short spawningIntervalInCaseOfLag = 3500;

    public Spawner(GridManager gridManager, int gridColumns, int gridRows, int maxAllowableSpawns) {
        this.gridManager = gridManager;
        this.gridColumns = gridColumns;
        this.gridRows = gridRows;

        this.reservedGrids = new LinkedList<>();

        this.maxAllowableSpawns = maxAllowableSpawns;
    }

    public void reserveAt(int gridX, int gridY){
        reservedGrids.add(new Vector2(gridX, gridY));
    }

    public void removeAt(int gridX, int gridY){
        for (int i = 0; i < reservedGrids.size(); i++){
            Vector2 v = reservedGrids.get(i);
            if (v.x == gridX && v.y == gridY) {
                reservedGrids.remove(i);
                break;
            }
        }
    }

    public void setMaxAllowableSpawns(int maxAllowableSpawns) {
        this.maxAllowableSpawns = maxAllowableSpawns;
    }

    // To be updated with the update loop
    public void update(){
        if (canSpawn) {
            if (maxAllowableSpawns > toBeSpawnedNow)
                toBeSpawnedNow = maxAllowableSpawns;

            // Checks if it is the time to spawn
            long currentTime = System.currentTimeMillis();
            int diff = (int) (currentTime - lastSpawnTime) ;
            if ((diff > spawningIntervalMin & diff < spawningIntervalMax) || diff < spawningIntervalInCaseOfLag){
                spawn(checkBeforeSpawning());
            }

            // Records the time to keep spawning once every many frames
            lastSpawnTime = currentTime;
        }
    }

    // This makes sure to signal to only spawn once there is space
    private byte checkBeforeSpawning(){
        if (currentlySpawnedItems < toBeSpawnedNow)
            return (byte) (toBeSpawnedNow - currentlySpawnedItems);
         else
            return -1;
    }

    private void spawn(byte toSpawn){
        if (toSpawn > 0){
            Random rand = new Random();
            short i = 0;
            while (i < toSpawn){
                byte gridX = (byte) (rand.nextInt(gridColumns));
                byte gridY = (byte) (rand.nextInt(gridRows));

                if (!existsAt(gridX, gridY)){
                    performAndNotifySpawning(gridX, gridY);
                    i++;
                }
            }

        }
    }

    private void performAndNotifySpawning(int x, int y){
        reservedGrids.add(new Vector2(x, y));
        currentlySpawnedItems++;
        toBeSpawnedNow--;

        // The actual guy
        IEntity entity = getEntity(true);

        // Notifying
        gridManager.spawnedAt(x, y, entity);
    }

    private IEntity getEntity(boolean friend){
        return new Chika(Chika.ChikaSize.SMALL);
    }

    private boolean existsAt(byte gridX, byte gridY){
        boolean result = false;
        for (int i = 0; i < reservedGrids.size(); i++){
            Vector2 v = reservedGrids.get(i);
            if (v.x == gridX && v.y == gridY) {
                result = true;
                break;
            }
        }

        return result;
    }

    public interface SpawningCallback {
        void spawnedAt(int gridX, int gridY, IEntity entity);
    }

}
