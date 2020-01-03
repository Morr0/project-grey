package rami.project.grey.core.gridsystem;

// Lets the GridManager users know
public interface GridSubscriber {
    void jumpedLevel();
    void playerPosChanged(int newGridX, int newGridY);
    /**
     * Each change in the number of entities calls this provided not all entities were removed together.
     * */
    void removedEntity();
}
