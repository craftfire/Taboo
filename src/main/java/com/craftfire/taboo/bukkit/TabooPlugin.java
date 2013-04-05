package com.craftfire.taboo.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.craftfire.taboo.TabooException;
import com.craftfire.taboo.TabooManager;

import com.craftfire.commons.util.LoggingManager;

public class TabooPlugin extends JavaPlugin implements Listener {
    private LoggingManager logger;
    private TabooManager manager;

    @Override
    public void onEnable() {
        this.logger = new LoggingManager(getLogger().getName(), "[Taboo]");
        this.logger.info("Enabling Taboo");
        this.manager = new TabooManager(getDataFolder());
        this.manager.setLoggingManager(this.logger);
        try {
            this.manager.load();
        } catch (TabooException e) {
            this.logger.stackTrace(e);
            this.logger.severe("Error occurred during initialization of Taboo. Disabling self.");
            getServer().getPluginManager().disablePlugin(this);
        }
        getServer().getPluginManager().registerEvents(this, this);
        this.logger.info("Taboo enabled");
    }

    @Override
    public void onDisable() {
        this.manager = null;
        this.logger.info("Taboo disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("taboo") && args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("Reloading Taboo...");
            this.logger.info("Reloading Taboo (command issued by " + sender.getName() + ")");
            try {
                this.manager.load();
                sender.sendMessage("Taboo reloaded");
                this.logger.info("Taboo reloaded");
            } catch (TabooException e) {
                sender.sendMessage("Failed to reload Taboo. Check errors on console.");
                this.logger.stackTrace(e);
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setMessage(this.manager.processMessage(event.getMessage(), new TabooBukkitPlayer(event.getPlayer())));
    }

}
