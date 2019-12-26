package rami.project.grey.core.entity.chika;

import com.badlogic.gdx.Gdx;

import java.util.Stack;

public class BigChika extends Chika {

    @Override
    public boolean isPlayable() {
        return true;
    }

    // Towing properties:
    private byte maxTows;
    // -Can only tow and untow from the front
    private boolean isTowed;
    private Stack<Chika> towes;

    public BigChika(byte maxAllowableTowes) {
        super(ChikaSize.XLARGE);
        this.maxTows = maxAllowableTowes;

        towes = new Stack<>();
    }

    public void setMaxTows(byte maxTows){
        this.maxTows = maxTows;
    }

    public int getNoTows(){
        return towes.size();
    }

    public Stack<Chika> getTowes() {
        return towes;
    }

    private boolean hasTowes(){
        return isTowed;
    }

    void tow(Chika child){
        // Only same or smaller size of the front tower can be towed
        // The front tower maybe BigChika itself if none other is towed

        if (!isTowed){ // First tow doesnt care about size of new tow
            child.hasParent = true;
            child.parent = this;
            towes.push(child);
            isTowed = true;
        } else {
            if (towes.size() < maxTows){
                if (canBeTowen(child)){
                    child.hasParent = true;
                    child.parent = this;
                    towes.push(child);
                } else
                    unsuccessfulTow(child, TowingReason.LIMITED_CAPACITY);
            }
        }
    }

    public void untow(){
        // Untows the first Chika into the front
        if (isTowed){
            towes.peek().hasParent = false;
            towes.pop();
            if (towes.size() == 0)
                isTowed = false;
        }
    }

    // Determines whether can be towed or not
    private boolean canBeTowen(Chika potentialChild){
        if (towes.peek().getSize().towWeight >= potentialChild.getSize().towWeight)
            return true;

        unsuccessfulTow(potentialChild, TowingReason.BIG_SIZE);
        return false;
    }

    // This must be called whenever a tow couldn't occur
    private void unsuccessfulTow(Chika failedApplicant, TowingReason reason){
        Gdx.app.log("Game", "Unsuccessful tow due to " + reason.toString());
    }
}
