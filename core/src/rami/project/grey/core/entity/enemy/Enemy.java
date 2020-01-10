package rami.project.grey.core.entity.enemy;

import com.badlogic.gdx.Gdx;

import rami.project.grey.core.entity.EntityDeathReceiver;
import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.ILiveable;
import rami.project.grey.core.entity.chika.BigChika;

public abstract class Enemy implements ILiveable {
    protected EntityDeathReceiver entityDeathReceiver;
    protected EntitySize size;


    public Enemy(EntityDeathReceiver entityDeathReceiver, EntitySize size){
        this.entityDeathReceiver = entityDeathReceiver;
        this.size = size;

        this.hp = BASE_HEALTH * size.number;
    }

    private final static short BASE_HEALTH = 6;
    protected int hp;

    protected void hit(int damageDealt){
        hp -= damageDealt;

        if (hp <= 0){
            entityDeathReceiver.died(this);
            return;
        }
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public void touched(IEntity toucher) {
        if (toucher instanceof BigChika){
            hit(((BigChika) toucher).getDamageDealt());
        }
    }

    @Override
    public boolean walkedIn(IEntity walker) {
        return false;
    }

    @Override
    public boolean walkedInBehind(IEntity walker) {
        return false;
    }

    @Override
    public int getTotalHealth() {
        return (short) (BASE_HEALTH * size.number);
    }

    @Override
    public int getCurrentHealth() {
        return hp;
    }

    @Override
    public int getDamageDealt() {
        return 0;
    }
}
