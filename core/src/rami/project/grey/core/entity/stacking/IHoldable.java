package rami.project.grey.core.entity.stacking;

import rami.project.grey.core.entity.EntitySize;

/**
 * Represents an entity that can be stacked by an IStackable
 * */
public interface IHoldable {
    EntitySize getSize();

    void setMasterHolder(IStackable parent);

    boolean isHeldByStacker();
    IStackable getStacker();
}
