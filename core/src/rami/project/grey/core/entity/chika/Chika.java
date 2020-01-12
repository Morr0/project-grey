package rami.project.grey.core.entity.chika;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.EntityType;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.stacking.IHoldable;
import rami.project.grey.core.entity.LiveableEntity;
import rami.project.grey.core.entity.stacking.IStackable;

// The good entity that the player controls
public class Chika extends LiveableEntity implements IHoldable {

    protected byte BASE_HEALTH = 4;

    public boolean hasParent = false;
    IStackable parent = null;

    @Override
    public float getWeight() {
        return size.number;
    }

    public Chika(EntitySize size) {
        super(EntityType.CHIKA, size);
    }

    @Override
    public int getDamageDealt() {
        if (hasParent){
            return ((BigChika) parent).getDamageDealt();
        }
        return size.number;
    }

    // EVENT CALLBACKS

    @Override
    public void touched(IEntity toucher) {

    }

    @Override
    public boolean walkedIn(IEntity walker) {
        if (walker instanceof BigChika) {
            BigChika player = (BigChika) walker;
            player.controller.walkedIn(this);
        }

        return true;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        // Try to stack if hit by IHoldable
        if (walker instanceof IHoldable){
            if (((IHoldable) walker).isStakced()){
                IStackable stackable = ((IHoldable) walker).getStacker();
                if (stackable.canTow(this))
                    stackable.tow(this);
            }
        }

        // Try to stack if hit by IStackable
        if (walker instanceof IStackable){
            IStackable stackable = (IStackable) walker;
            if (stackable.canTow(this))
                stackable.tow(this);
        }

        return true;
    }

    @Override
    public void setMasterHolder(IStackable parent) {
        hasParent = parent != null;
        this.parent = parent;
    }

    @Override
    public boolean isStakced() {
        return hasParent;
    }

    @Override
    public IStackable getStacker() {
        return parent;
    }
}