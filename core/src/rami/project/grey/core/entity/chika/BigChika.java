package rami.project.grey.core.entity.chika;

import java.util.Stack;

public class BigChika extends Chika {

    // Towing properties:
    public static final byte DEFAULT_MAX_TOWS = 2;
    private byte maxTows = DEFAULT_MAX_TOWS;
    // -Can only tow and untow from the front
    private boolean isTowed;
    private Stack<Chika> towes;

    public BigChika() {
        super(ChikaSize.XLARGE);

        towes = new Stack<>();
    }

    private boolean hasTowes(){
        return isTowed;
    }

    public void tow(Chika child){
        // Only same or smaller size of the front tower can be towed
        // The front tower maybe BigChika itself if none other is towed

        if (!isTowed){
            towes.push(child);
            isTowed = true;
        } else {
            if (towes.size() < maxTows){
                if (canBeTowen(child))
                    towes.push(child);
                else
                    unsuccesfulTow(child);
            }
        }
    }

    public void untow(){
        // Untows the first Chika into the front
        if (isTowed){
            towes.pop();
            if (towes.size() == 0)
                isTowed = false;
        }
    }

    // Determines whether can be towed or not
    private boolean canBeTowen(Chika potentialChild){
        if (towes.peek().getSize().towWeight >= potentialChild.getSize().towWeight)
            return true;

        return false;
    }

    // This must be called whenever a tow couldn't occur
    private void unsuccesfulTow(Chika failedApplicant){

    }
}
