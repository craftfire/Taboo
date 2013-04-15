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

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.craftfire.taboo.Layer;
import com.craftfire.taboo.TabooPlayer;

public class BukkitLayer implements Layer {
    private Server server;
    private Plugin plugin;

    public BukkitLayer(Server server, Plugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    @Override
    public TabooPlayer getPlayer(String username) {
        Player player = this.server.getPlayer(username);
        if (player != null) {
            return new TabooBukkitPlayer(player);
        }
        return null;
    }

    @Override
    public void broadcast(String message) {
        this.server.broadcastMessage(message);
    }

    @Override
    public void executeCommand(String command) {
        this.server.dispatchCommand(this.server.getConsoleSender(), command);
    }

    @Override
    public void schedule(Runnable task) {
        this.server.getScheduler().scheduleSyncDelayedTask(this.plugin, task);
    }

}
