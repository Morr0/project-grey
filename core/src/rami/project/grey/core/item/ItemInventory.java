package rami.project.grey.core.item;

import java.util.HashSet;
import java.util.Iterator;

import rami.project.grey.core.entity.consumable.attachables.weaponery.Weapon;
import rami.project.grey.core.entity.consumable.attachables.weaponery.ammo.Ammo;

/**
 * A class that represents the entire player's inventory that can be in-game or off-game
 * */
// SINGLETON
public final class ItemInventory {
    private HashSet<ItemHolder<? extends Item>> totalItemHolders;

    // TODO change this to be false when the inventory can be accessed by the main menu
    private boolean inGameSession = true;
    // ONLY APPLIES TO IN GAME
    // Describes the stuff that are currently in use by the player
    private ItemHolder<Weapon> inUseWeapon;
    private ItemHolder<Ammo>   inUseAmmo;

    /**
     * Since not all ItemTypes have the concept of in game usable,
     * then this will return null on those items otherwise will return the instance if exists.
     * */
    public ItemHolder getInUseItem(Item.ItemType type){
        switch (type){
            default:
                return null;
            case WEAPON:
                return inUseWeapon;
            case AMMO:
                return inUseAmmo;
        }
    }

    public void  setInUseItem(ItemHolder holder){
        switch (holder.getItem().getType()){
            case WEAPON:
                inUseWeapon = holder;
                break;
            case AMMO:
                inUseAmmo = holder;
                break;
        }
    }

    // Singleton
    private static ItemInventory inv;
    public static ItemInventory getInv(){
        if (inv == null)
            inv = new ItemInventory();

        return inv;
    }

    // Constructor
    private ItemInventory(){
        this.totalItemHolders = new HashSet<>();
    }

    public HashSet<ItemHolder<? extends Item>> getItems(){ return totalItemHolders; }
    public int totalItems(){ return totalItemHolders.size(); }

    /**
     * @param amount if left empty, then will default to 1, else just pass the amount > 0
     * @return  reference to the item holder
     * */
    public <T extends Item> ItemHolder<T> addItem(T item, int... amount){
        int amnt = amount.length > 0? amount[0]: 1;

        // Searches if exists else will add a new ItemHolder downstairs
        for (ItemHolder holder: totalItemHolders){
            if (holder.getItem().getType() == item.getType()){ // Checks if same type
                if (item.isStackable()){ // Ensures to only add stackable items
                    if (holder.count < item.stackingLimit()){ // Ensures to not add more than stacking limit
                        holder.count += amnt;
                    }

                    // Even if you cannot add more count you still need to return
                    return holder;
                }
            }
        }

        // Just adds a new holder to the system
        ItemHolder<T> holder = new ItemHolder<>(item, amnt);
        totalItemHolders.add(holder);

        return holder;
    }

    // ASSUMES THE ITEM EXISTS
    public void consumeItem(Item item){
        Iterator<ItemHolder<? extends Item>> iterator = totalItemHolders.iterator();
        while (iterator.hasNext()){
            ItemHolder holder = iterator.next();
            if (holder.getItem().getType() == item.getType()){
                holder.count--;

                // In case there is not any left, remove the item holder
                if (holder.count <= 0){
                    iterator.remove();
                    return;
                }
            }
        }
    }

}
