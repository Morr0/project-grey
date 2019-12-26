package rami.project.grey.core.entity.chika;

import com.badlogic.gdx.Gdx;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.ILiveable;

// The good entity that the player controls
public class Chika implements IEntity, ILiveable {

    public boolean hasParent = false;
    BigChika parent = null;

    public enum ChikaSize{ // TODO handle translation
        XSMALL((byte) 1, "Nene"),
        SMALL((byte) 2, "Nunu"),
        MEDIUM((byte) 3, "Checknoon"),
        LARGE((byte) 4, "Unchooky"),
        XLARGE((byte) 5, "Unter"); // This is the player size

        // To be used to determine if a potential Chika can be towed or not
        public final byte towWeight;
        protected String name;

        ChikaSize(byte towWeight, String name){
            this.towWeight = towWeight;
            this.name = name;
        }
    }

    // Size is set once
    private ChikaSize size;

    public Chika(ChikaSize size){
        this.size = size;
    }

    public ChikaSize getSize(){
        return size;
    }

    // IEntity

    @Override
    public String getName() {
        return size.name;
    }

    @Override
    public boolean isPlayable() {
        return false;
    }

    // ILiveable
    protected short currentHealth;

    @Override
    public short getTotalHealth() {
        switch (size){
            case XSMALL:
                return 4;
            case SMALL:
                return 6;
            case MEDIUM:
                return 8;
            case LARGE:
                return 12;
            case XLARGE:
                return 16;
        }
        return 0;
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

    }

    @Override
    public void walkedInBehind(IEntity walker) {
        if (walker instanceof BigChika){
            BigChika bigChika = (BigChika) walker;
            bigChika.tow(this);
        }
    }
}
