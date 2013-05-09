package com.craftfire.taboo.util;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class LocationPreParser {
    private String worldName = null;
    private String playerName = null;
    private double x, y, z;

    public LocationPreParser(YamlNode node) throws YamlException {
        if (node.isScalar()) {
            this.playerName = node.getString();
            return;
        }
        if (node.isMap()) {
            throw new IllegalArgumentException("Argument '" + node.getName() + "' must not be a map.");
        }
        if (node.getChildrenCount() < 3) {
            throw new IllegalArgumentException("Argument '" + node.getName() + "' is a list, so it must have at least 3 elements (x, y, z).");
        }
        this.x = node.getChildrenList().get(0).getDouble();
        this.y = node.getChildrenList().get(1).getDouble();
        this.z = node.getChildrenList().get(2).getDouble();
        if (node.getChildrenCount() >= 4) {
            this.worldName = node.getChildrenList().get(3).getString();
        }
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

}
