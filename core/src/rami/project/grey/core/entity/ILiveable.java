package rami.project.grey.core.entity;

// Describes an entity that has health
public interface ILiveable extends IEntity {
    short getTotalHealth();
    short getCurrentHealth();
}
