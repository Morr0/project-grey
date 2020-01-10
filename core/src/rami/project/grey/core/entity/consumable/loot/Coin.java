package rami.project.grey.core.entity.consumable.loot;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.item.IItem;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.item.ItemNameRegistry;

public class Coin implements IItem, IEntity, ILoot {
    @Override
    public boolean isItem() {
        return true;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public void touched(IEntity toucher) {

    }

    @Override
    public boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            BigChika chika = (BigChika) walker;
            chika.controller.interact(this);
        }

        return true;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        return true;
    }

    @Override
    public String getName() {
        return ItemNameRegistry.COIN_STANDARD;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public int stackingLimit() {
        return IItem.UNLIMATED_STACKING;
    }

    @Override
    public boolean canBeConsumed() {
        return true;
    }

    @Override
    public void consume() {

    }

    @Override
    public ItemType getType() {
        return ItemType.LOOT;
    }

    @Override
    public short getAward(){
        return 500;
    }

    @Override
    public EntitySize getSize() {
        return null;
    }
}
