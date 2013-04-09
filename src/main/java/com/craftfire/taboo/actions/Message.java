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
package com.craftfire.taboo.actions;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.Taboo;
import com.craftfire.taboo.TabooPlayer;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class Message extends Action {
    public Message(YamlNode args) {
        super(args);
        if (!args.hasChild("message")) {
            throw new IllegalArgumentException("Missing argument: message");
        }
    }

    @Override
    public void execute(TabooPlayer player, Taboo taboo, String message) {
        try {
            String msg = format(getArgs().getChild("message").getString(), taboo, player, message);
            if (getArgs().hasChild("to")) {
                String to = getArgs().getChild("to").getString();
                if (to != null && !to.isEmpty()) {
                    to = format(to, taboo, player, message);
                    TabooPlayer target = taboo.getManager().getLayer().getPlayer(to);
                    if (target != null) {
                        target.sendMessage(msg);
                    } else {
                        taboo.getManager().getLogger().info("Action: " + getArgs().getName() + ", taboo: " + taboo.getName() + "cannot send message to player: " + to + " - player not found.");
                    }
                    return;
                }
            }
            player.sendMessage(msg);
        } catch (YamlException e) {
            throw new RuntimeException(e); // Shouldn't happen, we checked hasChild() in the constructor.
        }
    }
}
