package rami.project.grey.core.entity;

/**
 * Describes an entity that has health and can attack. The units are in HP.
 * */
public interface ILiveable extends IEntity {
    int getTotalHealth();
    int getCurrentHealth();

    /**
     * @return how much HP should be deducted by an attack
     * */
    int getDamageDealt();
}
