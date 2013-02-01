package com.craftfire.taboo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.craftfire.commons.yaml.YamlManager;

public class TabooManager {
    private YamlManager config;
    private List<Taboo> taboos;
    private boolean onlyOnce;
    private Map<String, Action> actions;

    public TabooManager(YamlManager config) {
        this.config = config;
        loadConfig();
    }

    public void loadConfig() {
        // TODO
    }

    public String processMessage(String message, TabooPlayer player) {
        Iterator<Taboo> i = this.taboos.iterator();
        while (i.hasNext()) {
            Taboo taboo = i.next();
            if (taboo.matches(message, player)) {
                executeActions(taboo, player);
                message = taboo.replace(message);
                if (this.onlyOnce) {
                    break;
                }
            }
        }
        return message;
    }

    protected void executeActions(Taboo taboo, TabooPlayer player) {
        Iterator i = taboo.getActions().iterator();
        while (i.hasNext()) {
            Action action = this.actions.get(i.next());
            if (action != null) {
                action.execute(player);
            }
        }
    }

}
