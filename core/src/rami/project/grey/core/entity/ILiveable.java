package rami.project.grey.core.entity;

/**
 * Describes an entity that has health and can attack. The units are in HP.
 * */
public interface ILiveable extends IEntity {
    short getTotalHealth();
    short getCurrentHealth();

    /**
     * @return how much HP should be deducted by an attack
     * */
    short getDamageDealt();
}
