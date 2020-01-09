package rami.project.grey.core.entity.consumable.attachables.weaponery;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.item.Item;
import rami.project.grey.core.item.ItemHolder;

/**
 * Represents a weapon that uses Ammo
 * */
public abstract class Weapon extends Item implements IAttachable {
    private ItemHolder ammo;

    public final void setAmmo(ItemHolder ammo){
        this.ammo = ammo;
    }

    public final boolean canShoot(){
        return ammo.getCount() > 0;
    }

    @Override
    public final short getHealthPenalty() {
        return 0;
    }

    @Override
    public boolean isItem() {
        return true;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public final void touched(IEntity toucher) {

    }

    @Override
    public final boolean walkedInBehind(IEntity walker) {
        return true;
    }

    @Override
    public boolean inGameUsable() {
        return true;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public final int stackingLimit() {
        return 1;
    }

    @Override
    public final boolean canBeConsumed() {
        return true;
    }

    @Override
    public void consume() {
        ammo.consume(1);
    }

    @Override
    public ItemType getType() {
        return ItemType.ATTACHABLE_WEAPON;
    }

    @Override
    public final boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            ((BigChika) walker).controller.inventory.addItem(this);
        }

        return true;
    }
}
