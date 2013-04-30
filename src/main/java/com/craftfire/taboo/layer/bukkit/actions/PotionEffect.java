package com.craftfire.taboo.layer.bukkit.actions;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.Taboo;
import com.craftfire.taboo.TabooPlayer;
import com.craftfire.taboo.layer.bukkit.TabooBukkitPlayer;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class PotionEffect extends Action {

    public PotionEffect(YamlNode args) {
        super(args);
        if (!args.hasChild("type")) {
            throw new IllegalArgumentException("Missing argument: type");
        }
    }

    @Override
    public void execute(TabooPlayer target, Taboo taboo, String message) {
        Player player = ((TabooBukkitPlayer) target).getPlayer();
        int duration = 1;
        int amplifier = 1;
        try {
            if (getArgs().hasChild("length")) {
                duration = getArgs().getChild("length").getInt();
            }
            if (getArgs().hasChild("power")) {
                amplifier = getArgs().getChild("power").getInt();
            }
            player.addPotionEffect(PotionEffectType.getByName(getArgs().getChild("type").getString()).createEffect(duration, amplifier));
        } catch (YamlException e) {
            throw new RuntimeException(e);
        }
    }

}
