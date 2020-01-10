package rami.project.grey.core.entity.chika;

import com.badlogic.gdx.Gdx;

import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.consumable.attachables.AttachmentStructure;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.entity.consumable.attachables.weaponery.Weapon;
import rami.project.grey.core.item.ItemInventory;
import rami.project.grey.gameplay.PlayerController;

public class BigChika extends Chika {

    public PlayerController controller;

    private float totalWeight;

    // Attachments
    public AttachmentStructure attachments;

    public BigChika(ItemInventory inventory, byte maxAllowableTowes, byte maxAllowableAttachments) {
        super(EntitySize.XLARGE);

        attachments = new AttachmentStructure();

        // KEEP IT CALLED AFTER THE ATTACHMENTS CODE
        updateTotalWeight();
    }

    public void addController(PlayerController controller){
        this.controller = controller;
    }

    private void updateConfiguration(){
        updateTotalHealth();
        updateTotalWeight();
    }

    //  To be used whenever the
    private void updateTotalHealth(){
        totalHealth = (short) (BASE_HEALTH * size.number * 3/2);

        for (IAttachable attachment: attachments.getUsedAttachments())
            totalHealth += attachment.getHealthPenalty();

        // To make sure no extra health exists
        if (currentHealth > totalHealth)
            currentHealth = totalHealth;
    }

    private void updateTotalWeight(){
        totalWeight = size.number;
//        for (Chika chika: towes)
//            totalWeight += chika.getWeight();

        for (IAttachable attachment: attachments.getUsedAttachments())
            totalWeight += attachment.getWeight();
    }

    @Override
    public float getWeight() {
        return totalWeight;
    }

    // ATTACHING
    public void attach(IAttachable attachment){
        attachments.put(attachment);
        updateConfiguration();

    }

    @Override
    public int getDamageDealt() {
        if (attachments.hasWeapon()){
            Weapon weapon = attachments.getWeapon();
            if (weapon.canBeConsumed()){
                int damage = size.number;
                damage *= weapon.getWeaponType().multiplier;
                weapon.consume();
                Gdx.app.log("Game", "" + damage);
                return damage;
            }
        }

        return 0;
    }
}
