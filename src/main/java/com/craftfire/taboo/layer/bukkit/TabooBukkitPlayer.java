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
package com.craftfire.taboo.layer.bukkit;

import org.bukkit.entity.Player;

import com.craftfire.taboo.TabooPlayer;

public class TabooBukkitPlayer implements TabooPlayer {
    private Player player;

    public TabooBukkitPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    @Override
    public boolean checkPermission(String node) {
        return this.player.hasPermission(node);
    }

    @Override
    public void sendMessage(String message) {
        this.player.sendMessage(message);
    }

    @Override
    public void kick(String message) {
        this.player.kickPlayer(message);
    }

    @Override
    public void executeCommand(String command) {
        this.player.getServer().dispatchCommand(this.player, command);
    }
}
