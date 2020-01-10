package rami.project.grey.core.entity;

public enum EntityType {
    CHIKA(4, "Chika"),
    MUKA(6, "Muka")
    ;

    final int baseHealth;
    final String name;

    EntityType(int baseHealth, String name){
        this.baseHealth = baseHealth;
        this.name = name;
    }
}
