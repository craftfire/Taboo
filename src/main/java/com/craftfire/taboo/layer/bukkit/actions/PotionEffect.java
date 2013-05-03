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

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.Taboo;
import com.craftfire.taboo.TabooPlayer;
import com.craftfire.taboo.layer.bukkit.TabooBukkitPlayer;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class PotionEffect extends Action {

    public PotionEffect(YamlNode args) {
        super(args);
        if (!args.hasChild("type")) {
            throw new IllegalArgumentException("Missing argument: type");
        }
    }

    @Override
    public void execute(TabooPlayer target, Taboo taboo, String message) {
        Player player = ((TabooBukkitPlayer) target).getPlayer();
        int duration = 600;
        int amplifier = 0;
        try {
            PotionEffectType effectType = PotionEffectType.getByName(getArgs().getChild("type").getString());
            if (effectType.isInstant()) {
                duration = 1;
            }
            if (getArgs().hasChild("duration")) {
                duration = getArgs().getChild("duration").getInt();
            }
            if (getArgs().hasChild("power")) {
                amplifier = getArgs().getChild("power").getInt();
            }
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(effectType, duration, amplifier));
        } catch (YamlException e) {
            throw new RuntimeException(e);
        }
    }

}
