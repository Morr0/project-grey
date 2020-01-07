package rami.project.grey.core.entity.consumable.loot;

import rami.project.grey.core.item.Item;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.item.ItemNameRegistry;

public class StandardCoin extends Item implements IEntity {
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
    public boolean inGameUsable() {
        return false;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public int stackingLimit() {
        return Item.UNLIMATED_STACKING;
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
        return null;
    }
}
