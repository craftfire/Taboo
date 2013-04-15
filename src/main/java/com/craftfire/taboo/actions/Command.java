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

public class Command extends Action {

    public Command(YamlNode args) {
        super(args);
        if (!args.hasChild("command")) {
            throw new IllegalArgumentException("Missing argument: command");
        }
    }

    @Override
    public void execute(TabooPlayer target, Taboo taboo, String message) {
        try {
            String cmd = format(getArgs().getChild("command").getString(), taboo, target, message);
            if (getArgs().hasNode("as-console") && getArgs().getNode("as-console").getBool()) {
                taboo.getManager().getLayer().executeCommand(cmd);
                return;
            }
            if (getArgs().hasChild("as-player")) {
                String username = getArgs().getChild("as-player").getString();
                if (username != null) {
                    TabooPlayer executor = taboo.getManager().getLayer().getPlayer(username);
                    if (executor != null) {
                        executor.executeCommand(cmd);
                    } else {
                        taboo.getManager().getLogger()
                        .info("Action: " + getArgs().getName() + ", taboo: " + taboo.getName() + " cannot execute command as player: " + username + " - player not found.");
                    }
                    return;
                }
            }
            target.executeCommand(cmd);
        } catch (YamlException e) {
            throw new RuntimeException(e); // Shouldn't happen, we checked hasChild() in the constructor.
        }


    }

}
