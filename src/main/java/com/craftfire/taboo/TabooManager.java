/*
 * This file is part of Taboo.
 *
 * Copyright (c) 2013-2013, CraftFire <http://www.craftfire.com/>
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
package com.craftfire.taboo;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.craftfire.commons.util.LoggingManager;
import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlManager;
import com.craftfire.commons.yaml.YamlNode;

public class TabooManager {
    private YamlManager config;
    private List<Taboo> taboos;
    private boolean onlyOnce;
    private Map<String, Action> actions;
    private LoggingManager logger = new LoggingManager("CraftFire.TabooManager", "[Taboo]");

    public TabooManager(YamlManager config) throws YamlException {
        this.config = config;
        loadConfig();
    }

    public void loadConfig() throws YamlException {
        this.config.load();
        this.onlyOnce = this.config.getBoolean("match-once");
        loadActions();
        loadTaboos();
    }

    public String processMessage(String message, TabooPlayer player) {
        Iterator<Taboo> i = this.taboos.iterator();
        while (i.hasNext()) {
            Taboo taboo = i.next();
            if (taboo.matches(message, player)) {
                executeActions(taboo, player, message);
                message = taboo.replace(message);
                if (this.onlyOnce) {
                    break;
                }
            }
        }
        return message;
    }

    public LoggingManager getLogger() {
        return this.logger;
    }

    protected void executeActions(Taboo taboo, TabooPlayer player, String message) {
        Iterator<String> i = taboo.getActions().iterator();
        while (i.hasNext()) {
            Action action = this.actions.get(i.next());
            if (action != null) {
                try {
                    action.execute(player, taboo, message);
                } catch (Exception e) {
                    this.logger.stackTrace(e);
                    // TODO: Catch all throwables, print some friendly warning
                }
            }
        }
    }

    protected void loadActions() throws YamlException {
        for (YamlNode node : this.config.getNode("actions").getChildrenList()) {
            String className = node.getChild("class").getString();
            Class<?> c = null;
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException ignore) {
            }
            if (c == null || !Action.class.isAssignableFrom(c)) {
                try {
                    c = Class.forName("com.craftfire.taboo.actions." + className);
                } catch (ClassNotFoundException ignore) {
                }
            }
            if (c == null || !Action.class.isAssignableFrom(c)) {
                // TODO: Try to class-load the class from local directory.
            }
            if (c == null || !Action.class.isAssignableFrom(c)) {
                // TODO: print some nice warning
                continue;
            }
            Constructor<? extends Action> con;
            try {
                con = c.asSubclass(Action.class).getConstructor(YamlNode.class);
                this.actions.put(node.getName(), con.newInstance(node));
            } catch (Exception e) {
                this.logger.stackTrace(e);
                // TODO: print some nice warning
            }
        }
    }

    protected void loadTaboos() throws YamlException {
        for (YamlNode node : this.config.getNode("taboos").getChildrenList()) {
            this.taboos.add(new Taboo(node));
        }
    }
}
