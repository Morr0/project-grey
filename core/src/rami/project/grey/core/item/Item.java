package rami.project.grey.core.item;

/**
 * Describes an item that can be stored
 * */
public abstract class Item {
    public ItemGrade grade;

    public static final int UNLIMATED_STACKING = -1;

    public abstract String getName();

    /**
     * Describes whether an item can be used while in game. This does not affect this item being added to the inventory nor removed when consumed.
     * */
    public abstract boolean inGameUsable();

    /**
     * Describes whether this item can be stacked or only one can be owned at a time.
     * */
    public abstract boolean isStackable();

    /**
     * If is stackable, how many can exist off this item
     * */
    public abstract int stackingLimit();


    /**
     * A consumable item is one where it must stack, else just ignore the use of the consumability stuff
     * */
    public abstract boolean canBeConsumed();
    public abstract void consume();


    public abstract ItemType getType();

    public enum ItemType {
        // Thrusters
        ATTACHABLE_THRUSTER,
        ATTACHABLE_AMMO,
        ATTACHABLE_WEAPON,
        LOOT

        ;
    }
}
