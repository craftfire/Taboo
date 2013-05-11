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
package com.craftfire.taboo.layer.bukkit.actions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.Taboo;
import com.craftfire.taboo.TabooPlayer;
import com.craftfire.taboo.layer.bukkit.TabooBukkitPlayer;
import com.craftfire.taboo.util.LocationPreParser;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class Spawn extends Action {
    private LocationPreParser target = null;

    public Spawn(YamlNode args) {
        super(args);
        try {
            if (!args.hasChild("entity") || args.getChild("entity").isNull()) {
                throw new IllegalArgumentException("Missing argument: entity");
            }
            if (args.hasChild("target") && !args.getChild("target").isNull()) {
                this.target = new LocationPreParser(args.getChild("target"));
            }
        } catch (YamlException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(TabooPlayer target, Taboo taboo, String message) {
        Location loc;
        Player player = ((TabooBukkitPlayer) target).getPlayer();
        if (this.target == null) {
            loc = player.getLocation();
        } else {
            if (this.target.getPlayerName() != null) {
                loc = player.getServer().getPlayerExact(format(this.target.getPlayerName(), taboo, target, message)).getLocation();
            } else {
                World world = null;
                if (this.target.getWorldName() != null) {
                    world = player.getServer().getWorld(format(this.target.getWorldName(), taboo, target, message));
                }
                if (world == null) {
                    world = player.getWorld();
                }
                loc = new Location(world, this.target.getX(), this.target.getY(), this.target.getZ());
            }
        }
        String entity;
        int amount = 1;
        try {
            entity = format(getArgs().getChild("entity").getString(), taboo, target, message);
            if (getArgs().hasChild("amount")) {
                amount = getArgs().getChild("amount").getInt(amount);
            }
        } catch (YamlException e) {
            throw new RuntimeException(e);
        }
        EntityType type = EntityType.fromName(entity);
        if (type == null) {
            throw new IllegalArgumentException("Unknown entity type: " + entity);
        }
        for (int i = 0; i < amount; ++i) {
            loc.getWorld().spawnEntity(loc, type);
        }
    }

}
