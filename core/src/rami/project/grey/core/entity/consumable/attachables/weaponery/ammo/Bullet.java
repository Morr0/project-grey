package rami.project.grey.core.entity.consumable.attachables.weaponery.ammo;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.item.ItemNameRegistry;

public class Bullet extends Ammo {
    @Override
    public String getName() {
        return ItemNameRegistry.STANDARD_BULLET;
    }

    @Override
    public int stackingLimit() {
        return 250;
    }

    @Override
    public ItemType getType() {
        return ItemType.AMMO;
    }

    @Override
    public boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            ((BigChika) walker).controller.inventory.addItem(this);
        }
        return true;
    }

    @Override
    public EntitySize getSize() {
        return null;
    }
}
