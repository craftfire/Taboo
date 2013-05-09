package com.craftfire.taboo.layer.bukkit.actions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.Taboo;
import com.craftfire.taboo.TabooPlayer;
import com.craftfire.taboo.layer.bukkit.TabooBukkitPlayer;
import com.craftfire.taboo.util.LocationPreParser;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class Lightning extends Action {
    private boolean effect = false;
    private LocationPreParser target = null;

    public Lightning(YamlNode args) {
        super(args);
        try {
            if (getArgs().hasChild("effect")) {
                this.effect = getArgs().getChild("effect").getBool();
            }
            if (getArgs().hasChild("target")) {
                this.target = new LocationPreParser(getArgs().getChild("target"));
            }
        } catch (YamlException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(TabooPlayer target, Taboo taboo, String message) {
        Location loc;
        try {
            if (!getArgs().hasChild("target") || getArgs().getChild("target").isNull()) {
                loc = ((TabooBukkitPlayer) target).getPlayer().getLocation();
            } else {
                Player player = ((TabooBukkitPlayer) target).getPlayer();
                if (this.target.getPlayerName() != null) {
                    loc = player.getServer().getPlayer(format(this.target.getPlayerName(), taboo, target, message)).getLocation();
                } else {
                    World world = null;
                    if (this.target.getWorldName() != null) {
                        world = player.getServer().getWorld(format(this.target.getWorldName(), taboo, target, message));
                    }
                    if (world == null) {
                        world = player.getWorld();
                    }
                    loc = new Location(world, this.target.getX(), this.target.getY(), this.target.getZ());
                }
            }
        } catch (YamlException e) {
            throw new RuntimeException(e);
        }
        if (this.effect) {
            loc.getWorld().strikeLightningEffect(loc);
        } else {
            loc.getWorld().strikeLightning(loc);
        }
    }

}
