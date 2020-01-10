package rami.project.grey.core.entity.consumable.attachables.weaponery;

import com.badlogic.gdx.Gdx;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.item.Item;
import rami.project.grey.core.item.ItemHolder;
import rami.project.grey.core.item.ItemInventory;

/**
 * Represents a weapon that uses Ammo
 * */
public class Weapon extends Item implements IAttachable {
    private WeaponType type;
    private int currentDurability;

    public Weapon(WeaponType type, int currentDurability) {
        this.type = type;
        this.currentDurability = currentDurability;
    }

    public WeaponType getWeaponType(){
        return type;
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
        return currentDurability > 0;
    }

    @Override
    public void consume() {
        if (ItemInventory.getInv().getInUseItem(ItemType.AMMO).canBeConsumed()){
            ItemInventory.getInv().getInUseItem(ItemType.AMMO).consume(1);
            currentDurability--;
        }
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }

    @Override
    public final boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            ((BigChika) walker).controller.inventory.addItem(this);
        }

        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
