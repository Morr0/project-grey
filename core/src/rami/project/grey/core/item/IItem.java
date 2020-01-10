package rami.project.grey.core.item;

/**
 * Describes an IItem that can be stored
 * */
public interface IItem {

    int UNLIMATED_STACKING = -1;

    String getName();

    /**
     * Describes whether this IItem can be stacked or only one can be owned at a time.
     * */
    boolean isStackable();

    /**
     * If is stackable, how many can exist off this IItem
     * */
    int stackingLimit();


    /**
     * A consumable IItem is one where it must stack, else just ignore the use of the consumability stuff
     * */
    boolean canBeConsumed();
    void consume();


    ItemType getType();

    enum ItemType {
        // Thrusters
        THRUSTER,
        AMMO,
        WEAPON,
        LOOT

        ;
    }
}
