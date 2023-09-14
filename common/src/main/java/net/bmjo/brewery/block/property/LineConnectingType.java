package net.bmjo.brewery.block.property;

import net.minecraft.util.StringRepresentable;

public enum LineConnectingType implements StringRepresentable {
    NONE("none"),
    MIDDLE("middle"),
    LEFT("left"),
    RIGHT("right");

    private final String name;

    LineConnectingType(String type) {
        this.name = type;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
