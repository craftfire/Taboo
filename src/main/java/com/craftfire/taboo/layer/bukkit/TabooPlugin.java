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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.craftfire.taboo.TabooException;
import com.craftfire.taboo.TabooManager;
import com.craftfire.taboo.TabooPlayer;

import com.craftfire.commons.util.LoggingManager;

public class TabooPlugin extends JavaPlugin implements Listener {
    public static final String LAYER_ACTION_PATH = "com.craftfire.taboo.layer.bukkit.actions.";
    private static final boolean CARE_ABOUT_PERMISSIONS_THREADSAFE = true;
    private static final String NO_PERM_MSG = ChatColor.RED + "You don't have permission to use this command.";
    private LoggingManager logger;
    private TabooManager manager;
    private List<String> actionPaths = new ArrayList<String>();

    @Override
    public void onEnable() {
        this.logger = new LoggingManager(getLogger().getName(), "[Taboo]");
        this.logger.info("Enabling Taboo");
        this.actionPaths.add(LAYER_ACTION_PATH);
        try {
            this.manager = loadTabooManager();
        } catch (TabooException e) {
            this.logger.stackTrace(e);
            this.logger.severe("Error occurred during initialization of Taboo. Disabling self.");
            getServer().getPluginManager().disablePlugin(this);
            return;
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
        if (!command.getName().equalsIgnoreCase("taboo") || args.length < 1) {
            return false;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("taboo.reload")) {
                sender.sendMessage(NO_PERM_MSG);
                return true;
            }
            sender.sendMessage("Reloading Taboo...");
            this.logger.info("Reloading Taboo");
            try {
                this.manager = loadTabooManager();
                sender.sendMessage("Taboo reloaded");
                this.logger.info("Taboo reloaded");
            } catch (TabooException e) {
                sender.sendMessage("Failed to reload Taboo. Check errors on console.");
                this.logger.stackTrace(e);
            }
            return true;
        }
        if ((args[0].equalsIgnoreCase("execute") || args[0].equalsIgnoreCase("exec")) && args.length >= 3) {
            if (!sender.hasPermission("taboo.execute." + args[2])) {
                sender.sendMessage(NO_PERM_MSG);
                return true;
            }
            try {
                Player target = getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("Player \"" + args[1] + "\" not found");
                    return true;
                }
                sender.sendMessage("Executing action \"" + args[2] + "\" on player: " + args[1]);
                this.manager.execAction(args[2], new TabooBukkitPlayer(target));
            } catch (TabooException e) {
                sender.sendMessage("Failed to execute the action: " + e.getMessage());
                if (e.getCause() != null) {
                    List<String> extra = new ArrayList<String>();
                    extra.add("sender: " + sender.getName());
                    extra.add("action: " + args[2]);
                    extra.add("target: " + args[1]);
                    this.logger.stackTrace(e, extra);
                }
            }
            return true;
        }
        return false;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(final AsyncPlayerChatEvent event) throws ExecutionException, InterruptedException {
        this.logger.debug("Got an " + (event.isAsynchronous() ? "async" : "sync") + " AsyncPlayerChatEvent");
        String message = event.getMessage();
        final TabooPlayer player = new TabooBukkitPlayer(event.getPlayer());
        if (CARE_ABOUT_PERMISSIONS_THREADSAFE && event.isAsynchronous()) {
            Future<String> future = getServer().getScheduler().callSyncMethod(this, new Callable<String>() {
                @Override
                public String call() {
                    return TabooPlugin.this.manager.processMessage(event.getMessage(), player, true);
                }
            });
            try {
                while (!future.isDone()) {
                    try {
                        future.get(); // Just wait for it to finish
                    } catch (InterruptedException e) {
                    }
                }
                message = future.get();
            } catch (CancellationException e) {
            }
        } else {
            message = this.manager.processMessage(message, player, event.isAsynchronous());
        }
        if (message.isEmpty()) {
            event.setCancelled(true);
            return;
        }
        event.setMessage(message);
    }

    public List<String> getActionPaths() {
        return this.actionPaths;
    }

    protected TabooManager loadTabooManager() throws TabooException {
        TabooManager manager = new TabooManager(new BukkitLayer(getServer(), this), getDataFolder(), this.actionPaths);
        manager.setLoggingManager(this.logger);
        manager.load();
        return manager;
    }
}
