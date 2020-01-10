package rami.project.grey.core.entity;

import rami.project.grey.core.gridsystem.GridManager;

public abstract class LiveableEntity implements ILiveableEntity {
    protected EntityType type;
    protected EntitySize size;

    protected int totalHealth;
    protected int currentHealth;

    public LiveableEntity(EntityType type, EntitySize size) {
        this.type = type;
        this.size = size;

        this.totalHealth = this.currentHealth = type.baseHealth * size.number;
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public final String getName() {
        return type.name;
    }

    @Override
    public final EntitySize getSize() {
        return size;
    }

    @Override
    public int getTotalHealth() {
        return totalHealth;
    }

    @Override
    public final int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    protected final void hit(int damageDealt){
        currentHealth -= damageDealt;

        if (currentHealth <= 0){
            GridManager.get().died(this);
        }
    }
}
