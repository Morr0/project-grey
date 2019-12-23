package rami.project.grey.core.gridsystem;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.spawning.Spawner;

// TODO a certain IEntity must only touch one grid at a single time
// To be used directly with the graphics
public final class GridManager implements Spawner.SpawningCallback {
    private int columns, rows;

    private Grid[][] map;
    // This is used to decrease processing
    private LinkedList<Grid> occupiedGrids;

    public LinkedList<Grid> getOccupiedGrids() {
        return occupiedGrids;
    }

    // The reason columns and rows are not constants is to deal with the future expandability of the game
    public GridManager(int columns, int rows){
        this.columns = columns;
        this.rows = rows;

        // This remains so that the memory is defined once
        this.map = new Grid[columns][rows];
        for (int col = 0; col < columns; col++){
            for (int row = 0; row < rows; row++){
                map[col][row] = new Grid(col, row, null);
            }
        }

        this.occupiedGrids = new LinkedList<>();
    }

    public void put(int locationX, int locationY, IEntity entity){
        map[locationX][locationY].currentResider = entity;

        if (entity != null)
            occupiedGrids.add(map[locationX][locationY]);
    }

    public IEntity at(int locationX, int locationY){
        return map[locationX][locationY].currentResider;
    }

    public void removeAt(int locationX, int locationY){
        Grid currentGrid = map[locationX][locationY];
        if (currentGrid.currentResider != null) {
            occupiedGrids.remove(currentGrid);
            map[locationX][locationY].currentResider = null;
        }
    }

    public boolean isEmptyAt(int locationX, int locationY){
        return map[locationX][locationY].currentResider == null;
    }

    // RELOCATION
    // These will take care of recycling grids and moving them up and down depending on what is going on

    /**
     * Moves an IEntity that is movable to a new location. All parameters' units are in grids.
     *
     * @param oldX           Old Grid X
     * @param oldY           Old Grid Y
     * @param offsetX        By how much to move in X. Can be negative.
     * @param offsetY        By how much to move in Y. Can be negative.
     * @param fireCallbacks  Whether to let the method call callbacks to existing IEntitys in the desired location.
     *
     * @return               When fireCallbacks is false, this will be a reference to the IEntity that resided in it's original location before it was taken over.
     * */
    public IEntity moveTo(int oldX, int oldY, int offsetX, int offsetY, boolean fireCallbacks){
        // This is all assuming that at oldPos there exists an IEntity
        Grid oldPos = map[oldX][oldY];
        Grid newPos = map[oldX + offsetX][oldY + offsetY];
        Grid takenOverPos = null;

        if (newPos.currentResider == null){ // If the desired location is empty
            newPos.currentResider = oldPos.currentResider;
        } else { // If the desired location is not empty
            if (fireCallbacks)
                newPos.currentResider.walkedIn(oldPos.currentResider);
            else
                takenOverPos = newPos;

            newPos = oldPos;
        }

        // Since in both cases the OLD position will be empty
        map[oldX][oldY].currentResider = null;
        map[oldX + offsetX][oldY + offsetY] = newPos;
        // The previous 2 lines are to update the array

        return takenOverPos == null? null: takenOverPos.currentResider;
    }

    // INTERACTION callbacks that entities implement to know if something happened

    // Touch event
    public void touchAt(int locationX, int locationY, IEntity toucher){
        if (map[locationX][locationY].currentResider != null)
            map[locationX][locationY].currentResider.touched(toucher);
    }

    // Movement event
    public void walkedAt(int locationX, int locationY, IEntity walker){
        if (map[locationX][locationY].currentResider != null)
            map[locationX][locationY].currentResider.touched(walker);
    }

    // CALLBACK


    @Override
    public void spawnedAt(int gridX, int gridY, IEntity entity) {
        map[gridX][gridY].currentResider = entity;
        occupiedGrids.add(map[gridX][gridY]);
    }
}
