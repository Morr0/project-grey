package rami.project.grey.core.gridsystem;

import rami.project.grey.core.entity.IEntity;

// Describes a location in the game
public class Grid {
    public int x, y;
    // If null it means that the grid is empty
    public IEntity currentResider;

    Grid(int x, int y, IEntity currentResider) {
        this.x = x;
        this.y = y;
        this.currentResider = currentResider;
    }
}
