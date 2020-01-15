package rami.project.grey.core.entity.chika;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.EntityType;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.stacking.IHoldable;
import rami.project.grey.core.entity.LiveableEntity;
import rami.project.grey.core.entity.stacking.IStackable;

// The good entity that the player controls
public class Chika extends LiveableEntity implements IHoldable {

    byte BASE_HEALTH = 4;

    private BigChika parent = null;

    @Override
    public float getWeight() {
        return size.number;
    }

    public Chika(EntitySize size) {
        super(EntityType.CHIKA, size);
    }

    @Override
    public int getDamageDealt() {
        if (parent != null)
            return (parent).getDamageDealt();

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
            return ALLOW;
        }

        return DISALLOW;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        // Try to stack if hit by IHoldable
        if (walker instanceof IHoldable){
            System.out.println("Chika behind");

            IStackable stackable = ((IHoldable) walker).isHeldByStacker()?
                    ((IHoldable) walker).getStacker(): null;
            if (stackable != null){
                System.out.printf("Chika behind 2");
                if (stackable.canStack(this))
                    stackable.stack(this);
            }
        }

        // Try to stack if hit by IStackable
        if (walker instanceof IStackable){
            IStackable stackable = (IStackable) walker;
            // This fixes a bug
            if (!stackable.isEmpty())
                return ALLOW;

            if (stackable.canStack(this))
                stackable.stack(this);
        }

        return ALLOW;
    }

    @Override
    public void setMasterHolder(IStackable parent) {
        this.parent = (BigChika) parent;
    }

    @Override
    public boolean isHeldByStacker() {
        return parent != null;
    }

    @Override
    public IStackable getStacker() {
        return parent;
    }
}