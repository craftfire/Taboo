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
