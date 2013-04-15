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
