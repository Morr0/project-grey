package rami.project.grey.core.gridsystem;

// Lets the GridManager users know
public interface GridSubscriber {
    void playerPosChanged(int newGridX, int newGridY);
}
