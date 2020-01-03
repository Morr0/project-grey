package rami.project.grey.core.entity.chika;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.ILiveable;

// The good entity that the player controls
public class Chika implements ILiveable {

    protected byte BASE_HEALTH = 4;

    public boolean hasParent = false;
    BigChika parent = null;

    public enum ChikaSize{ // TODO handle translation
        XSMALL((byte) 1, "Nene"),
        SMALL((byte) 2, "Nunu"),
        MEDIUM((byte) 3, "Checknoon"),
        LARGE((byte) 4, "Unchooky"),
        XLARGE((byte) 5, "Unter"); // This is the player size

        // Is a number to differentiate different chikas based on size, towing and etc...
        public final byte number;
        protected String name;

        ChikaSize(byte number, String name){
            this.number = number;
            this.name = name;
        }

        public static ChikaSize valueOf(byte num){
            switch (num){
                default: case 1:
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

    @Override
    public float getWeight() {
        return size.number;
    }

    // Size is set once
    protected ChikaSize size;

    public Chika(ChikaSize size){
        this.size = size;

        this.totalHealth = (short) (BASE_HEALTH * size.number);
        this.currentHealth = totalHealth;
    }

    public ChikaSize getSize(){
        return size;
    }

    // IEntity

    @Override
    public String getName() {
        return size.name;
    }

    // ILiveable
    protected short totalHealth;
    protected short currentHealth;

    @Override
    public short getTotalHealth() {
        return totalHealth;
    }

    @Override
    public short getCurrentHealth() {
        return currentHealth;
    }



    // EVENT CALLBACKS

    @Override
    public void touched(IEntity toucher) {

    }

    @Override
    public void walkedIn(IEntity walker) {
        if (walker instanceof BigChika){
            BigChika player = (BigChika) walker;
            player.controller.walkedIn(this);
        }
    }

    @Override
    public void walkedInBehind(IEntity walker) {
        if (walker instanceof BigChika){
            BigChika bigChika = (BigChika) walker;
            bigChika.tow(this);
        }
    }
}
