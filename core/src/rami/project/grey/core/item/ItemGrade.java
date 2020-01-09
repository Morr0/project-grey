package rami.project.grey.core.item;

/**
 * Describes the grade of items
 * */
public enum ItemGrade {
    STANDARD((byte) 1),
    UPGRADED((byte) 2),
    ELITE((byte) 4),
    LIMITED((byte) 6);

    public final byte number;

    ItemGrade(byte number){
        this.number = number;
    }
}
