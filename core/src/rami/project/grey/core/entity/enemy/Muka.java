package rami.project.grey.core.entity.enemy;

import rami.project.grey.core.entity.EntityDeathReceiver;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.ILiveable;
import rami.project.grey.core.entity.chika.BigChika;

public class Muka implements ILiveable, IEnemy {

    private final static short BASE_HEALTH = 6;
    private short hp;

    private EntityDeathReceiver receiver;

    private MukaSize size;

    public enum MukaSize { // TODO handle translation
        XSMALL((byte) 1, "Nene"),
        SMALL((byte) 2, "Nunu"),
        MEDIUM((byte) 3, "Checknoon"),
        LARGE((byte) 4, "Unchooky"),
        XLARGE((byte) 5, "Unter");

        public final byte number;
        protected String name;

        MukaSize(byte number, String name) {
            this.number = number;
            this.name = name;
        }

        public static MukaSize valueOf(byte num) {
            switch (num) {
                default:
                case 1:
                    return XSMALL;
                case 2:
                    return SMALL;
                case 3:
                    return MEDIUM;
                case 4:
                    return LARGE;
                case 5:
                    return XLARGE;
            }
        }
    }

    public Muka(EntityDeathReceiver receiver, MukaSize size) {
        this.receiver = receiver;
        this.size = size;

        this.hp = (short) (BASE_HEALTH * size.number);
    }

    @Override
    public String getName() {
        return "Muka";
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
    public short getTotalHealth() {
        return (short) (BASE_HEALTH * size.number);
    }

    @Override
    public short getCurrentHealth() {
        return hp;
    }

    @Override
    public short getDamageDealt() {
        return 0;
    }

    private void hit(short damageDealt){
        hp -= damageDealt;

        if (hp <= 0)
            receiver.died(this);
    }
}
