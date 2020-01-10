package rami.project.grey.core.entity.consumable.attachables.weaponery.ammo;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.item.IItem;

public abstract class Ammo implements IItem, IEntity {
    @Override
    public final boolean isStackable() {
        return true;
    }

    @Override
    public final boolean canBeConsumed() {
        return true;
    }

    @Override
    public final void consume() {

    }

    @Override
    public final boolean isItem() {
        return true;
    }

    @Override
    public final void touched(IEntity toucher) {

    }

    @Override
    public final float getWeight() {
        return 0;
    }

    @Override
    public final boolean walkedInBehind(IEntity walker) {
        return true;
    }
}
