package rami.project.grey.core.entity;

/**
 * Describes an entity residing in a grid.
 *
 * Only moving objects must fire up callbacks so that no mismatch or weired behaviour happens.
 * */
public interface IEntity {
    /**
     * Every type of entity must have a unique name in the name register.
     * */
    String getName();

    /**
     * @return null -> an item having no notion of size
     * */
    EntitySize getSize();

    /**
     * For the inventory system.
     * */
    boolean isItem();

    float getWeight();

    // INTERACTION CALLBACKS
    /**
     * Describes an IEntity (maybe a player) touches a particular grid on the screen (e.g. by mouse/finger)
     * */
    void touched(IEntity toucher);

    boolean ALLOW = true;
    boolean DISALLOW = false;

    /**
     * Describes an IEntity have moved into a specific grid (i.e. walked on it)
     *
     * @return ALLOW or DISALLOW
     * */
    boolean walkedIn(IEntity walker);

    /**
     * Describes an IEntity walked and is currently behind the called entity
     *
     * @return ALLOW or DISALLOW
     * */
    boolean walkedInBehind(IEntity walker);
}
