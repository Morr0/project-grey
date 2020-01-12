package rami.project.grey.core.entity.stacking;

/**
 * Represents an IEntity that can hold other entities that are market as IHoldable
 * */
public interface IStackable {
    boolean canTow(IHoldable holdable);
    void tow(IHoldable holdable);
    void release();
}
