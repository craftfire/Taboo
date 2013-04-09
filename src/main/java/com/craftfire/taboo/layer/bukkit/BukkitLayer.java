package com.craftfire.taboo.layer.bukkit;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.craftfire.taboo.Layer;
import com.craftfire.taboo.TabooPlayer;

public class BukkitLayer implements Layer {
    private Server server;

    public BukkitLayer(Server server) {
        this.server = server;
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

}
