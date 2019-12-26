package rami.project.grey.core.gridsystem;

import java.util.*;

import com.badlogic.gdx.Gdx;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.spawning.Spawner;

// TODO a certain IEntity must only touch one grid at a single time
// To be used directly with the graphics
public final class GridManager implements Spawner.SpawningCallback {
    private int columns, rows;

    private Grid[][] map;
    // This is used to decrease processing
    private LinkedList<Grid> occupiedGrids;

    // Records player's
    private LinkedList<Grid> players;

    // Level skipping and stuff
    private boolean skippedLevel = false;

    // Subscribers
    private ArrayList<GridSubscriber> subscribers;

    public LinkedList<Grid> getOccupiedGrids() {
        return occupiedGrids;
    }

    public LinkedList<Grid> getPlayers() {
        return players;
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
        this.players = new LinkedList<>();

        this.subscribers = new ArrayList<>(2);
    }

    public void addSubscriber(GridSubscriber sub){
        this.subscribers.add(sub);
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

    public void setPlayerAt(int locationX, int locationY, IEntity entity){
        map[locationX][locationY].currentResider = entity;
        players.add(map[locationX][locationY]);
    }

    // Updating per frame
    public void update(){
        if (skippedLevel){
            occupiedGrids.clear();
            // TODO implement skipping later
        }
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
     * */
    public void moveTo(int oldX, int oldY, int offsetX, int offsetY){
        // Restricts going sideways off the screen
        int desiredX = oldX + offsetX;
        if (desiredX >= columns)
            desiredX = columns - 1;
        else if (desiredX < 0)
            desiredX = 0;

        // Allows to jump out of the level upwards but not downwards
        int desiredY = oldY + offsetY;
        if (desiredY < 0)
            desiredY = 0;
        else if (desiredY >= rows){
            desiredY = 0;
            skippedLevel = true;
        }

        // So that entities do not get stuck that way
        // DONT CHANGE IT UNLESS YOU KNOW HOW IT WORKS BECAUSE IT JUST WORKS
        if (oldX == desiredX && oldY == desiredY)
            return;

        Grid oldPos = map[oldX][oldY];
        Grid newPos = map[desiredX][desiredY];

        // For WalkedIn event
        if (newPos.currentResider != null)
            newPos.currentResider.walkedIn(oldPos.currentResider);

        // For WalkedInBehind event
        try {
            if (!skippedLevel && map[desiredX][desiredY + 1].currentResider != null)
                map[desiredX][desiredY + 1].currentResider.walkedInBehind(oldPos.currentResider);
        } catch (ArrayIndexOutOfBoundsException ex){Gdx.app.log("Game", " Exception");}

        // The actual moving
        map[desiredX][desiredY].currentResider = map[oldX][oldY].currentResider;
        map[oldX][oldY].currentResider = null;

        // Notifying subscribers
        if (map[desiredX][desiredY].currentResider instanceof BigChika)
            notifySubscribers(map[desiredX][desiredY].currentResider, desiredX, desiredY);
    }

    private void notifySubscribers(IEntity player, int newGridX, int newGridY){
        for (GridSubscriber subscriber: subscribers)
            subscriber.playerPosChanged(newGridX, newGridY);
    }

    // CALLBACK

    @Override
    public void spawnedAt(int gridX, int gridY, IEntity entity) {
        map[gridX][gridY].currentResider = entity;
        occupiedGrids.add(map[gridX][gridY]);
    }
}
