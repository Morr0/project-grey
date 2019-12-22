package rami.project.grey.core.gridsystem;

import rami.project.grey.core.entity.IEntity;

// Describes a location in the game
public class Grid {
    // If null it means that the grid is empty
    IEntity currentResider;

    Grid(IEntity currentResider){
        this.currentResider = currentResider;
    }
}
