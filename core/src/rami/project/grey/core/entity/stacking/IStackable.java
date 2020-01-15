package rami.project.grey.core.entity.stacking;

/**
 * Represents an IEntity that can hold other entities that are market as IHoldable
 * */
public interface IStackable {
    boolean isEmpty();
    boolean canStack(IHoldable holdable);
    void stack(IHoldable holdable);
    boolean canRelease();
    void release();
}
