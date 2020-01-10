package rami.project.grey.core.entity.consumable.attachables.weaponery;

import rami.project.grey.ui.resources.ITypeHasTexture;

public enum WeaponType implements ITypeHasTexture {
    STANDARD(1, 500, 4),
    UPGRADED(2, 2000, 5)
    ;
    public final int multiplier;
    // How much uses it has
    public final int durability;
    // Score usage
    public final int cost;

    WeaponType(int multiplier, int durability, int cost){
        this.multiplier = multiplier;
        this.durability = durability;
        this.cost = cost;
    }
}