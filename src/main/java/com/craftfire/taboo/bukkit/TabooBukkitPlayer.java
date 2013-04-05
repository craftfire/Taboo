package com.craftfire.taboo.bukkit;

import org.bukkit.entity.Player;

import com.craftfire.taboo.TabooPlayer;

public class TabooBukkitPlayer implements TabooPlayer {
    private Player player;

    public TabooBukkitPlayer(Player player) {
        this.player = player;
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

}
