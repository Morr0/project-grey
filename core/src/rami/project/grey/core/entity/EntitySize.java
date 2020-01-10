package rami.project.grey.core.entity;

public enum EntitySize {
    XSMALL((byte) 1, "Nene"),
    SMALL((byte) 2, "Nunu"),
    MEDIUM((byte) 3, "Checknoon"),
    LARGE((byte) 4, "Unchooky"),
    XLARGE((byte) 5, "Unter");

    public final byte number;
    protected String name;

    EntitySize(byte number, String name) {
        this.number = number;
        this.name = name;
    }

    public static EntitySize valueOf(byte num) {
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
