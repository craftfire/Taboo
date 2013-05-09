/*
 * This file is part of Taboo.
 *
 * Copyright (c) 2013 CraftFire <http://www.craftfire.com/>
 * Taboo is licensed under the GNU Lesser General Public License.
 *
 * Taboo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Taboo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
